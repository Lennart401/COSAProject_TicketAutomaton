package de.leuphana.cosa.routesystem.behaviour;

import de.leuphana.cosa.routesystem.behaviour.service.RouteCommandService;
import de.leuphana.cosa.routesystem.behaviour.service.event.RouteEventHandler;
import de.leuphana.cosa.routesystem.behaviour.service.exceptions.RouteDoesNotExistException;
import de.leuphana.cosa.routesystem.structure.Route;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Lennart_Admin
 */
public class RouteSystemImpl implements RouteCommandService, BundleActivator {

	private Set<Route> routes = Set.of(
			new Route("Hamburg", "Harburg", 10),
			new Route("Harburg", "Buxtehude", 19),
			new Route("Harburg", "Buchholz (Nordheide)", 17),
			new Route("Harburg", "Winsen (Luhe)", 19),
			new Route("Winsen (Luhe)", "Lüneburg", 19),
			new Route("Lüneburg", "Dahlenburg", 22),
			new Route("Dahlenburg", "Dannenberg", 26),
			new Route("Lüneburg", "Uelzen", 32),
			new Route("Uelzen", "Soltau", 48),
			new Route("Uelzen", "Celle", 51),
			new Route("Celle", "Langenhagen", 28),
			new Route("Langenhagen", "Hannover", 9)
	);

	private ServiceReference eventAdminRef;
	private Logger logger;

	@Override
	public void start(BundleContext context) throws Exception {
		logger = LogManager.getLogger(this.getClass());
		logger.info("Starting RouteSystem");

		String[] topics = new String[] {
				"de/leuphana/cosa/routing/GET_STARTPOINTS",
				"de/leuphana/cosa/routing/GET_DESTINATIONS",
				"de/leuphana/cosa/routing/GET_ROUTE_LENGTH"
		};

		eventAdminRef = context.getServiceReference(EventAdmin.class.getName());
		EventAdmin eventAdmin = null;
		if (eventAdminRef != null) {
			eventAdmin = (EventAdmin) context.getService(eventAdminRef);
		} else {
			logger.fatal("no EventAdmin-Service found!");
		}

		Dictionary<String, Object> props = new Hashtable<>();
		props.put(EventConstants.EVENT_TOPIC, topics);
		context.registerService(EventHandler.class.getName(), new RouteEventHandler(this, eventAdmin), props);

		//Hashtable<String, String> props = new Hashtable<>();
		//props.put("Something", "Foo");
		//context.registerService(RoutingCommandService.class.getName(), (RoutingCommandService) (start, destination) -> start + " " + destination, props);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		context.ungetService(eventAdminRef);
	}

	@Override
	public Set<String> getStartpoints() {
		Set<String> startpoints = new HashSet<>();
		routes.stream().map(route -> List.of(route.getStartpoint(), route.getDestination())).forEach(startpoints::addAll);
		return startpoints;
	}

	@Override
	public Set<String> getDestinations(String startpoint) {
		Set<String> destinations = new HashSet<>();
		destinations.addAll(
				routes.stream()
						.filter(route -> route.getStartpoint().equals(startpoint))
						.map(Route::getDestination)
						.collect(Collectors.toSet())
		);
		destinations.addAll(
				routes.stream()
						.filter(route -> route.getDestination().equals(startpoint))
						.map(Route::getStartpoint)
						.collect(Collectors.toSet())
		);
		return destinations;
	}

	@Override
	public Double getRouteLength(String start, String destination) throws RouteDoesNotExistException {
		Route findRoute = routes.stream()
				.filter(route -> (route.getStartpoint().equals(start) && route.getDestination().equals(destination))
						|| (route.getStartpoint().equals(destination) && route.getDestination().equals(start)))
				.findAny()
				.orElseThrow(() -> new RouteDoesNotExistException(start, destination));
		return findRoute.getDistance() * 1.45;
	}
}

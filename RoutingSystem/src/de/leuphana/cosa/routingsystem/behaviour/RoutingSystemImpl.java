package de.leuphana.cosa.routingsystem.behaviour;

import de.leuphana.cosa.routingsystem.behaviour.service.RoutingCommandService;
import de.leuphana.cosa.routingsystem.behaviour.service.exceptions.RouteDoesNotExistException;
import de.leuphana.cosa.routingsystem.structure.Route;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Lennart_Admin
 */
public class RoutingSystemImpl implements RoutingCommandService, BundleActivator {

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

	@Override
	public void start(BundleContext context) throws Exception {
		System.out.println("Starting RoutingSystem");

		String[] topics = new String[] {
				"de/leuphana/cosa/routing/GET_STARTPOINTS",
				"de/leuphana/cosa/routing/GET_DESTINATIONS"
		};

		Dictionary<String, Object> props = new Hashtable<>();
		props.put(EventConstants.EVENT_TOPIC, topics);
		context.registerService(EventHandler.class.getName(), new RoutingEventHandler(this, context), props);

		//Hashtable<String, String> props = new Hashtable<>();
		//props.put("Something", "Foo");
		//context.registerService(RoutingCommandService.class.getName(), (RoutingCommandService) (start, destination) -> start + " " + destination, props);
	}

	@Override
	public void stop(BundleContext context) throws Exception {

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
	public int getRouteLength(String start, String destination) throws RouteDoesNotExistException {
		Route findRoute = routes.stream()
				.filter(route -> (route.getStartpoint().equals(start) && route.getDestination().equals(destination))
						|| (route.getStartpoint().equals(destination) && route.getDestination().equals(start)))
				.findAny()
				.orElseThrow(() -> new RouteDoesNotExistException(start, destination));
		return findRoute.getDistance();
	}
}

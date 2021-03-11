package de.leuphana.cosa.routesystem.behaviour.service.event;

import de.leuphana.cosa.routesystem.behaviour.service.RouteCommandService;
import de.leuphana.cosa.routesystem.behaviour.service.exceptions.RouteDoesNotExistException;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.event.EventHandler;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Set;

/**
 * @author Lennart_Admin
 */
public class RouteEventHandler implements EventHandler {

	private RouteCommandService routingService;
	private EventAdmin eventAdmin;

	public RouteEventHandler(RouteCommandService routingService, BundleContext context) {
		this.routingService = routingService;

		ServiceReference ref = context.getServiceReference(EventAdmin.class.getName());
		if (ref != null) {
			eventAdmin = (EventAdmin) context.getService(ref);
		} else {
			System.err.println("RoutingSystem: no EventAdmin-Service found!");
		}
	}

	@Override
	public void handleEvent(Event event) {
		if (eventAdmin != null) {
			System.out.println("RoutingEventHandler: " + event.getTopic());
			switch (event.getTopic()) {
				case "de/leuphana/cosa/routing/GET_STARTPOINTS" -> {
					if (eventAdmin != null) {
						Set<String> startspoints = routingService.getStartpoints();

						Dictionary<String, Object> props = new Hashtable<>();
						props.put("startpoints", startspoints);
						Event returnEvent = new Event("de/leuphana/cosa/routing/RESPONSE_STARTPOINTS", props);
						eventAdmin.sendEvent(returnEvent);
					}
				}

				case "de/leuphana/cosa/routing/GET_DESTINATIONS" -> {
					if (eventAdmin != null) {
						String startpoint = (String) event.getProperty("startpoint");
						Set<String> destinations = routingService.getDestinations(startpoint);

						Dictionary<String, Object> props = new Hashtable<>();
						props.put("startpoint", startpoint);
						props.put("destinations", destinations);
						Event returnEvent = new Event("de/leuphana/cosa/routing/RESPONSE_DESTINATIONS", props);
						eventAdmin.sendEvent(returnEvent);
					}

				}
				case "de/leuphana/cosa/routing/GET_ROUTE_LENGTH" -> {
					if (eventAdmin != null) {
						String startpoint = (String) event.getProperty("startpoint");
						String destination = (String) event.getProperty("destination");

						try {
							Double length = routingService.getRouteLength(startpoint, destination);

							Dictionary<String, Object> props = new Hashtable<>();
							props.put("startpoint", startpoint);
							props.put("destination", destination);
							props.put("length", length);

							// this is because pricingsystem will communicate with routesystem directly
							// if and only if this property is set, will routesystem process this event and
							// send a new one back out for ticketautomaton
							if ((boolean) event.getProperty("allowforward")) {
								props.put("allowforward", true);
							}

							Event returnEvent = new Event("de/leuphana/cosa/routing/RESPONSE_ROUTE_LENGTH", props);
							eventAdmin.sendEvent(returnEvent);
						} catch (RouteDoesNotExistException e) {
							// TODO what to do here?
							e.printStackTrace();
						}
					}
				}
			}
		}

	}
}

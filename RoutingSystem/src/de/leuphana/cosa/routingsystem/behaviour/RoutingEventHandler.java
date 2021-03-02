package de.leuphana.cosa.routingsystem.behaviour;

import de.leuphana.cosa.routingsystem.behaviour.service.RoutingCommandService;
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
public class RoutingEventHandler implements EventHandler {

	private RoutingCommandService routingService;
	private EventAdmin eventAdmin;

	public RoutingEventHandler(RoutingCommandService routingService, BundleContext context) {
		this.routingService = routingService;

		ServiceReference ref = context.getServiceReference(EventAdmin.class.getName());
		if (ref != null) {
			eventAdmin = (EventAdmin) context.getService(ref);
		} else {
			System.out.println("no eventadmin found!");
		}
	}

	@Override
	public void handleEvent(Event event) {
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
					String start = (String) event.getProperty("start");
					Set<String> destinations = routingService.getDestinations(start);

					Dictionary<String, Object> props = new Hashtable<>();
					props.put("destinations", destinations);
					Event returnEvent = new Event("de/leuphana/cosa/routing/RESPONSE_DESTINATIONS", props);
					eventAdmin.sendEvent(returnEvent);
				}

			}
			case "de/leuphana/cosa/routing/GET_ROUTES" -> {}
		}
	}
}

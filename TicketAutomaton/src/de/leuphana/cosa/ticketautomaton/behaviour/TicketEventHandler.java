package de.leuphana.cosa.ticketautomaton.behaviour;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.event.EventHandler;

import java.util.*;

/**
 * @author Lennart_Admin
 */
public class TicketEventHandler implements EventHandler {

	private Scanner scanner;
	private EventAdmin eventAdmin;

	// TODO create scanner in TicketEventHandler
	public TicketEventHandler(Scanner scanner, BundleContext context) {
		this.scanner = scanner;

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
			case "de/leuphana/cosa/routing/RESPONSE_STARTPOINTS" -> {
				Set<String> startpoints = (Set<String>) event.getProperty("startpoints");
				List<String> sps = List.copyOf(startpoints);

				for (int i = 0; i < sps.size(); i++) {
					System.out.println(i + " " + sps.get(i));
				}

				System.out.print("Please select a startpoint (type number 0 - " + (sps.size() - 1) + "): ");
				int startpointIndex = scanner.nextInt();
				System.out.println("Selected: " + sps.get(startpointIndex));

				System.out.println("\nLoading available destinations...");
				Dictionary<String, Object> eventGetDestinationsDictionaryWithGenericTypesOfStringAndObjectAsHashtableVariable = new Hashtable<>();
				eventGetDestinationsDictionaryWithGenericTypesOfStringAndObjectAsHashtableVariable.put("start", sps.get(startpointIndex));
				Event getDestinationsEventOrDeLeuphanaCosaRoutingGET_DESTINATIONSEvent = new Event("de/leuphana/cosa/routing/GET_DESTINATIONS",
						eventGetDestinationsDictionaryWithGenericTypesOfStringAndObjectAsHashtableVariable);
				eventAdmin.sendEvent(getDestinationsEventOrDeLeuphanaCosaRoutingGET_DESTINATIONSEvent);

//				Dictionary d = new Hashtable();
//				d.put("s", sps.get(startpointIndex));
//				Event e = new Event("gd", d);
//				eventAdmin.sendEvent(e);
			}

			case "de/leuphana/cosa/routing/RESPONSE_DESTINATIONS" -> {
				Set<String> destinations = (Set<String>) event.getProperty("destinations");
				System.out.println(destinations);
			}
		}
	}
}

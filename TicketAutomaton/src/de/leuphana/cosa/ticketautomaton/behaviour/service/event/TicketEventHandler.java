package de.leuphana.cosa.ticketautomaton.behaviour.service.event;

import de.leuphana.cosa.ticketautomaton.behaviour.CLI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.event.EventHandler;

import java.util.*;
import java.util.function.Function;

/**
 * @author Lennart_Admin
 */
public class TicketEventHandler implements EventHandler {

	private EventAdmin eventAdmin;
	private CLI cli;

	private Logger logger;

	public TicketEventHandler(EventAdmin eventAdmin) {
		this.cli = CLI.getInstance();
		this.eventAdmin = eventAdmin;
		this.logger = LogManager.getLogger(this.getClass());
	}

	@Override
	public void handleEvent(Event event) {
		logger.debug("Receiving event: " + event.getTopic());

		switch (event.getTopic()) {
			case "de/leuphana/cosa/routing/RESPONSE_STARTPOINTS" -> {
				Set<String> startpoints = (Set<String>) event.getProperty("startpoints");

				// select startpoint (CLI)
				String selectedStartpoint = cli.displayAndSelect(startpoints, "Please select a start point (type index): ");

				// send event for destinations
				cli.info("\nLoading available destinations...");
				Dictionary<String, Object> props = new Hashtable<>();
				props.put("startpoint", selectedStartpoint);
				Event eventGetDestinations = new Event("de/leuphana/cosa/routing/GET_DESTINATIONS", props);
				eventAdmin.sendEvent(eventGetDestinations);
			}

			case "de/leuphana/cosa/routing/RESPONSE_DESTINATIONS" -> {
				String startpoint = (String) event.getProperty("startpoint");
				Set<String> destinations = (Set<String>) event.getProperty("destinations");

				// select destination (CLI)
				String selectedDestination = cli.displayAndSelect(destinations, "Please select a destination (type index): ");

				// send event to show prices
				cli.info("\nLoading possible tickets...");
				Dictionary<String, Object> props = new Hashtable<>();
				props.put("startpoint", startpoint);
				props.put("destination", selectedDestination);

				Event eventGetPrices = new Event("de/leuphana/cosa/pricing/GET_PRICES", props);
				eventAdmin.sendEvent(eventGetPrices);
			}

			case "de/leuphana/cosa/pricing/RESPONSE_PRICES" -> {
				String startpoint = (String) event.getProperty("startpoint");
				String destination = (String) event.getProperty("destination");
				Double length = (Double) event.getProperty("length");
				Map<String, Double> priceGroups = (Map<String, Double>) event.getProperty("pricegroups");

				// fix needed to convert Map<String, Double> to Map<String, Object) for cli
				Map<String, Object> generalizedMap = new HashMap<>();
				priceGroups.forEach(generalizedMap::put);

				// select pricegroup
				String selectedGroup = cli.displayAndSelect(generalizedMap, "Please select a price group (type index): ", input -> {
					Double price = (Double) input;
					return price + " €"; // (Math.round(price * 100.0) / 100.0)
				});

				// send a event to create a ticket
				Dictionary<String, Object> props = new Hashtable<>();
				props.put("startpoint", startpoint);
				props.put("destination", destination);
				props.put("length", length);
				props.put("pricegroup", selectedGroup);
				props.put("price", priceGroups.get(selectedGroup));
				Event returnEvent = new Event("de/leuphana/cosa/document/CREATE_TICKET", props);
				eventAdmin.sendEvent(returnEvent);
			}
		}
	}
}

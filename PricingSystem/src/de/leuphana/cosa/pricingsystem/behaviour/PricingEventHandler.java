package de.leuphana.cosa.pricingsystem.behaviour;

import de.leuphana.cosa.pricingsystem.behaviour.service.PricingCommandService;
import de.leuphana.cosa.pricingsystem.structure.PriceGroup;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.event.EventHandler;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * @author Lennart_Admin
 */
public class PricingEventHandler implements EventHandler {

	private PricingCommandService service;
	private EventAdmin eventAdmin;

	public PricingEventHandler(PricingCommandService service, EventAdmin eventAdmin) {
		this.service = service;
		this.eventAdmin = eventAdmin;
	}

	@Override
	public void handleEvent(Event event) {
		if (eventAdmin != null) {
			System.out.println("PricingEventHandler: " + event.getTopic());
			switch (event.getTopic()) {
				case "de/leuphana/cosa/pricing/GET_PRICES" -> {
					String startpoint = (String) event.getProperty("startpoint");
					String destination = (String) event.getProperty("destination");

					// Send event get route length
					Dictionary<String, Object> props = new Hashtable<>();
					props.put("startpoint", startpoint);
					props.put("destination", destination);
					props.put("allowforward", true);
					Event returnEvent = new Event("de/leuphana/cosa/routing/GET_ROUTE_LENGTH", props);
					eventAdmin.sendEvent(returnEvent);
				}
				case "de/leuphana/cosa/routing/RESPONSE_ROUTE_LENGTH" -> {
					// receive event get route length
					if ((Boolean) event.getProperty("allowforward")) {
						String startpoint = (String) event.getProperty("startpoint");
						String destination = (String) event.getProperty("destination");
						Double length = (Double) event.getProperty("length");

						// calculate prices
						Map<PriceGroup, Double> prices = service.getPrices(length);
						Map<String, Double> printablePrices = new HashMap<>();
						prices.forEach(((priceGroup, price) -> printablePrices.put(priceGroup.getDisplayName(), price)));

						// forward event as price groups
						Dictionary<String, Object> props = new Hashtable<>();
						props.put("startpoint", startpoint);
						props.put("destination", destination);
						props.put("length", length);
						props.put("pricegroups", printablePrices);
						Event returnEvent = new Event("de/leuphana/cosa/pricing/RESPONSE_PRICES", props);
						eventAdmin.sendEvent(returnEvent);
					}

				}
			}
		}
	}
}

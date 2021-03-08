package de.leuphana.cosa.pricingsystem.behaviour;

import de.leuphana.cosa.pricingsystem.behaviour.service.PricingCommandService;
import de.leuphana.cosa.pricingsystem.structure.PriceGroup;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Map;

/**
 * @author Lennart_Admin
 */
public class PricingSystemImpl implements PricingCommandService, BundleActivator {

	@Override
	public Map<PriceGroup, Double> getPrices(Double routeLength) {
		double defaultCost = routeLength * 0.03;

		return Map.of(
				PriceGroup.NORMAL_TARIF, defaultCost,
				PriceGroup.GUENSTIGERREISEN_TARIF, defaultCost * 0.75,
				PriceGroup.SCHNAEPPCHEN_TARIF, defaultCost * 0.5
		);
	}

	@Override
	public void start(BundleContext context) throws Exception {
		System.out.println("Starting PricingSystem");

		// Register event handler
		String[] topics = new String[] {
				"de/leuphana/cosa/pricing/GET_PRICES",
				"de/leuphana/cosa/routing/RESPONSE_ROUTE_LENGTH"
		};

		Dictionary<String, Object> eventHandlerProps = new Hashtable<>();
		eventHandlerProps.put(EventConstants.EVENT_TOPIC, topics);
		context.registerService(EventHandler.class.getName(), new PricingEventHandler(this, context), eventHandlerProps);
	}

	@Override
	public void stop(BundleContext context) throws Exception {

	}
}

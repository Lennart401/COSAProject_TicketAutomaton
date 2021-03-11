package de.leuphana.cosa.pricingsystem.behaviour;

import de.leuphana.cosa.pricingsystem.behaviour.service.PricingCommandService;
import de.leuphana.cosa.pricingsystem.behaviour.service.event.PricingEventHandler;
import de.leuphana.cosa.pricingsystem.structure.PriceGroup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Map;

/**
 * @author Lennart_Admin
 */
public class PricingSystemImpl implements PricingCommandService, BundleActivator {

	private ServiceReference eventAdminRef;
	private Logger logger;

	@Override
	public void start(BundleContext context) throws Exception {
		logger = LogManager.getLogger(this.getClass());
		logger.info("Starting PricingSystem");

		EventAdmin eventAdmin = null;
		eventAdminRef = context.getServiceReference(EventAdmin.class.getName());
		if (eventAdminRef != null) {
			eventAdmin = (EventAdmin) context.getService(eventAdminRef);
		} else {
			logger.fatal("no EventAdmin-Service found!");
		}

		// Register event handler
		String[] topics = new String[] {
				"de/leuphana/cosa/pricing/GET_PRICES",
				"de/leuphana/cosa/routing/RESPONSE_ROUTE_LENGTH"
		};

		Dictionary<String, Object> eventHandlerProps = new Hashtable<>();
		eventHandlerProps.put(EventConstants.EVENT_TOPIC, topics);
		context.registerService(EventHandler.class.getName(), new PricingEventHandler(this, eventAdmin), eventHandlerProps);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		context.ungetService(eventAdminRef);
	}

	@Override
	public Map<PriceGroup, Double> getPrices(Double routeLength) {
		double defaultCost = routeLength * 0.03;

		return Map.of(
				PriceGroup.NORMAL_TARIF, (Math.round(defaultCost * 100.0) / 100.0),
				PriceGroup.GUENSTIGERREISEN_TARIF, (Math.round(defaultCost * 75.0) / 100.0),
				PriceGroup.SCHNAEPPCHEN_TARIF, (Math.round(defaultCost * 50.0) / 100.0)
		);
	}
}

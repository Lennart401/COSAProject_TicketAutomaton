package de.leuphana.cosa.ticketautomaton.behaviour;

//import de.leuphana.cosa.component.Component;
//import de.leuphana.cosa.componentservicebus.ComponentServiceBus;
//import de.leuphana.swa.documentsystem.behaviour.DocumentSystemImpl;
//import de.leuphana.swa.documentsystem.behaviour.service.DocumentCommandService;
//import de.leuphana.swa.messagingsystem.behaviour.MessagingSystemImpl;
//import de.leuphana.swa.messagingsystem.behaviour.service.MessagingCommandService;
//import de.leuphana.swa.printingsystem.behaviour.PrintingSystemImpl;
//import de.leuphana.swa.printingsystem.behaviour.service.PrintingCommandService;
import de.leuphana.cosa.ticketautomaton.behaviour.service.event.TicketEventHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.osgi.framework.*;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;

import java.util.Collections;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Set;

public class TicketAutomaton implements BundleActivator {

	private EventAdmin eventAdmin;
	private ServiceReference eventAdminRef;

	private Logger logger;

	@Override
	public void start(BundleContext context) throws Exception {
		logger = LogManager.getLogger(this.getClass());
		logger.info("Starting TicketAutomaton");

		eventAdminRef = context.getServiceReference(EventAdmin.class.getName());
		if (eventAdminRef != null) {
			eventAdmin = (EventAdmin) context.getService(eventAdminRef);
		}

		// Register event handler
		String[] topics = new String[] {
				"de/leuphana/cosa/routing/RESPONSE_STARTPOINTS",
				"de/leuphana/cosa/routing/RESPONSE_DESTINATIONS",
				"de/leuphana/cosa/pricing/RESPONSE_PRICES"
		};

		Dictionary<String, Object> eventHandlerProps = new Hashtable<>();
		eventHandlerProps.put(EventConstants.EVENT_TOPIC, topics);
		context.registerService(EventHandler.class.getName(), new TicketEventHandler(eventAdmin), eventHandlerProps);

		// Run ticketautomaton logic
		if (eventAdmin != null) {
			CLI cli = CLI.getInstance();

			// start the CLI workflow, the rest of the CLI will be in TicketEventHandler
			while (true) {
				cli.info("\n\nFinding possible startpoints...");
				Event getStartpointsEvent = new Event("de/leuphana/cosa/routing/GET_STARTPOINTS", Collections.emptyMap());
				eventAdmin.sendEvent(getStartpointsEvent);

				cli.prompt("What would you like to do next?\n");
				String nextAction = cli.displayAndSelect(Set.of("Buy another ticket", "Exit"), "Select next action (type index): ");
				if (nextAction.equals("Exit")) break;
			}

			logger.info("All CLI Code executed, the framework may be closed now.");

		} else {
			logger.error("no EventAdmin service could be found!");
		}
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		context.ungetService(eventAdminRef);
	}
}
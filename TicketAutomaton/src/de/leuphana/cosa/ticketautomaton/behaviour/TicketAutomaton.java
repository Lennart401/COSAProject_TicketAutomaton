package de.leuphana.cosa.ticketautomaton.behaviour;

//import de.leuphana.cosa.component.Component;
//import de.leuphana.cosa.componentservicebus.ComponentServiceBus;
//import de.leuphana.swa.documentsystem.behaviour.DocumentSystemImpl;
//import de.leuphana.swa.documentsystem.behaviour.service.DocumentCommandService;
//import de.leuphana.swa.messagingsystem.behaviour.MessagingSystemImpl;
//import de.leuphana.swa.messagingsystem.behaviour.service.MessagingCommandService;
//import de.leuphana.swa.printingsystem.behaviour.PrintingSystemImpl;
//import de.leuphana.swa.printingsystem.behaviour.service.PrintingCommandService;
import org.osgi.framework.*;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;

import java.util.Collections;
import java.util.Dictionary;
import java.util.Hashtable;

public class TicketAutomaton implements BundleActivator {

	private EventAdmin eventAdmin;
	private ServiceReference eventAdminRef;

	@Override
	public void start(BundleContext context) throws Exception {
		System.out.println("Starting TicketAutomaton");

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
		context.registerService(EventHandler.class.getName(), new TicketEventHandler(context, eventAdmin), eventHandlerProps);

		// Run ticketautomaton logic
		if (eventAdmin != null) {
			CLI cli = CLI.getInstance();

//			if (true) {
				// get list of startpoints

			// start the CLI workflow, the rest of the CLI will be in TicketEventHandler
			cli.info("\n\nFinding possible startpoints...");
			Event getStartpointsEvent = new Event("de/leuphana/cosa/routing/GET_STARTPOINTS", Collections.emptyMap());
			eventAdmin.sendEvent(getStartpointsEvent);

//				// read startpoint via CLI
//				System.out.print("Type startpoint: ");
//				String startpoint = scanner.nextLine();
//
//				System.out.println("Finding possible destinations...");
//
//				//
//				Dictionary<String, String> props = new Hashtable<>();
//				props.put("start", startpoint);
//				Event getDestinationEvent = new Event("de/leuphana/cosa/routingsystem/GET_DESTINATIONS", props);
//				eventAdmin.sendEvent(getDestinationEvent);
//			}
		} else {
			System.out.println("no service found");
		}
//		context.addServiceListener(this);
//
//		ServiceReference[] refs = context.getServiceReferences(RoutingCommandService.class.getName(), "(Something=*)");
//
//		if (refs != null) {
//			System.out.println("found services!");
//			Scanner scanner = new Scanner(System.in);
//
//			while (true) {
//				System.out.print("Type start point: ");
//
//
//				routingService = (RoutingCommandService) context.getService(refs[0]);
//				//System.out.println(routingService.getRoute(line[0], line[1]));
//			}
//		}
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		context.ungetService(eventAdminRef);
	}
}
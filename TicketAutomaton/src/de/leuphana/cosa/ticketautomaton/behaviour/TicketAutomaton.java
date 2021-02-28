package de.leuphana.cosa.ticketautomaton.behaviour;

//import de.leuphana.cosa.component.Component;
//import de.leuphana.cosa.componentservicebus.ComponentServiceBus;
//import de.leuphana.swa.documentsystem.behaviour.DocumentSystemImpl;
//import de.leuphana.swa.documentsystem.behaviour.service.DocumentCommandService;
//import de.leuphana.swa.messagingsystem.behaviour.MessagingSystemImpl;
//import de.leuphana.swa.messagingsystem.behaviour.service.MessagingCommandService;
//import de.leuphana.swa.printingsystem.behaviour.PrintingSystemImpl;
//import de.leuphana.swa.printingsystem.behaviour.service.PrintingCommandService;
import de.leuphana.cosa.routingsystem.behaviour.service.RoutingCommandService;
import org.osgi.framework.*;

import java.util.Scanner;

public class TicketAutomaton implements BundleActivator, ServiceListener {
	// Components
//	private PrintingCommandService printingSystem;
//	private DocumentCommandService documentSystem;
//	private MessagingCommandService messagingSystem;
	private RoutingCommandService routingService;
	
	// Connector 
//	private ComponentServiceBus componentServiceBus;
	
	public TicketAutomaton() {
		// Create topology (star)
//		documentSystem = new DocumentSystemImpl();
//		printingSystem = new PrintingSystemImpl();
//		messagingSystem = new MessagingSystemImpl();
//
//		componentServiceBus = new ComponentServiceBus();
//
//		componentServiceBus.registerComponent((Component) documentSystem);
//		componentServiceBus.registerComponent((Component) printingSystem);
//		componentServiceBus.registerComponent((Component) messagingSystem);
//
//		componentServiceBus.configureComponentConnections();
	}
	
//	public boolean sellTicket(String start, String end) {
//		return componentServiceBus.sellTicket(start, end);
//	}

	@Override
	public void start(BundleContext context) throws Exception {
		System.out.println("Starting TicketAutomaton");
		context.addServiceListener(this);

		ServiceReference[] refs = context.getServiceReferences(RoutingCommandService.class.getName(), "(Something=*)");

		if (refs != null) {
			System.out.println("found services!");
			Scanner scanner = new Scanner(System.in);

			while (scanner.hasNextLine()) {
				String[] line = scanner.nextLine().split(" ");
				routingService = (RoutingCommandService) context.getService(refs[0]);
				System.out.println(routingService.getRoute(line[0], line[1]));
			}
		}
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		context.removeServiceListener(this);
	}

	@Override
	public void serviceChanged(ServiceEvent event) {
		String[] objectClass = (String[]) event.getServiceReference().getProperty("objectClass");

		switch (event.getType()) {
			case ServiceEvent.REGISTERED -> System.out.println("Service of type " + objectClass[0] + " registered");
			case ServiceEvent.UNREGISTERING -> System.out.println("Service of type " + objectClass[0] + " is unregistering");
			case ServiceEvent.MODIFIED -> System.out.println("Service of type " + objectClass[0] + " modified");
		}
	}
}
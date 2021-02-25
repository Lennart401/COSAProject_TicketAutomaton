package de.leuphana.cosa.ticketautomaton.behaviour;

import de.leuphana.cosa.component.Component;
import de.leuphana.cosa.componentservicebus.ComponentServiceBus;
import de.leuphana.swa.documentsystem.behaviour.DocumentSystemImpl;
import de.leuphana.swa.documentsystem.behaviour.service.DocumentCommandService;
import de.leuphana.swa.messagingsystem.behaviour.MessagingSystemImpl;
import de.leuphana.swa.messagingsystem.behaviour.service.MessagingCommandService;
import de.leuphana.swa.printingsystem.behaviour.PrintingSystemImpl;
import de.leuphana.swa.printingsystem.behaviour.service.PrintingCommandService;

public class TicketAutomaton {
	// Components
	private PrintingCommandService printingSystem;
	private DocumentCommandService documentSystem;
	private MessagingCommandService messagingSystem;
	
	// Connector 
	private ComponentServiceBus componentServiceBus;
	
	public TicketAutomaton() {
		// Create topology (star)
		documentSystem = new DocumentSystemImpl();
		printingSystem = new PrintingSystemImpl();
		messagingSystem = new MessagingSystemImpl();
		
		componentServiceBus = new ComponentServiceBus();
		
		componentServiceBus.registerComponent((Component) documentSystem);
		componentServiceBus.registerComponent((Component) printingSystem);
		componentServiceBus.registerComponent((Component) messagingSystem);
		
		componentServiceBus.configureComponentConnections();
	}
	
	public boolean sellTicket(String start, String end) {
		return componentServiceBus.sellTicket(start, end);
	}

}
package de.leuphana.cosa.componentservicebus;

import java.util.HashMap;
import java.util.Map;

import de.leuphana.cosa.component.Component;
import de.leuphana.cosa.componentservicebus.adapter.ManageableToPrintableAdapter;
import de.leuphana.cosa.componentservicebus.adapter.PrintReportToSendableAdapter;
import de.leuphana.swa.documentsystem.behaviour.service.DocumentCommandService;
import de.leuphana.swa.documentsystem.behaviour.service.event.ManageableEventService;
import de.leuphana.swa.messagingsystem.behaviour.service.MessagingCommandService;
import de.leuphana.swa.printingsystem.behaviour.service.PrintingCommandService;
import de.leuphana.swa.printingsystem.behaviour.service.event.PrintableEventService;

public class ComponentServiceBus {

	private Map<String, Component> componentMap;
	private Map<String, String> commandServices;
	private Map<String, String> eventServices;

	public ComponentServiceBus() {
		componentMap = new HashMap<String, Component>();
		commandServices = new HashMap<String, String>();
		eventServices = new HashMap<String, String>();
	}

	public void registerComponent(Component component) {
		componentMap.put(component.getComponentName(), component);
		commandServices.put(component.getComponentName(),
				component.getCommandServicePath() + "." + component.getCommandServiceName());
		eventServices.put(component.getComponentName(),
				component.getEventServicePath() + "." + component.getEventServiceName());
	}

	public void configureComponentConnections() {
		// Später externe Konfiguration, die aufzeigt, welcher CommandService bei
		// welchem Event aufgerufen wird!!!

		// Erzeugung des manageableToPrintableAdapter
		Component sourceComponent = componentMap.get("DocumentSystem");
		Component targetComponent = componentMap.get("PrintingSystem");

		// Später Nutzung von Java Reflection API um generisch sourceComponent und
		// targetComponent zuzuweisen
		new ManageableToPrintableAdapter((ManageableEventService) sourceComponent,
				(PrintingCommandService) targetComponent);

		// Erzeugung des printReportToSendableAdapter
		sourceComponent = componentMap.get("PrintingSystem");
		targetComponent = componentMap.get("MessagingSystem");

		// Später Nutzung von Java Reflection API um generisch sourceComponent und
		// targetComponent zuzuweisen
		new PrintReportToSendableAdapter((PrintableEventService) sourceComponent,
				(MessagingCommandService) targetComponent);
	}

	public boolean sellTicket(String start, String end) {
		// Später statt sellTicket lose Koppelung durch Event-Delegation
		Component startComponent = componentMap.get("DocumentSystem");
		((DocumentCommandService) startComponent).createDocument(start + " " + end);

		// TODO Es dürfte erst "true" zurückgegeben werden, wenn im DeliveryReport des MessagingSystem isDelivered true ist!!!
		return true;
	}
}

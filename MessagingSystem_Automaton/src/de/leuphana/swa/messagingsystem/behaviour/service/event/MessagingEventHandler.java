package de.leuphana.swa.messagingsystem.behaviour.service.event;

import de.leuphana.swa.messagingsystem.behaviour.service.DeliveryReport;
import de.leuphana.swa.messagingsystem.behaviour.service.MessagingCommandService;
import de.leuphana.swa.messagingsystem.behaviour.service.Sendable;
import de.leuphana.swa.messagingsystem.structure.MessageType;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.event.EventHandler;

import java.util.Dictionary;
import java.util.Hashtable;

/**
 * @author Lennart_Admin
 */
public class MessagingEventHandler implements EventHandler {

	private MessagingCommandService service;
	private EventAdmin eventAdmin;

	public MessagingEventHandler(MessagingCommandService service, EventAdmin eventAdmin) {
		this.service = service;
		this.eventAdmin = eventAdmin;
	}

	@Override
	public void handleEvent(Event event) {
		System.out.println("MessagingSystem: " + event.getTopic());
		if (event.getTopic().equals("de/leuphana/cosa/document/DOCUMENT_ADDED")) {
			String title = (String) event.getProperty("title");
			String content = (String) event.getProperty("content");

			Sendable confirmationMessage = new Sendable() {
				@Override
				public String getContent() {
					return title + "\n\nContent:\n" + content;
				}

				@Override
				public MessageType getMessageType() {
					return MessageType.EMAIL;
				}

				@Override
				public String getSender() {
					return "messagingsystem@ticketautomaton.leuphana.de";
				}

				@Override
				public String getReceiver() {
					return "central@ticketautomaton.leuphana.de";
				}
			};

			DeliveryReport report = service.sendMessage(confirmationMessage);
			Dictionary<String, Object> props = new Hashtable<>();
			props.put("confirmationtext", report.getConfirmationText());
			props.put("deliverydate", report.getDeliveryDate());
			props.put("success", report.isDeliverySuccessful());
			Event returnEvent = new Event("de/leuphana/cosa/messaging/DELIVERY_REPORT_CREATED", props);
			eventAdmin.sendEvent(returnEvent);
		}
	}
}

package de.leuphana.swa.messagingsystem.behaviour;

import java.time.LocalDate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.leuphana.cosa.component.Component;
import de.leuphana.swa.messagingsystem.behaviour.service.DeliveryReport;
import de.leuphana.swa.messagingsystem.behaviour.service.MessagingCommandService;
import de.leuphana.swa.messagingsystem.behaviour.service.Sendable;
import de.leuphana.swa.messagingsystem.behaviour.service.event.SendableEventService;
import de.leuphana.swa.messagingsystem.structure.message.Message;
import de.leuphana.swa.messagingsystem.structure.messagingfactory.AbstractMessagingFactory;
import de.leuphana.swa.messagingsystem.structure.messagingprotocol.MessagingProtocol;

public class MessagingSystemImpl implements MessagingCommandService, Component {
	private Logger logger;

	@Override
	public DeliveryReport sendMessage(Sendable sendable) {
		logger = LogManager.getLogger(this.getClass());
		
		AbstractMessagingFactory abstractMessagingFactory = AbstractMessagingFactory.getFactory(sendable.getMessageType());

		Message message = abstractMessagingFactory.createMessage(sendable.getReceiver(), sendable.getSender(), sendable.getContent());

		MessagingProtocol messageProtocol = abstractMessagingFactory.createMessagingProtocol();
		messageProtocol.open();
		messageProtocol.transfer(message);
		messageProtocol.close();

		String deliveryConfirmationText = "Message: " + sendable.getContent() + " transported via " + sendable.getMessageType();
		logger.info(deliveryConfirmationText);
		
		DeliveryReport deliveryReport = new DeliveryReport();
		deliveryReport.setConfirmationText(deliveryConfirmationText);
		deliveryReport.setDeliveryDate(LocalDate.now());
		deliveryReport.setDeliverySuccessful(true);

		return deliveryReport;
	}

	@Override
	public String getCommandServiceName() {
		return MessagingCommandService.class.getName();
	}

	@Override
	public String getEventServiceName() {
		return SendableEventService.class.getName();
	}

	@Override
	public String getCommandServicePath() {
		return MessagingCommandService.class.getPackageName();
	}

	@Override
	public String getEventServicePath() {
		return SendableEventService.class.getPackageName();
	}

	@Override
	public String getComponentName() {
		return "MessagingSystem";
	}
}

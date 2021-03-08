package de.leuphana.swa.messagingsystem.behaviour;

import java.time.LocalDate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.leuphana.swa.messagingsystem.behaviour.service.DeliveryReport;
import de.leuphana.swa.messagingsystem.behaviour.service.MessagingCommandService;
import de.leuphana.swa.messagingsystem.behaviour.service.Sendable;
import de.leuphana.swa.messagingsystem.structure.message.Message;
import de.leuphana.swa.messagingsystem.structure.messagingfactory.AbstractMessagingFactory;
import de.leuphana.swa.messagingsystem.structure.messagingprotocol.MessagingProtocol;

public class MessagingSystemImpl implements MessagingCommandService {
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
}

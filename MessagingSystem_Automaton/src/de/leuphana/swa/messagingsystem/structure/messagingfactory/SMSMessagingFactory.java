package de.leuphana.swa.messagingsystem.structure.messagingfactory;

import de.leuphana.swa.messagingsystem.structure.MessageType;
import de.leuphana.swa.messagingsystem.structure.message.Message;
import de.leuphana.swa.messagingsystem.structure.message.MessageBuilder;
import de.leuphana.swa.messagingsystem.structure.messagingprotocol.MessagingProtocol;
import de.leuphana.swa.messagingsystem.structure.messagingprotocol.MessagingProtocolFactory;

public class SMSMessagingFactory extends AbstractMessagingFactory {

	@Override
	public MessagingProtocol createMessagingProtocol() {
		return MessagingProtocolFactory.createMessagingProtocol(MessageType.SMS);
	}

	@Override
	public Message createMessage(String sender, String receiver, String contentAsString) {
		return MessageBuilder.createMessage(receiver, sender, contentAsString, MessageType.SMS);
	}
}

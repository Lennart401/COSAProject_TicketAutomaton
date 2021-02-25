package de.leuphana.swa.messagingsystem.structure.messagingprotocol;

import de.leuphana.swa.messagingsystem.structure.message.Message;

public interface MessagingProtocol {
	boolean open();
	boolean transfer(Message message);
	boolean close();
}

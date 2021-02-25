package de.leuphana.swa.messagingsystem.structure.message;

import de.leuphana.swa.messagingsystem.structure.communicationpartner.Receiver;
import de.leuphana.swa.messagingsystem.structure.communicationpartner.Sender;

public class MessageHeader {
	private Sender sender;
	private Receiver receiver;
	
	public MessageHeader(Sender sender, Receiver receiver) {
		this.receiver = receiver;
		this.sender = sender;
	}
}

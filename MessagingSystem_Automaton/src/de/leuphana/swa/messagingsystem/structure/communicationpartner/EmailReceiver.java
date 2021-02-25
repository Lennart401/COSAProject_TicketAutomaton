package de.leuphana.swa.messagingsystem.structure.communicationpartner;

public class EmailReceiver implements Receiver {
	// später Role-Object-Pattern
	private String name;
	// TODO statt String später event. Address
	private String address;
	
	public EmailReceiver(String receiverAddress) {
		this.address = receiverAddress;
	}
}

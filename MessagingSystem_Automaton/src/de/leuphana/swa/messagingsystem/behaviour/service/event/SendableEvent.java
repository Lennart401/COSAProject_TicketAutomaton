package de.leuphana.swa.messagingsystem.behaviour.service.event;

import java.util.EventObject;

import de.leuphana.swa.messagingsystem.behaviour.service.DeliveryReport;

public class SendableEvent extends EventObject {
	private DeliveryReport deliveryReport;

	public SendableEvent(DeliveryReport deliveryReport) {
		super(deliveryReport);
		this.deliveryReport = deliveryReport;
	}

	public DeliveryReport getDeliveryReport() {
		return deliveryReport;
	}

}
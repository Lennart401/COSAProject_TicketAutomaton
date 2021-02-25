package de.leuphana.swa.messagingsystem.behaviour.service.event;

import java.util.EventListener;

public interface SendableEventListener extends EventListener {
	void onMessageDelivered(SendableEvent sendableEvent);
//	void onMessageNotDelivered(SendableEvent sendableEvent);
}

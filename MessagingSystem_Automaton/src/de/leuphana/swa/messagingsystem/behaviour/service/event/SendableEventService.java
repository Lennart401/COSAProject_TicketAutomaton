package de.leuphana.swa.messagingsystem.behaviour.service.event;

public interface SendableEventService {
	void addSendableEventListener(SendableEventListener sendableEventListener);
	void removeSendableEventListener(SendableEventListener sendableEventListener);
}
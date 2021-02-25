package de.leuphana.swa.messagingsystem.behaviour.service;

import de.leuphana.swa.messagingsystem.structure.MessageType;

public interface Sendable {
	String getContent();

	MessageType getMessageType();

	String getSender();

	String getReceiver();
}

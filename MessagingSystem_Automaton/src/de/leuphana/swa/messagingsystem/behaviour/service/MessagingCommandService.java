package de.leuphana.swa.messagingsystem.behaviour.service;

public interface MessagingCommandService  {

	DeliveryReport sendMessage(Sendable sendable);

}
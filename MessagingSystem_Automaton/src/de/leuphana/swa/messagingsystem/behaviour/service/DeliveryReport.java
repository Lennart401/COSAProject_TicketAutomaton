package de.leuphana.swa.messagingsystem.behaviour.service;

import java.time.LocalDate;

public class DeliveryReport  {
	private boolean isDeliverySuccessful;
	private LocalDate deliveryDate;
	private String confirmationText;
	
	public boolean isDeliverySuccessful() {
		return isDeliverySuccessful;
	}

	public void setDeliverySuccessful(boolean isDeliverySuccessful) {
		this.isDeliverySuccessful = isDeliverySuccessful;
	}

	public LocalDate getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(LocalDate deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getConfirmationText() {
		return confirmationText;
	}

	public void setConfirmationText(String confirmationText) {
		this.confirmationText = confirmationText;
	}
}

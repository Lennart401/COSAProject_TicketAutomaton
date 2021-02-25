package de.leuphana.swa.printingsystem.behaviour.service;

import java.time.LocalDate;

public class PrintReport  {
	private boolean isPrintSuccessful;
	private LocalDate printDate;
	private String confirmationText;
	private float price;
	
	public boolean isPrintSuccessful() {
		return isPrintSuccessful;
	}

	public void setPrintSuccessful(boolean isPrintSuccessful) {
		this.isPrintSuccessful = isPrintSuccessful;
	}

	public LocalDate getPrintDate() {
		return printDate;
	}

	public void setPrintDate(LocalDate printDate) {
		this.printDate = printDate;
	}

	public String getConfirmationText() {
		return confirmationText;
	}

	public void setConfirmationText(String confirmationText) {
		this.confirmationText = confirmationText;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

}

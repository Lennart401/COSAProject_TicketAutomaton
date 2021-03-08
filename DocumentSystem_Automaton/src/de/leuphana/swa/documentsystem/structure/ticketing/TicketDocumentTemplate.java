package de.leuphana.swa.documentsystem.structure.ticketing;

import de.leuphana.swa.documentsystem.structure.Document;

import java.time.LocalDate;

/**
 * @author Lennart_Admin
 */
public abstract class TicketDocumentTemplate extends Document {

	private static int amountTickets = 1;

	private String startpoint;
	private String destination;
	private Double length;
	private Double price;
	private LocalDate date;

	public TicketDocumentTemplate(String startpoint, String destination, Double length, Double price) {
		super("Ticket #" + (amountTickets++));

		this.startpoint = startpoint;
		this.destination = destination;
		this.length = length;
		this.price = price;
		this.date = LocalDate.now();
	}

	public String getStartpoint() {
		return startpoint;
	}

	public String getDestination() {
		return destination;
	}

	public Double getLength() {
		return length;
	}

	public Double getPrice() {
		return price;
	}

	public LocalDate getDate() {
		return date;
	}

	@Override
	public void setText(String text) {
		throw new UnsupportedOperationException("Can't change the text of a ticket");
	}
}

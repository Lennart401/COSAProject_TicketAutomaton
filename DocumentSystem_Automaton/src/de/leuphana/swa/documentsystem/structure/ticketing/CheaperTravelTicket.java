package de.leuphana.swa.documentsystem.structure.ticketing;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * @author Lennart_Admin
 */
public class CheaperTravelTicket extends TicketDocumentTemplate {
	public CheaperTravelTicket(String startpoint, String destination, Double length, Double price) {
		super(startpoint, destination, length, price);
	}

	@SuppressWarnings("DuplicatedCode")
	@Override
	public String getText() {
		int lineLength = "#===================================================================#".length();

		int startpointFillLength  = lineLength - "# Ticket von:   #".length() - getStartpoint().length();
		int destinationFillLength = lineLength - "#        nach:  #".length() - getDestination().length();

		String price = getPrice().toString();
		int priceFillLength = lineLength - "# Preis:           € #".length() - price.length();
		String date = getDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT));
		int dateFillLength = lineLength - "# Datum:           #".length() - date.length();
		int lengthFillLength = lineLength - "# Fahrtkilometer:  #".length() - getLength().toString().length();

		return  "#===================================================================#" +
				"#                  G Ü N S T I G E R - R E I S E N                  #" +
				"#                                                                   #" +
				"# Ticket von:  " + getStartpoint() + " ".repeat(startpointFillLength) + "#" +
				"#        nach: " + getDestination() + " ".repeat(destinationFillLength) + "#" +
				"#                                                                   #" +
				"# ----------------------------------------------------------------- #" +
				"#                                                                   #" +
				"# Preisklasse:    GünstigerReisen-Tarif                             #" +
				"# Preis:          " + price + " €" + " ".repeat(priceFillLength) + "#" +
				"# Datum:          " + date + " ".repeat(dateFillLength) + "#" +
				"# Fahrtkilometer: " + getLength() + " ".repeat(lengthFillLength) + "#" +
				"#                                                                   #" +
				"# Hinweis: Ticket gilt nur für den gebuchten Zug und ist nicht      #" +
				"# übertragbar. Stornierung gegen den halben Ticketpreis möglich.    #" +
				"#                                                                   #" +
				"#===================================================================#";
	}
}

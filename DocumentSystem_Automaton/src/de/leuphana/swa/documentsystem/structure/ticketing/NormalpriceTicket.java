package de.leuphana.swa.documentsystem.structure.ticketing;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * @author Lennart_Admin
 */
public class NormalpriceTicket extends TicketDocumentTemplate {
	public NormalpriceTicket(String startpoint, String destination, Double length, Double price) {
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
				"#                  N  O  R  M  A  L  P  R  E  I  S                  #" +
				"#                                                                   #" +
				"# Ticket von:  " + getStartpoint() + " ".repeat(startpointFillLength) + "#" +
				"#        nach: " + getDestination() + " ".repeat(destinationFillLength) + "#" +
				"#                                                                   #" +
				"# ----------------------------------------------------------------- #" +
				"#                                                                   #" +
				"# Preisklasse:    Normal-Tarif                                      #" +
				"# Preis:          " + price + " €" + " ".repeat(priceFillLength) + "#" +
				"# Datum:          " + date + " ".repeat(dateFillLength) + "#" +
				"# Fahrtkilometer: " + getLength() + " ".repeat(lengthFillLength) + "#" +
				"#                                                                   #" +
				"#===================================================================#";
	}
}

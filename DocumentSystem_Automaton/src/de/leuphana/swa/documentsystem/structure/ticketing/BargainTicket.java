package de.leuphana.swa.documentsystem.structure.ticketing;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * @author Lennart_Admin
 */
public class BargainTicket extends TicketDocumentTemplate {
	public BargainTicket(String startpoint, String destination, Double length, Double price) {
		super(startpoint, destination, length, price);
	}

	@SuppressWarnings("DuplicatedCode")
	@Override
	public String getText() {
		int lineLength = "#===================================================================#".length();

		int startpointFillLength  = lineLength - "# Ticket von:  #".length() - getStartpoint().length();
		int destinationFillLength = lineLength - "#        nach: #".length() - getDestination().length();

		String price = getPrice().toString();
		int priceFillLength = lineLength - "# Preis:           €#".length() - price.length();
		String date = getDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT));
		int dateFillLength = lineLength - "# Datum:          #".length() - date.length();
		int lengthFillLength = lineLength - "# Fahrtkilometer: #".length() - getLength().toString().length();

		return  "#===================================================================#\n" +
				"#                  S C N Ä P P C H E N - T A R I F                  #\n" +
				"#                                                                   #\n" +
				"# Ticket von:  " + getStartpoint() + " ".repeat(startpointFillLength) + "#\n" +
				"#        nach: " + getDestination() + " ".repeat(destinationFillLength) + "#\n" +
				"#                                                                   #\n" +
				"# ----------------------------------------------------------------- #\n" +
				"#                                                                   #\n" +
				"# Preisklasse:    Schnäppchen-Tarif                                 #\n" +
				"# Preis:          " + price + " €" + " ".repeat(priceFillLength) + "#\n" +
				"# Datum:          " + date + " ".repeat(dateFillLength) + "#\n" +
				"# Fahrtkilometer: " + getLength() + " ".repeat(lengthFillLength) + "#\n" +
				"#                                                                   #\n" +
				"# Hinweis: Ticket gilt nur für den gebuchten Zug und ist nicht      #\n" +
				"# übertragbar. Stornierung ausgeschlossen.                          #\n" +
				"#                                                                   #\n" +
				"#===================================================================#\n";
	}
}

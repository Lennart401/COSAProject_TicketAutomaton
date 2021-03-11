package de.leuphana.swa.documentsystem.behaviour.service.event;

import de.leuphana.swa.documentsystem.behaviour.service.DocumentCommandService;
import de.leuphana.swa.documentsystem.structure.ticketing.TicketDocumentTemplate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

/**
 * @author Lennart_Admin
 */
public class DocumentEventHandler implements EventHandler {

	private DocumentCommandService service;
	private Logger logger;

	public DocumentEventHandler(DocumentCommandService service) {
		this.service = service;
		this.logger = LogManager.getLogger(this.getClass());
	}

	@Override
	public void handleEvent(Event event) {
		logger.debug("Receiving event: " + event.getTopic());

		if (event.getTopic().equals("de/leuphana/cosa/document/CREATE_TICKET")) {
			String startpoint = (String) event.getProperty("startpoint");
			String destination = (String) event.getProperty("destination");
			Double length = (Double) event.getProperty("length");
			String priceGroup = (String) event.getProperty("pricegroup");
			Double price = (Double) event.getProperty("price");

			TicketDocumentTemplate ticket = service.createTicket(startpoint, destination, length, priceGroup, price);
			service.addDocument(ticket);
		}
	}
}

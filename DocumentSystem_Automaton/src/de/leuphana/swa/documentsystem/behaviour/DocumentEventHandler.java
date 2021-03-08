package de.leuphana.swa.documentsystem.behaviour;

import de.leuphana.swa.documentsystem.behaviour.service.DocumentCommandService;
import de.leuphana.swa.documentsystem.structure.ticketing.TicketDocumentTemplate;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.event.EventHandler;

/**
 * @author Lennart_Admin
 */
public class DocumentEventHandler implements EventHandler {

	private DocumentCommandService service;
	// do we even need this?
	private EventAdmin eventAdmin;

	public DocumentEventHandler(DocumentCommandService service, BundleContext context) {
		this.service = service;

		ServiceReference ref = context.getServiceReference(EventAdmin.class.getName());
		if (ref != null) {
			eventAdmin = (EventAdmin) context.getService(ref);
		} else {
			System.err.println("DocumentSystem: no EventAdmin-Service found!");
		}
	}

	@Override
	public void handleEvent(Event event) {
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

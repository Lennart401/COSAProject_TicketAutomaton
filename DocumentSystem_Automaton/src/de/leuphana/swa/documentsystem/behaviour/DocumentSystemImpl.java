package de.leuphana.swa.documentsystem.behaviour;

import java.util.*;

import de.leuphana.swa.documentsystem.behaviour.service.event.DocumentEventHandler;
import de.leuphana.swa.documentsystem.structure.ticketing.BargainTicket;
import de.leuphana.swa.documentsystem.structure.ticketing.CheaperTravelTicket;
import de.leuphana.swa.documentsystem.structure.ticketing.NormalpriceTicket;
import de.leuphana.swa.documentsystem.structure.ticketing.TicketDocumentTemplate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.leuphana.swa.documentsystem.behaviour.service.DocumentCommandService;
import de.leuphana.swa.documentsystem.structure.Document;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;

public class DocumentSystemImpl implements DocumentCommandService, BundleActivator {
	// Java Collection classes
	// Interface (Was? - 1): List, Set, Map, Queue
	// Realisierung: (Wie? - N): ArrayList, LinkedList / HashMap, TreeMap
	private Map<Integer, Document> documents;

	private Logger logger;
	private EventAdmin eventAdmin;
	private ServiceReference eventAdminRef;

	public DocumentSystemImpl() {
		// Was? / Interface = Wie? / Realisierung
		documents = new HashMap<Integer, Document>();
	}

	@Override
	public void start(BundleContext context) throws Exception {
		logger = LogManager.getLogger(this.getClass().getName());
		System.out.println("Starting DocumentSystem");
		logger.info("DocumentSystem - Test");

		// Register event handler
		String[] topics = new String[] {
				"de/leuphana/cosa/document/CREATE_TICKET"
		};

		Dictionary<String, Object> eventHandlerProps = new Hashtable<>();
		eventHandlerProps.put(EventConstants.EVENT_TOPIC, topics);
		context.registerService(EventHandler.class.getName(), new DocumentEventHandler(this), eventHandlerProps);

		// get EventAdmin
		eventAdminRef = context.getServiceReference(EventAdmin.class.getName());
		if (eventAdminRef != null) {
			eventAdmin = (EventAdmin) context.getService(eventAdminRef);
		} else {
			System.err.println("DocumentSystem: no EventAdmin-Service found!");
		}
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		context.ungetService(eventAdminRef);
	}

	@Override
	public Boolean addDocument(Document document) {
		documents.put(document.getId(), document);

		logger.info("Document with title " + document.getTitle() + " added!");

		return documents.containsKey(document.getId());
	}

	@Override
	public Document createDocument(String title) {
		Document document = new Document(title);

		logger.info("Document : " + title + " created!");

		sendDocumentCreatedEvent(document);
		return document;
	}

	@Override
	public TicketDocumentTemplate createTicket(String startpoint, String destination, Double length, String priceGroup, Double price) {
		TicketDocumentTemplate ticket = switch (priceGroup) {
			case "Normal-Tarif" -> new NormalpriceTicket(startpoint, destination, length, price);
			case "GünstigerReisen-Tarif" -> new CheaperTravelTicket(startpoint, destination, length, price);
			case "Schnäppchen-Tarif" -> new BargainTicket(startpoint, destination, length, price);

			default -> throw new IllegalStateException("Unexpected value: " + priceGroup);
		};

		sendDocumentCreatedEvent(ticket);
		logger.info(ticket.getTitle() + " sold! Route: " + startpoint + " to " + destination + " (" + length + " km) " +
				"PriceGroup: " + priceGroup + ", Price: " + price + " €");

		return ticket;
	}

	private void sendDocumentCreatedEvent(Document document) {
		System.out.println("sending document created event for document " + document.getTitle());
		if (eventAdmin != null) {
			Dictionary<String, Object> props = new Hashtable<>();
			props.put("title", document.getTitle());
			props.put("content", document.getText());

			Event event = new Event("de/leuphana/cosa/document/DOCUMENT_ADDED", props);
			eventAdmin.sendEvent(event);
		} else {
			System.err.println("DocumentSystem: Cannot send event due to missing EventAdmin service!");
		}
	}
}

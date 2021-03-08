package de.leuphana.swa.documentsystem.behaviour;

import java.util.*;

import de.leuphana.swa.documentsystem.structure.ticketing.BargainTicket;
import de.leuphana.swa.documentsystem.structure.ticketing.CheaperTravelTicket;
import de.leuphana.swa.documentsystem.structure.ticketing.NormalpriceTicket;
import de.leuphana.swa.documentsystem.structure.ticketing.TicketDocumentTemplate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.leuphana.cosa.component.Component;
import de.leuphana.swa.documentsystem.behaviour.service.DocumentCommandService;
import de.leuphana.swa.documentsystem.behaviour.service.Manageable;
import de.leuphana.swa.documentsystem.behaviour.service.event.ManageableEvent;
import de.leuphana.swa.documentsystem.behaviour.service.event.ManageableEventListener;
import de.leuphana.swa.documentsystem.behaviour.service.event.ManageableEventService;
import de.leuphana.swa.documentsystem.structure.Document;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;

public class DocumentSystemImpl implements DocumentCommandService, ManageableEventService, Component, BundleActivator {
	// Java Collection classes
	// Interface (Was? - 1): List, Set, Map, Queue
	// Realisierung: (Wie? - N): ArrayList, LinkedList / HashMap, TreeMap
	private Map<Integer, Document> documents;

	// TODO remove
	private Set<ManageableEventListener> manageableEventListeners;

	private Logger logger;
	private EventAdmin eventAdmin;

	public DocumentSystemImpl() {
		// TODO remove
		manageableEventListeners = new HashSet<ManageableEventListener>();

		// Was? / Interface = Wie? / Realisierung
		documents = new HashMap<Integer, Document>();
		logger = LogManager.getLogger(this.getClass());
	}

	@Override
	public void start(BundleContext context) throws Exception {
		System.out.println("Starting DocumentSystem");

		// Register event handler
		String[] topics = new String[] {
				"de/leuphana/cosa/pricing/CREATE_TICKET"
		};

		Dictionary<String, Object> eventHandlerProps = new Hashtable<>();
		eventHandlerProps.put(EventConstants.EVENT_TOPIC, topics);
		context.registerService(EventHandler.class.getName(), new DocumentEventHandler(this, context), eventHandlerProps);

		// get EventAdmin
		ServiceReference ref = context.getServiceReference(EventAdmin.class.getName());
		if (ref != null) {
			eventAdmin = (EventAdmin) context.getService(ref);
		} else {
			System.err.println("DocumentSystem: no EventAdmin-Service found!");
		}
	}

	@Override
	public void stop(BundleContext context) throws Exception {
	}

	@Override
	public Boolean addDocument(Document document) {
		documents.put(document.getId(), document);

		logger.info("Document with title " + document.getTitle() + " added!");

		return documents.containsKey(document.getId());
	}

	@Override
	public Document createDocument(String title) {
		Manageable manageable = new Manageable() {

			@Override
			public String getTitle() {
				return title;
			}

			@Override
			public String getContent() {
				return "";
			}
			
		};
		
		Document document = new Document(title);
		
		logger.info("Document : " + title + " created!");

		// TODO remove
		// TODO Refactor into seperate method
		ManageableEvent manageableEvent = new ManageableEvent(manageable);
		
		for (ManageableEventListener manageableEventListener : manageableEventListeners) {
			manageableEventListener.onManageableCreated(manageableEvent);
		}
		
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

		return ticket;
	}

	private void sendDocumentCreatedEvent(Document document) {
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

	// TODO remove
	@Override
	public void addManageableEventListener(ManageableEventListener manageableEventListener) {
		manageableEventListeners.add(manageableEventListener);
	}

	// TODO remove
	@Override
	public void removeManageableEventListener(ManageableEventListener manageableEventListener) {
		manageableEventListeners.remove(manageableEventListener);
	}

	// TODO remove
	@Override
	public String getCommandServiceName() {
		return DocumentCommandService.class.getName();
	}

	// TODO remove
	@Override
	public String getEventServiceName() {
		return ManageableEventService.class.getName();
	}

	// TODO remove
	@Override
	public String getCommandServicePath() {
		return DocumentCommandService.class.getPackageName();
	}

	// TODO remove
	@Override
	public String getEventServicePath() {
		return ManageableEventService.class.getPackageName();
	}

	// TODO remove
	@Override
	public String getComponentName() {
		return "DocumentSystem";
	}

}

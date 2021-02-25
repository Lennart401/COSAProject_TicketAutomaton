package de.leuphana.swa.documentsystem.behaviour;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.leuphana.cosa.component.Component;
import de.leuphana.swa.documentsystem.behaviour.service.DocumentCommandService;
import de.leuphana.swa.documentsystem.behaviour.service.Manageable;
import de.leuphana.swa.documentsystem.behaviour.service.event.ManageableEvent;
import de.leuphana.swa.documentsystem.behaviour.service.event.ManageableEventListener;
import de.leuphana.swa.documentsystem.behaviour.service.event.ManageableEventService;
import de.leuphana.swa.documentsystem.structure.Document;

public class DocumentSystemImpl implements DocumentCommandService, ManageableEventService, Component {
	// Java Collection classes
	// Interface (Was? - 1): List, Set, Map, Queue
	// Realisierung: (Wie? - N): ArrayList, LinkedList / HashMap, TreeMap
	private Map<Integer, Document> documents;
	private Set<ManageableEventListener> manageableEventListeners;

	private Logger logger;

	public DocumentSystemImpl() {
		manageableEventListeners = new HashSet<ManageableEventListener>();

		// Was? / Interface = Wie? / Realisierung
		documents = new HashMap<Integer, Document>();
		logger = LogManager.getLogger(this.getClass());
	}

	@Override
	public Boolean addDocument(Document document) {
		documents.put(document.getId(), document);

		logger.info("Document with title " + document.getTitel() + " added!");

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
		
		// TODO Refactor into seperate method
		ManageableEvent manageableEvent = new ManageableEvent(manageable);
		
		for (ManageableEventListener manageableEventListener : manageableEventListeners) {
			manageableEventListener.onManageableCreated(manageableEvent);
		}
		
		return document;
	}

	@Override
	public void addManageableEventListener(ManageableEventListener manageableEventListener) {
		manageableEventListeners.add(manageableEventListener);
	}

	@Override
	public void removeManageableEventListener(ManageableEventListener manageableEventListener) {
		manageableEventListeners.remove(manageableEventListener);
	}

	@Override
	public String getCommandServiceName() {
		return DocumentCommandService.class.getName();
	}

	@Override
	public String getEventServiceName() {
		return ManageableEventService.class.getName();
	}

	@Override
	public String getCommandServicePath() {
		return DocumentCommandService.class.getPackageName();
	}

	@Override
	public String getEventServicePath() {
		return ManageableEventService.class.getPackageName();
	}

	@Override
	public String getComponentName() {
		return "DocumentSystem";
	}
	
}

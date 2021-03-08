package de.leuphana.swa.documentsystem.behaviour.service;

import de.leuphana.swa.documentsystem.structure.Document;
import de.leuphana.swa.documentsystem.structure.ticketing.TicketDocumentTemplate;

public interface DocumentCommandService  {

	Document createDocument(String title);
	TicketDocumentTemplate createTicket(String startpoint, String destination, Double length, String priceGroup, Double price);
	Boolean addDocument(Document document);
	//TODO to implement
//	void updateDocument(Document document);
//	Document getDocument(Integer documentId) throws DocumentNotFoundException;

}
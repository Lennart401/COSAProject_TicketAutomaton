package de.leuphana.swa.documentsystem.behaviour;

import de.leuphana.swa.documentsystem.structure.ticketing.BargainTicket;
import de.leuphana.swa.documentsystem.structure.ticketing.CheaperTravelTicket;
import de.leuphana.swa.documentsystem.structure.ticketing.NormalpriceTicket;
import de.leuphana.swa.documentsystem.structure.ticketing.TicketDocumentTemplate;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import de.leuphana.swa.documentsystem.behaviour.service.DocumentCommandService;
import de.leuphana.swa.documentsystem.structure.Document;

import static org.junit.jupiter.api.Assertions.*;

class DocumentSystemTest {

	private static DocumentCommandService documentSystem;
	private static Document document;

	private static String startpoint;
	private static String destination;
	private static Double length;
	private static Double price;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		documentSystem = new DocumentSystemImpl();
		document = new Document("New document");

		startpoint = "Hamburg";
		destination = "Harburg";
		length = 300.0;
		price = 9.0;
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		documentSystem = null;
		document = null;
	}

	@Test
	void canDocumentBeAdded() {
		assertTrue(documentSystem.addDocument(document));
	}

	@Test
	void canDocumentBeCreated() {
		assertNotNull(documentSystem.createDocument("New document"));
	}

	@Test
	void canTicketsBeCreated() {
		TicketDocumentTemplate normalPriceTicket = documentSystem.createTicket(startpoint, destination, length, "Normal-Tarif", price);
		TicketDocumentTemplate cheaperTravelTicket = documentSystem.createTicket(startpoint, destination, length, "GünstigerReisen-Tarif", price);
		TicketDocumentTemplate bargainTicket = documentSystem.createTicket(startpoint, destination, length, "Schnäppchen-Tarif", price);

		assertTrue(normalPriceTicket instanceof NormalpriceTicket);
		assertTrue(cheaperTravelTicket instanceof CheaperTravelTicket);
		assertTrue(bargainTicket instanceof BargainTicket);
	}
}

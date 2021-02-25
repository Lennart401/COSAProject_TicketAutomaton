package de.leuphana.swa.documentsystem.behaviour;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import de.leuphana.swa.documentsystem.behaviour.service.DocumentCommandService;
import de.leuphana.swa.documentsystem.structure.Document;

class DocumentSystemTest {

	private static DocumentCommandService documentSystem;
	private static Document document;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		documentSystem = new DocumentSystemImpl();
		document = new Document("New document");
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		documentSystem = null;
		document = null;
	}

	@Test
	void canDocumentBeAdded() {
		Assertions.assertTrue(documentSystem.addDocument(document));
	}

}

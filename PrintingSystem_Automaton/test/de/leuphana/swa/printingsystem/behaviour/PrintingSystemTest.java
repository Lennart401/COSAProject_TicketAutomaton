package de.leuphana.swa.printingsystem.behaviour;

import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import de.leuphana.swa.printingsystem.behaviour.service.PrintConfiguration;
import de.leuphana.swa.printingsystem.behaviour.service.Printable;
import de.leuphana.swa.printingsystem.behaviour.service.PrintingCommandService;

class PrintingSystemTest {

	private static PrintingCommandService printingSystem;
	private static PrintConfiguration printConfiguration;
	private static Printable printable;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		printingSystem = new PrintingSystemImpl();
		
		printable = new Printable() {
			
			@Override
			public String getContent() {
				return "This is a content!";
			}

			@Override
			public String getTitle() {
				return "New Title";
			}
		};
		
		printConfiguration = new PrintConfiguration() {
			String printFormat;

			@Override
			public void setPrintFormat(String printFormat) {
				this.printFormat = printFormat;
			}

			@Override
			public String getPrintFormat() {
				return printFormat;
			}
		};
		
		Set<String > supportedPrintFormats = printingSystem.getSupportedPrintFormats();
		
		// Ausgabe durch GUI und Auswahl durch Nutzer eines Wertes (String)
		
		printConfiguration.setPrintFormat("A4");
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		printingSystem = null;
		printConfiguration = null;
	}

	@Test
	void canDocumentBePrinted() {
		Assertions.assertTrue(printingSystem.printDocument(printable, printConfiguration).isPrintSuccessful());
	}

}

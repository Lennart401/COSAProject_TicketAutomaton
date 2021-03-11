package de.leuphana.swa.printingsystem.behaviour.service.event;

import de.leuphana.swa.printingsystem.behaviour.service.PrintConfiguration;
import de.leuphana.swa.printingsystem.behaviour.service.Printable;
import de.leuphana.swa.printingsystem.behaviour.service.PrintingCommandService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

import java.util.Set;

/**
 * @author Lennart_Admin
 */
public class PrintingEventHandler implements EventHandler {

	private PrintingCommandService service;

	private Logger logger;

	public PrintingEventHandler(PrintingCommandService service) {
		this.service = service;
		this.logger = LogManager.getLogger(this.getClass());
	}

	@Override
	public void handleEvent(Event event) {
		logger.debug("Receiving event: " + event.getTopic());

		// Print document if it was added
		if (event.getTopic().equals("de/leuphana/cosa/document/DOCUMENT_ADDED")) {
			String title = (String) event.getProperty("title");
			String content = (String) event.getProperty("content");

			Printable printable = new Printable() {
				@Override
				public String getTitle() {
					return title;
				}

				@Override
				public String getContent() {
					return content;
				}
			};

			PrintConfiguration printConfiguration = new PrintConfiguration() {
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

			Set<String> printFormats = service.getSupportedPrintFormats();

			// TODO check if A4 print format actually exists and print and error otherwise
			printConfiguration.setPrintFormat("A4");

			service.printDocument(printable, printConfiguration);
		}
	}
}

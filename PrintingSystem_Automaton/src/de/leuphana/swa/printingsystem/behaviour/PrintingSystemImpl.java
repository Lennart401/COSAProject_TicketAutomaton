package de.leuphana.swa.printingsystem.behaviour;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import de.leuphana.swa.printingsystem.behaviour.service.PrintConfiguration;
import de.leuphana.swa.printingsystem.behaviour.service.PrintReport;
import de.leuphana.swa.printingsystem.behaviour.service.Printable;
import de.leuphana.swa.printingsystem.behaviour.service.PrintingCommandService;
import de.leuphana.swa.printingsystem.behaviour.service.event.PrintingEventHandler;
import de.leuphana.swa.printingsystem.structure.PrintFormat;
import de.leuphana.swa.printingsystem.structure.PrintJob;
import de.leuphana.swa.printingsystem.structure.PrintJobQueue;
import de.leuphana.swa.printingsystem.structure.Printer;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;

public class PrintingSystemImpl implements PrintingCommandService, BundleActivator {
	// Interfaces
	// Collection (Sammlung von Objekten)(Was?) ==> Set[keine doppelten Objekte],
	// List[kann auch doppelten Objekte enthalten], Map[organisiert nach
	// Schlüssel/Wert-Prinzip], Queue[Reihenfolge der Objekte spielt eine Rolle]
	// (Wie? Unterschied: NFR's)

	// Was? / Interface
//	private Map<DocumentFormat, Printer> printers;
	private PrintJobQueue printJobQueue;

	private EventAdmin eventAdmin;
	private ServiceReference eventAdminRef;

	public PrintingSystemImpl() {
		// Wie? / konkrete Klasse
//		printers = new HashMap<DocumentFormat, Printer>();
//		printers.put();
//		printers.put(DocumentFormat.A5, new Printer(DocumentFormat.A5));

		printJobQueue = PrintJobQueue.getInstance();
		printJobQueue.addPrinter(new Printer(PrintFormat.A4));
		printJobQueue.addPrinter(new Printer(PrintFormat.A3));
	}

	@Override
	public void start(BundleContext context) throws Exception {
		System.out.println("Starting PrintingSystem");

		// get EventAdmin
		eventAdminRef = context.getServiceReference(EventAdmin.class.getName());
		if (eventAdminRef != null) {
			eventAdmin = (EventAdmin) context.getService(eventAdminRef);
		} else {
			System.err.println("PrintingSystem: no EventAdmin-Service found!");
		}

		String[] topics = new String[] {
				"de/leuphana/cosa/document/DOCUMENT_ADDED"
		};

		Dictionary<String, Object> eventHandlerProps = new Hashtable<>();
		eventHandlerProps.put(EventConstants.EVENT_TOPIC, topics);
		context.registerService(EventHandler.class.getName(), new PrintingEventHandler(this), eventHandlerProps);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		context.ungetService(eventAdminRef);
	}

	@Override
	public PrintReport printDocument(Printable printable, PrintConfiguration printConfiguration) {
		// TODO später in der Queue prüfen, ob DocumentFormat korrekt ist
		// TODO prüfen, über Queue, ob PrintJob auch gedruckt wurde (Exceptions)
		PrintJob printJob = new PrintJob(printable, printConfiguration);
		// Action
		printJobQueue.addPrintJob(printJob);

		PrintReport printReport = new PrintReport();
		printReport.setConfirmationText(printable.getContent());
		printReport.setPrintDate(LocalDate.now());
		printReport.setPrintSuccessful(true);

		sendPrintableEvent(printReport);
		return printReport;
	}

	private void sendPrintableEvent(PrintReport report) {
		Dictionary<String, Object> props = new Hashtable<>();
		props.put("isPrintSuccessful", report.isPrintSuccessful());
		props.put("printDate", report.getPrintDate());
		props.put("confirmationText", report.getConfirmationText());
		props.put("price", report.getPrice());

		Event event = new Event("de/leuphana/cosa/printing/PRINT_REPORT", props);
		eventAdmin.sendEvent(event);
	}

	@Override
	public Set<String> getSupportedPrintFormats() {
		// Pipe&Filter
		// transformation
		return Arrays.stream(PrintFormat.values()).map(Enum::name).collect(Collectors.toSet());
	}
}

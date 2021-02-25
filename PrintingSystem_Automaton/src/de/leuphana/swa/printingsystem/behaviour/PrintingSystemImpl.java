package de.leuphana.swa.printingsystem.behaviour;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import de.leuphana.cosa.component.Component;
import de.leuphana.swa.printingsystem.behaviour.service.PrintConfiguration;
import de.leuphana.swa.printingsystem.behaviour.service.PrintReport;
import de.leuphana.swa.printingsystem.behaviour.service.Printable;
import de.leuphana.swa.printingsystem.behaviour.service.PrintingCommandService;
import de.leuphana.swa.printingsystem.behaviour.service.event.PrintableEvent;
import de.leuphana.swa.printingsystem.behaviour.service.event.PrintableEventListener;
import de.leuphana.swa.printingsystem.behaviour.service.event.PrintableEventService;
import de.leuphana.swa.printingsystem.structure.PrintFormat;
import de.leuphana.swa.printingsystem.structure.PrintJob;
import de.leuphana.swa.printingsystem.structure.PrintJobQueue;
import de.leuphana.swa.printingsystem.structure.Printer;

public class PrintingSystemImpl implements PrintingCommandService, PrintableEventService, Component {
	// Interfaces
	// Collection (Sammlung von Objekten)(Was?) ==> Set[keine doppelten Objekte],
	// List[kann auch doppelten Objekte enthalten], Map[organisiert nach
	// Schlüssel/Wert-Prinzip], Queue[Reihenfolge der Objekte spielt eine Rolle]
	// (Wie? Unterschied: NFR's)

	// Was? / Interface
//	private Map<DocumentFormat, Printer> printers;
	private PrintJobQueue printJobQueue;
	private Set<PrintableEventListener> printableEventListeners;

	public PrintingSystemImpl() {
		printableEventListeners = new HashSet<PrintableEventListener>();

		// Wie? / konkrete Klasse
//		printers = new HashMap<DocumentFormat, Printer>();
//		printers.put();
//		printers.put(DocumentFormat.A5, new Printer(DocumentFormat.A5));

		printJobQueue = PrintJobQueue.getInstance();
		printJobQueue.addPrinter(new Printer(PrintFormat.A4));
		printJobQueue.addPrinter(new Printer(PrintFormat.A3));
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
		
		// TODO Refactor into seperate method
		PrintableEvent printableEvent = new PrintableEvent(printReport);

		for (PrintableEventListener printableEventListener : printableEventListeners) {
			printableEventListener.onPrintableExcuted(printableEvent);
		}

		return printReport;
	}

	@Override
	public Set<String> getSupportedPrintFormats() {
		// transformation
//		Set<String> supportedPrintFormatAsString = new HashSet<String>();
//		
//		for (PrintFormat printFormat : PrintFormat.values()) {
//			supportedPrintFormatAsString.add(printFormat.toString());
//		}

//		return supportedPrintFormatAsString;

		// Pipe&Filter
		// transformation
		return Arrays.stream(PrintFormat.values()).map(Enum::name).collect(Collectors.toSet());
	}

	@Override
	public String getCommandServiceName() {
		return PrintingCommandService.class.getName();
	}

	@Override
	public String getEventServiceName() {
		return PrintableEventService.class.getName();
	}

	@Override
	public String getCommandServicePath() {
		return PrintingCommandService.class.getPackageName();
	}

	@Override
	public String getEventServicePath() {
		return PrintableEventService.class.getPackageName();
	}

	@Override
	public String getComponentName() {
		return "PrintingSystem";
	}

	@Override
	public void addPrintableEventListener(PrintableEventListener printableEventListener) {
		printableEventListeners.add(printableEventListener);
	}

	@Override
	public void removePrintableEventListener(PrintableEventListener printableEventListener) {
		printableEventListeners.remove(printableEventListener);
	}

}

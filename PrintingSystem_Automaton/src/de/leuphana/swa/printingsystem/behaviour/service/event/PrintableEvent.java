package de.leuphana.swa.printingsystem.behaviour.service.event;

import java.util.EventObject;

import de.leuphana.swa.printingsystem.behaviour.service.PrintReport;

public class PrintableEvent extends EventObject {
	private PrintReport printReport;

	public PrintableEvent(PrintReport printReport) {
		super(printReport);
		this.printReport = printReport;
	}

	public PrintReport getPrintReport() {
		return printReport;
	}

}
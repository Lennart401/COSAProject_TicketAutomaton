package de.leuphana.swa.printingsystem.behaviour.service.event;

public interface PrintableEventService {
	void addPrintableEventListener(PrintableEventListener printableEventListener);
	void removePrintableEventListener(PrintableEventListener printableEventListener);
}
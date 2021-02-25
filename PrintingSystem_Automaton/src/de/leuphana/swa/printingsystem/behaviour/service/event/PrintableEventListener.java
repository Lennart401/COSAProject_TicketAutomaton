package de.leuphana.swa.printingsystem.behaviour.service.event;

import java.util.EventListener;

public interface PrintableEventListener extends EventListener {
	void onPrintableExcuted(PrintableEvent printableEvent);
//	void onPrintablePaused(PrintableEvent printableEvent);
//	void onPrintableDeleted(PrintableEvent printableEvent);
}

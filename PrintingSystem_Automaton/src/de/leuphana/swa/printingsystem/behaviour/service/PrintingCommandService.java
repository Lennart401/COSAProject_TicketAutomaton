package de.leuphana.swa.printingsystem.behaviour.service;

import java.util.Set;

import de.leuphana.swa.printingsystem.behaviour.service.exceptions.UnsupportedPrintFormatException;

public interface PrintingCommandService  {

	// Signature
	PrintReport printDocument(Printable printable, PrintConfiguration printConfiguration) throws UnsupportedPrintFormatException;
	
	Set<String> getSupportedPrintFormats();

}
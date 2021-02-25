package de.leuphana.swa.printingsystem.structure;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class PrintJobQueue {
	// Interface / Was?
	private static PrintJobQueue printJobQueue;
	
	private Queue<PrintJob> queue;
	private Map<PrintFormat, Printer> printersMap;

	private PrintJobQueue() {
//		queue = new PriorityQueue<PrintJob>(100);
		queue = new LinkedList<PrintJob>();
		printersMap = new HashMap<PrintFormat, Printer>();
	}

	/**
	 * 
	 * Singleton-Pattern
	 * getInstance() kann n-mal aufgerufen werden
	 * 
	 * @return
	 */
	public static PrintJobQueue getInstance() {
		// konkrete Klasse / Was?
		if (printJobQueue == null) {
			printJobQueue = new PrintJobQueue();
		}

		return printJobQueue;
	}

	public void addPrinter(Printer printer) {
		printersMap.put(printer.getPrintFormat(), printer);
	}
	
	public void addPrintJob(PrintJob printJob) {
		queue.add(printJob);
		printJob.changePrintJobState(PrintJobAction.QUEUE);
		
		// TODO eigene Methode, die nach einer gewissen Zeit prüft, ob ein PrintJob in der Queue ist
		PrintJob lastPrintJob = queue.poll();
		if(lastPrintJob != null) {
			
			// transformation from String to PrintFormat
			
			PrintFormat printFormat = PrintFormat.valueOf(lastPrintJob.getConfiguration().getPrintFormat());
				
			// 
//			switch (lastPrintJob.getConfiguration().getPrintFormat()) {
//			case "A3": {
//				printFormat = PrintFormat.A3;
//			}
//			default:
//				throw new IllegalArgumentException("Unexpected value: " + lastPrintJob.getConfiguration().getPrintFormat());
//			}
			
			Printer selectedPrinter = printersMap.get(printFormat);
			selectedPrinter.executePrintJob(lastPrintJob);
		}
	}

}

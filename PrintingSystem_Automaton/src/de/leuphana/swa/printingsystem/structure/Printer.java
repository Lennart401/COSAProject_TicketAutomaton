package de.leuphana.swa.printingsystem.structure;

public class Printer {
	private PrintFormat printFormat;

	public Printer(PrintFormat printFormat) {
		this.printFormat = printFormat;
	}

	public PrintFormat getPrintFormat() {
		return printFormat;
	}

	public boolean executePrintJob(PrintJob printJob) {
		// Simulation of printing
		printJob.changePrintJobState(PrintJobAction.PRINT);

		// Print to console
		System.out.println(printJob.getPrintable().getTitle() + "\n\n" + printJob.getPrintable().getContent());

		return true;
	}
}

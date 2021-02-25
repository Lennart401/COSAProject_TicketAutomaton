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

		return true;
	}
}

package de.leuphana.swa.printingsystem.structure;

public class CreatedPrintJobState extends PrintJobState {
	
	public CreatedPrintJobState() {
		super();
	}

	@Override
	public PrintJobState changePrintJobState(PrintJobAction printJobAction) {
		PrintJobState printJobState = this;
		
		switch (printJobAction) {
		case QUEUE: {
			printJobState = new QueuedPrintJobState();
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + printJobAction);
		}
		
		return printJobState;
	}

}

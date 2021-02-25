package de.leuphana.swa.printingsystem.structure;

public class QueuedPrintJobState extends PrintJobState {
	
	public QueuedPrintJobState() {
		super();
	}

	@Override
	public PrintJobState changePrintJobState(PrintJobAction printJobAction) {
		PrintJobState printJobState = this;

		switch (printJobAction) {
		case PRINT: {
			printJobState = new PrintedPrintJobState();
			break;
		}
		case STOP: {
			printJobState = new StoppedPrintJobState();
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + printJobAction);
		}

		return printJobState;
	}

}

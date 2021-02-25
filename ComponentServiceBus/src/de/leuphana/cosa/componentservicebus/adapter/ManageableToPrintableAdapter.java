package de.leuphana.cosa.componentservicebus.adapter;

import de.leuphana.swa.documentsystem.behaviour.service.Manageable;
import de.leuphana.swa.documentsystem.behaviour.service.event.ManageableEvent;
import de.leuphana.swa.documentsystem.behaviour.service.event.ManageableEventListener;
import de.leuphana.swa.documentsystem.behaviour.service.event.ManageableEventService;
import de.leuphana.swa.printingsystem.behaviour.service.PrintConfiguration;
import de.leuphana.swa.printingsystem.behaviour.service.Printable;
import de.leuphana.swa.printingsystem.behaviour.service.PrintingCommandService;

public class ManageableToPrintableAdapter implements ManageableEventListener {
	
	private PrintingCommandService printingCommandService;
	
	public ManageableToPrintableAdapter(ManageableEventService manageableEventService, PrintingCommandService printingCommandService) {
		manageableEventService.addManageableEventListener(this);
		this.printingCommandService = printingCommandService;
	}

	@Override
	public void onManageableCreated(ManageableEvent manageableEvent) {
		Manageable manageable = manageableEvent.getMangeable();
		
		Printable printable = new Printable() {
			
			@Override
			public String getTitle() {
				return manageable.getTitle();
			}
			
			@Override
			public String getContent() {
				return manageable.getContent();
			}
		};
		
		PrintConfiguration printConfiguration = new PrintConfiguration() {
			String printFormat;

			@Override
			public void setPrintFormat(String printFormat) {
				this.printFormat = printFormat;
			}

			@Override
			public String getPrintFormat() {
				return printFormat;
			}
		};
		
		printConfiguration.setPrintFormat("A4");
		
		
		printingCommandService.printDocument(printable, printConfiguration);
	}

}

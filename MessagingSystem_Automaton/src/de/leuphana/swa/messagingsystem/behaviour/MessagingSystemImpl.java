package de.leuphana.swa.messagingsystem.behaviour;

import java.time.LocalDate;
import java.util.Dictionary;
import java.util.Hashtable;

import de.leuphana.swa.messagingsystem.behaviour.service.event.MessagingEventHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.leuphana.swa.messagingsystem.behaviour.service.DeliveryReport;
import de.leuphana.swa.messagingsystem.behaviour.service.MessagingCommandService;
import de.leuphana.swa.messagingsystem.behaviour.service.Sendable;
import de.leuphana.swa.messagingsystem.structure.message.Message;
import de.leuphana.swa.messagingsystem.structure.messagingfactory.AbstractMessagingFactory;
import de.leuphana.swa.messagingsystem.structure.messagingprotocol.MessagingProtocol;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;

public class MessagingSystemImpl implements MessagingCommandService, BundleActivator {

	private Logger logger;
	private ServiceReference eventAdminRef;
	private EventAdmin eventAdmin;

	@Override
	public void start(BundleContext context) throws Exception {
		logger = LogManager.getLogger(this.getClass());
		logger.info("Starting MessagagingSystem");

		eventAdminRef = context.getServiceReference(EventAdmin.class.getName());
		if (eventAdminRef != null) {
			eventAdmin = (EventAdmin) context.getService(eventAdminRef);
		} else {
			logger.fatal("no EventAdmin-Service found!");
		}

		String[] topics = new String[] {
				"de/leuphana/cosa/document/DOCUMENT_ADDED"
		};

		Dictionary<String, Object> eventHandlerProps = new Hashtable<>();
		eventHandlerProps.put(EventConstants.EVENT_TOPIC, topics);
		context.registerService(EventHandler.class.getName(), new MessagingEventHandler(this, eventAdmin), eventHandlerProps);
	}

	@Override
	public void stop(BundleContext context) throws Exception {

	}

	@Override
	public DeliveryReport sendMessage(Sendable sendable) {
		if (logger == null) logger = LogManager.getLogger(this.getClass());

		AbstractMessagingFactory abstractMessagingFactory = AbstractMessagingFactory.getFactory(sendable.getMessageType());

		Message message = abstractMessagingFactory.createMessage(sendable.getReceiver(), sendable.getSender(), sendable.getContent());

		MessagingProtocol messageProtocol = abstractMessagingFactory.createMessagingProtocol();
		messageProtocol.open();
		messageProtocol.transfer(message);
		messageProtocol.close();

		String deliveryConfirmationText = "Message: " + sendable.getContent().split("\\R")[0] + "[...] transported via " + sendable.getMessageType();
		logger.info(deliveryConfirmationText);
		
		DeliveryReport deliveryReport = new DeliveryReport();
		deliveryReport.setConfirmationText(deliveryConfirmationText);
		deliveryReport.setDeliveryDate(LocalDate.now());
		deliveryReport.setDeliverySuccessful(true);

		return deliveryReport;
	}


}

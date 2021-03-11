package de.leuphana.cosa.ticketautomaton.behaviour;

import de.leuphana.cosa.ticketautomaton.behaviour.service.event.TicketEventHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.osgi.service.event.Event;

import java.util.Dictionary;
import java.util.Hashtable;

import static org.junit.jupiter.api.Assertions.*;

class TicketAutomatonTest {

	private static TicketEventHandler ticketEventHandler;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		ticketEventHandler = new TicketEventHandler(null);
	}

	@Test
	void canReactToSpecifiedEvents() {
		Dictionary<String, Object> props = new Hashtable<>();

		// the assumtion is that if an event comes in that the ticketEventHandler should handle, it should crash
		// due to the EventAdmin service being null or some of the properties missing
		assertThrows(NullPointerException.class, () -> ticketEventHandler.handleEvent(
				new Event("de/leuphana/cosa/routing/RESPONSE_STARTPOINTS", props)));

		assertThrows(NullPointerException.class, () -> ticketEventHandler.handleEvent(
				new Event("de/leuphana/cosa/routing/RESPONSE_DESTINATIONS", props)));

		assertThrows(NullPointerException.class, () -> ticketEventHandler.handleEvent(
				new Event("de/leuphana/cosa/pricing/RESPONSE_PRICES", props)));

		// however, if an event comes in that is not relevant for TicketEventHandler, it should do nothing
		assertDoesNotThrow(() -> ticketEventHandler.handleEvent(
				new Event("de/leuphana/cosa/messaging/DELIVERY_REPORT_CREATED", props)));
	}

}
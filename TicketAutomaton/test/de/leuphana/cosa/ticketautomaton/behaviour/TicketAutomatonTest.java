package de.leuphana.cosa.ticketautomaton.behaviour;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TicketAutomatonTest {

	private static TicketAutomaton ticketAutomaton;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		ticketAutomaton = new TicketAutomaton();
	}

	@Test
	void canTicketBeSold() {
//		Assertions.assertTrue(ticketAutomaton.sellTicket("Lüneburg", "Hamburg"));
	}

}
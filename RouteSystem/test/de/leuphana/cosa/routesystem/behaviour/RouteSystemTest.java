package de.leuphana.cosa.routesystem.behaviour;

import de.leuphana.cosa.routesystem.behaviour.service.RouteCommandService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.ThrowingSupplier;
import org.osgi.service.event.Event;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Lennart_Admin
 */
public class RouteSystemTest {
	private static RouteCommandService service;

	@BeforeAll
	static void setUpBeforeClass() {
		service = new RouteSystemImpl();
	}

	@Test
	void doStartpointsExist() {
		Set<String> startpoints = service.getStartpoints();
		assertNotNull(startpoints);
		assertNotEquals(startpoints.size(), 0);
	}

	@Test
	void doDestinationsExistForEveryStartpoint() {
		Set<String> startpoints = service.getStartpoints();
		startpoints.forEach(startpoint -> {
			Set<String> destinations = service.getDestinations(startpoint);
			assertNotNull(destinations);
			assertNotEquals(destinations.size(), 0);
		});
	}

	@Test
	void doAllRoutesExist() {
		Set<String> startpoints = service.getStartpoints();
		startpoints.forEach(startpoint -> {
			Set<String> destinations = service.getDestinations(startpoint);
			destinations.forEach(destination ->
					assertDoesNotThrow(
							(ThrowingSupplier<Object>) () -> service.getRouteLength(startpoint, destination)
					)
			);
		});
	}
}

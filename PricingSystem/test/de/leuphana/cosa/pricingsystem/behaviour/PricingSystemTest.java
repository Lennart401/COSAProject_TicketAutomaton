package de.leuphana.cosa.pricingsystem.behaviour;

import de.leuphana.cosa.pricingsystem.behaviour.service.PricingCommandService;
import de.leuphana.cosa.pricingsystem.structure.PriceGroup;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Lennart_Admin
 */
public class PricingSystemTest {

	private static PricingCommandService pricingSystem;

	@BeforeAll
	static void setUpBeforeClass() {
		pricingSystem = new PricingSystemImpl();
	}

	@Test
	void canPriceGroupsBeGenerated() {
		Double routeLength = 300.0;
		Map<PriceGroup, Double> prices = pricingSystem.getPrices(routeLength);

		assertEquals(prices.get(PriceGroup.NORMAL_TARIF), 9.0, 0.001);
		assertEquals(prices.get(PriceGroup.GUENSTIGERREISEN_TARIF), 6.75, 0.001);
		assertEquals(prices.get(PriceGroup.SCHNAEPPCHEN_TARIF), 4.5, 0.001);
	}

}

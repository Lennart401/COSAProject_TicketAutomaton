package de.leuphana.cosa.pricingsystem.behaviour.service;

import de.leuphana.cosa.pricingsystem.structure.PriceGroup;

import java.util.Map;

public interface PricingCommandService {
	Map<PriceGroup, Double> getPrices(Double routeLength);
}

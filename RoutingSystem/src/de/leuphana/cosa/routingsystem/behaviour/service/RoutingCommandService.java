package de.leuphana.cosa.routingsystem.behaviour.service;

import de.leuphana.cosa.routingsystem.behaviour.service.exceptions.RouteDoesNotExistException;

import java.util.Set;

/**
 * @author Lennart_Admin
 */
public interface RoutingCommandService {
	Set<String> getStartpoints();
	Set<String> getDestinations(String startpoint);
	int getRouteLength(String start, String destination) throws RouteDoesNotExistException;
}

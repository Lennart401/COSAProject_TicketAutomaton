package de.leuphana.cosa.routesystem.behaviour.service;

import de.leuphana.cosa.routesystem.behaviour.service.exceptions.RouteDoesNotExistException;

import java.util.Set;

/**
 * @author Lennart_Admin
 */
public interface RouteCommandService {
	Set<String> getStartpoints();
	Set<String> getDestinations(String startpoint);
	Double getRouteLength(String start, String destination) throws RouteDoesNotExistException;
}

package de.leuphana.cosa.routesystem.behaviour.service.exceptions;

/**
 * @author Lennart_Admin
 */
public class RouteDoesNotExistException extends Exception {
	public RouteDoesNotExistException(String start, String destination) {
		super("Route from " + start + " to " + destination + " does not exist!");
	}
}

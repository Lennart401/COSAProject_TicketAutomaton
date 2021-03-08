package de.leuphana.cosa.routesystem.structure;

/**
 * @author Lennart_Admin
 */
public class Route {
	private String startpoint;
	private String destination;
	private int distance;

	public Route(String startpoint, String destination, int distance) {
		this.startpoint = startpoint;
		this.destination = destination;
		this.distance = distance;
	}

	public String getStartpoint() {
		return startpoint;
	}

	public String getDestination() {
		return destination;
	}

	public int getDistance() {
		return distance;
	}

	@Override
	public String toString() {
		return "Route{" +
				"startpoint='" + startpoint + '\'' +
				", destination='" + destination + '\'' +
				", distance=" + distance +
				'}';
	}
}

package de.leuphana.cosa.routingsystem.behaviour;

import de.leuphana.cosa.routingsystem.behaviour.service.RoutingCommandService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import java.util.Hashtable;

/**
 * @author Lennart_Admin
 */
public class RoutingSystemImpl implements BundleActivator {

	@Override
	public void start(BundleContext context) throws Exception {
		System.out.println("Starting RoutingSystemImpl");
		Hashtable<String, String> props = new Hashtable<>();
		props.put("Something", "Foo");
		context.registerService(RoutingCommandService.class.getName(), (RoutingCommandService) (start, destination) -> start + " " + destination, props);
	}

	@Override
	public void stop(BundleContext context) throws Exception {

	}

}

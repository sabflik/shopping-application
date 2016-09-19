package nz.ac.auckland.shop.services;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/services")
public class ShopApplication extends Application {

	private Set<Object> singletons = new HashSet<Object>();
	private Set<Class<?>> classes = new HashSet<Class<?>>();

	public ShopApplication() {
		ShopResource shopResource = new ShopResource();
		singletons.add(shopResource);
		
		ChatResource chatResource = new ChatResource();
		singletons.add(chatResource);

		classes.add(CustomerResolver.class);
		classes.add(PurchaseResolver.class);
	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}

	@Override
	public Set<Class<?>> getClasses() {
		return classes;
	}

}

package nz.ac.auckland.shop.services;

import javax.ws.rs.ext.ContextResolver;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import nz.ac.auckland.shop.domain.Address;
import nz.ac.auckland.shop.domain.Customer;
import nz.ac.auckland.shop.domain.Item;

public class CustomerResolver implements ContextResolver<JAXBContext> {
	private JAXBContext _context;

	public CustomerResolver() {
		try {
			_context = JAXBContext.newInstance(Customer.class, Item.class, Address.class);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	@Override
	public JAXBContext getContext(Class<?> type) {
		if (type.equals(Customer.class) || type.equals(Item.class) || type.equals(Address.class)) {
			return _context;
		} else {
			return null;
		}
	}

}

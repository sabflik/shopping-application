package nz.ac.auckland.shop.services;

import javax.ws.rs.ext.ContextResolver;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import nz.ac.auckland.shop.domain.Item;
import nz.ac.auckland.shop.domain.Purchase;

public class PurchaseResolver implements ContextResolver<JAXBContext>{
	private JAXBContext _context;

	public PurchaseResolver() {
		try {
			_context = JAXBContext.newInstance(Purchase.class, Item.class);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	@Override
	public JAXBContext getContext(Class<?> type) {
		if (type.equals(Purchase.class) || type.equals(Item.class)) {
			return _context;
		} else {
			return null;
		}
	}
}

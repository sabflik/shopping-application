package nz.ac.auckland.shop.domain;

import java.util.Date;

public class Purchase {
	
	private Customer _customer;
	private Item _item;
	private Date _dateOfPurchase;
	
	public Purchase(Customer customer, Item item, Date dateOfPurchase) {
		_customer = customer;
		_item = item;
		_dateOfPurchase = dateOfPurchase;
	}
	
	public Customer getCustomer() {
		return _customer;
	}
	
	public void setCustomer(Customer customer) {
		_customer = customer;
	}
	
	public Item getItem() {
		return _item;
	}
	
	public void setItem(Item item) {
		_item = item;
	}
	
	public Date getDateOfPurchase() {
		return _dateOfPurchase;
	}
	
	public void setDateOfPurchase(Date dateOfPurchase) {
		_dateOfPurchase = dateOfPurchase;
	}

}

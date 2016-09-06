package nz.ac.auckland.shop.domain;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlAccessType;

import java.util.Date;

@XmlAccessorType(XmlAccessType.FIELD)
public class Purchase {
	
	@XmlElement(name="customer")
	private Customer _customer;
	
	@XmlElement(name="item")
	private Item _item;
	
	@XmlElement(name="date-of-purchase")
	private Date _dateOfPurchase;
	
	protected Purchase() {
		
	}
	
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

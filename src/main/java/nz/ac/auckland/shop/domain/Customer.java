package nz.ac.auckland.shop.domain;

import java.util.List;
import java.util.Set;

public class Customer {
	
	private long _id;
	private String _name;
	private Address _address;
	private Set<CreditCard> _creditCards;
	private List<Purchase> _purchases;
	
	public Customer(long id, String name, Address address, Set<CreditCard> creditCards, List<Purchase> purchases) {
		_id  = id;
		_name = name;
		_address = address;
		_creditCards = creditCards;
		_purchases = purchases;
	}
	
	public long getId() {
		return _id;
	}
	
	public void setId(long id) {
		_id = id;
	}
	
	public String getName() {
		return _name;
	}
	
	public void setName(String name) {
		_name = name;
	}
	
	public Address getAddress() {
		return _address;
	}
	
	public void setAddress(Address address) {
		_address = address;
	}
	
	public Set<CreditCard> getCreaditCards() {
		return _creditCards;
	}
	
	public void setCreaditCards(Set<CreditCard> creditCards) {
		_creditCards = creditCards;
	}
	
	public List<Purchase> getPurchases() {
		return _purchases;
	}
	
	public void setPurchases(List<Purchase> purchases) {
		_purchases = purchases;
	}

}

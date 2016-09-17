package nz.ac.auckland.shop.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;

import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity
public class Customer {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long _id;
	
	@Column
	private String _name;
	
	@Column
	private Address _address;
	
	@OneToMany(fetch = FetchType.EAGER,
			cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	@JoinColumn(name = "CC_ID")
	private Set<CreditCard> _creditCards;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "PURCHASE", 
		joinColumns = @JoinColumn(name = "CUSTOMER_ID"))
	@OrderColumn
	private List<Purchase> _purchases;
	
	public Customer() {
		
	}
	
	public Customer(String name, Address address) {
		_name = name;
		_address = address;
		_creditCards = new HashSet<CreditCard>();
		_purchases = new ArrayList<Purchase>();
	}
	
	public Customer(long id, String name, Address address) {
		_id  = id;
		_name = name;
		_address = address;
		_creditCards = new HashSet<CreditCard>();
		_purchases = new ArrayList<Purchase>();
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
	
	public void addPurchase(Purchase purchase) {
		_purchases.add(purchase);
		
		Collections.sort(_purchases, Collections.reverseOrder());
	}
	
	public List<Purchase> getPurchases() {
		return Collections.unmodifiableList(_purchases);
	}
	
	public Purchase getLastPurchase() {
		Purchase purchase = null;
		
		if(!_purchases.isEmpty()) {
			purchase = _purchases.get(0);
		}
		return purchase;
	}
	
	public void addCreditCard(CreditCard creditCard) {
		_creditCards.add(creditCard);
	}
	
	public void removeCreditCard(CreditCard creditCard) {
		_creditCards.remove(creditCard);
	}
	
	public Set<CreditCard> getCreditCards() {
		return _creditCards;
	}
	
	public void updateCreditCards(Set<CreditCard> creditCards) {
		_creditCards = creditCards;
	}
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		
//		DateTimeFormatter dOfBFormatter = DateTimeFormat.forPattern("dd/MM/yyyy");
//		DateTimeFormatter timeFormatter = DateTimeFormat.forPattern("HH:mm");
//		
		buffer.append("Customer: { [");
		buffer.append(_id);
		buffer.append("]; ");
		if(_name != null) {
			buffer.append(_name);
			buffer.append(", ");
		}
		if(_address != null) {
			buffer.append(_address);
		}
		
		buffer.append("\n");
		buffer.append("  Credit cards: ");
		if(_creditCards.isEmpty()) {
			buffer.append("none");
		} else {
			for(CreditCard creditCard : _creditCards) {
				buffer.append("[");
				buffer.append(creditCard.getType().toString());
				buffer.append("]");
				buffer.append(" ");
				if(creditCard.getCardNumber() != null) {
					buffer.append(creditCard.getCardNumber());
					buffer.append(", ");
				}
				if(creditCard.getExpiryDate() != null) {
					buffer.append(creditCard.getExpiryDate().toString());
				}
				buffer.append(";");
			}
			buffer.deleteCharAt(buffer.length()-1);
		}
		
		if(!_purchases.isEmpty()) {
			buffer.append("\n  Last known purchase: ");
			Purchase lastPurchase = _purchases.get(0);
			buffer.append(lastPurchase);
		}
		
		buffer.append(" }");
		
		return buffer.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Customer))
            return false;
        if (obj == this)
            return true;

        Customer other = (Customer)obj;
        return _id == other._id;
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 31). 
	            append(_id).
	            toHashCode();
	}

}

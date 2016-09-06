package nz.ac.auckland.shop.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Customer {
	
	private long _id;
	private String _name;
	private Address _address;
	private Set<CreditCard> _creditCards;
	private List<Purchase> _purchases;
	
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
//		buffer.append("Parolee: { [");
//		buffer.append(_id);
//		buffer.append("]; ");
//		if(_lastname != null) {
//			buffer.append(_lastname);
//			buffer.append(", ");
//		}
//		if(_firstname != null) {
//			buffer.append(_firstname);
//		}
//		buffer.append("; ");
//		if(_gender != null) {
//			buffer.append(_gender);
//		}
//		buffer.append("; ");
//		
//		if(_dateOfBirth != null) {
//			buffer.append(dOfBFormatter.print(_dateOfBirth));
//		}
//		buffer.append("\n  ");
//		if(_homeAddress != null) {
//			buffer.append(_homeAddress);
//		}
//		
//		if(_curfew != null) {
//			buffer.append("\n  Curfew from ");
//			buffer.append(timeFormatter.print(_curfew.getStartTime()));
//			buffer.append(" to ");
//			buffer.append(timeFormatter.print(_curfew.getEndTime()));
//			buffer.append(" @ ");
//			
//			if(_homeAddress != null && _homeAddress.equals(_curfew.getConfinementAddress())) {
//				buffer.append("home");
//			} else {
//				buffer.append(_curfew.getConfinementAddress());
//			}
//		} else {
//			buffer.append("No curfew conditions");
//		}
//		
//		buffer.append("\n  ");
//		if(_criminalProfile != null) {
//			buffer.append(_criminalProfile);
//		} else {
//			buffer.append("No criminal profile");
//		}
//		
//		buffer.append("\n");
//		buffer.append("  Dissassociates: ");
//		if(_dissassociates.isEmpty()) {
//			buffer.append("none");
//		} else {
//			for(Parolee dissassociate : _dissassociates) {
//				buffer.append("[");
//				buffer.append(dissassociate._id);
//				buffer.append("]");
//				buffer.append(" ");
//				if(dissassociate._lastname != null) {
//					buffer.append(dissassociate._lastname);
//					buffer.append(", ");
//				}
//				if(dissassociate._firstname != null) {
//					buffer.append(dissassociate._firstname);
//				}
//				buffer.append(";");
//			}
//			buffer.deleteCharAt(buffer.length()-1);
//		}
//		
//		if(!_movements.isEmpty()) {
//			buffer.append("\n  Last known location: ");
//			Movement lastMovement = _movements.get(0);
//			buffer.append(lastMovement);
//		}
//		
//		buffer.append(" }");
		
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

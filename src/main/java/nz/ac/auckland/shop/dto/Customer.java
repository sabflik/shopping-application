package nz.ac.auckland.shop.dto;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import nz.ac.auckland.shop.domain.Address;
import nz.ac.auckland.shop.domain.Purchase;

@XmlRootElement(name="customer")
@XmlAccessorType(XmlAccessType.FIELD)
public class Customer {
	
	@XmlAttribute(name="id")
	private long _id;
	
	@XmlElement(name="name")
	private String _name;
	
	@XmlElement(name="address")
	private Address _address;
	
	@XmlElement(name="last-purchase")
	private Purchase _lastPurchase;
	
	protected Customer() {
		
	}
	
	public Customer(String name, Address address) throws IllegalArgumentException {
		this(0, name, address, null);
	}
	
	public Customer(long id,
			String name,
			Address address,
			Purchase lastPurchase) {
		_id = id;
		_name = name;
		_address = address;
		_lastPurchase = lastPurchase;
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
	
	public Purchase getLastPurchase() {
		return _lastPurchase;
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
//		buffer.append("\n  ");
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
//		buffer.append(" }");
		
		return buffer.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Customer))
            return false;
        if (obj == this)
            return true;

        Customer rhs = (Customer) obj;
        return new EqualsBuilder().
            append(_id, rhs._id).
            append(_name, rhs._name).
            append(_address, rhs._address).
            isEquals();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 31). 
	            append(_id).
	            append(_name).
	            append(_address).
				toHashCode();
	}

}
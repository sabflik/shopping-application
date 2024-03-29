package nz.ac.auckland.shop.dto;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import nz.ac.auckland.shop.domain.Address;

@XmlRootElement(name="customer")
@XmlAccessorType(XmlAccessType.FIELD)
public class Customer {
	
	@XmlAttribute(name="id")
	private long _id;
	
	@XmlElement(name="name")
	private String _name;
	
	@XmlElement(name="address")
	private Address _address;
	
	protected Customer() {
		
	}
	
	public Customer(String name, Address address) throws IllegalArgumentException {
		_name = name;
		_address = address;
	}

	public Customer(long id,
			String name,
			Address address) {
		_id = id;
		_name = name;
		_address = address;
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
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		
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
		
		buffer.append(" }");
		
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

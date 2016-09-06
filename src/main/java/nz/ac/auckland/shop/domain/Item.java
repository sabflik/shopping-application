package nz.ac.auckland.shop.domain;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Item {
	
	@XmlAttribute(name="id")
	private long _id;
	
	@XmlElement(name="name")
	private String _name;
	
	@XmlElement(name="price")
	private double _price;
	
	@XmlElement(name="description")
	private String _description;
	
	protected Item() {
		
	}

	public Item(long id, String name, double price, String description) {
		_id = id;
		_name = name;
		_price = price;
		_description = description;
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
	
	public double getPrice() {
		return _price;
	}
	
	public void setPrice(double price) {
		_price = price;
	}
	
	public String getDescrpition() {
		return _description;
	}
	
	public void setDescription(String description) {
		_description = description;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Item))
            return false;
        if (obj == this)
            return true;

        Item rhs = (Item) obj;
        return new EqualsBuilder().
            append(_id, rhs._id).
            append(_name, rhs._name).
            append(_price, rhs._price).
            append(_description, rhs._description).
            isEquals();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 31). 
	            append(_id).
	            append(_name).
	            append(_price).
	            append(_description).
	            toHashCode();
	}
	
}

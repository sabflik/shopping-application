package nz.ac.auckland.shop.domain;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.xml.bind.annotation.XmlAccessType;

import java.util.Date;

@XmlAccessorType(XmlAccessType.FIELD)
public class Purchase implements Comparable<Purchase> {
	
	@XmlElement(name="item")
	private Item _item;
	
	@XmlElement(name="date-of-purchase")
	private Date _dateOfPurchase;
	
	protected Purchase() {
		
	}
	
	public Purchase(Item item, Date dateOfPurchase) {
		_item = item;
		_dateOfPurchase = dateOfPurchase;
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
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Purchase))
            return false;
        if (obj == this)
            return true;

        Purchase rhs = (Purchase) obj;
        return new EqualsBuilder().
            append(_item, rhs._item).
            append(_dateOfPurchase, rhs._dateOfPurchase).
            isEquals();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 31). 
	            append(_item).
	            append(_dateOfPurchase).
	            toHashCode();
	}
	
	@Override
	public int compareTo(Purchase purchase) {
		return _dateOfPurchase.compareTo(purchase._dateOfPurchase);
	}
	
	@Override
	public String toString() {
		
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(_item.getId());
		buffer.append(" , ");
		buffer.append(" @ ");
		buffer.append(_dateOfPurchase.toString());
		
		return buffer.toString();
	}

}

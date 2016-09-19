package nz.ac.auckland.shop.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;

import java.util.Date;

@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CreditCard {
	
	@Enumerated
	@XmlElement(name="type")
	private CardType _type;
	
	@Id
	@Column
	@XmlElement(name="card-number")
	private String _cardNumber;
	
	@Temporal( TemporalType.DATE )
	@Column
	@XmlElement(name="expiry-date")
	private Date _expiryDate;
	
	protected CreditCard() {
		
	}
	
	public CreditCard(CardType type, String cardNumber, Date expiryDate) {
		_type = type;
		_cardNumber = cardNumber;
		_expiryDate = expiryDate;
	}
	
	public CardType getType() {
		return _type;
	}
	
	public void setType(CardType type) {
		_type = type;
	}
	
	public String getCardNumber() {
		return _cardNumber;
	}
	
	public void setCardNumber(String cardNumber) {
		_cardNumber = cardNumber;
	}
	
	public Date getExpiryDate() {
		return _expiryDate;
	}
	
	public void setExpiryDate(Date expiryDate) {
		_expiryDate = expiryDate;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof CreditCard))
            return false;
        if (obj == this)
            return true;

        CreditCard rhs = (CreditCard) obj;
        return new EqualsBuilder().
            append(_type, rhs._type).
            append(_cardNumber, rhs._cardNumber).
//            append(_expiryDate, rhs._expiryDate).
            isEquals();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 31). 
	            append(_type).
	            append(_cardNumber).
//	            append(_expiryDate).
	            toHashCode();
	}

}

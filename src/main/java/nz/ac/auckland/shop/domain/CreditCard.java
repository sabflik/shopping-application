package nz.ac.auckland.shop.domain;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlAccessType;

import java.util.Date;

@XmlAccessorType(XmlAccessType.FIELD)
public class CreditCard {
	
	@XmlElement(name="type")
	private CardType _type;
	
	@XmlElement(name="card-number")
	private String _cardNumber;
	
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

}

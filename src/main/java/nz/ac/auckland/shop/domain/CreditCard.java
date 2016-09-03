package nz.ac.auckland.shop.domain;

import java.util.Date;

public class CreditCard {
	
	private CardType _type;
	private String _cardNumber;
	private Date _expiryDate;
	
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

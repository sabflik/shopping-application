package nz.ac.auckland.shop.domain;

public enum CardType {
	CREDIT, DEBIT;
	
	public static CardType fromString(String text) {
	    if (text != null) {
	      for (CardType ct : CardType.values()) {
	        if (text.equalsIgnoreCase(ct.toString())) {
	          return ct;
	        }
	      }
	    }
	    return null;
	  }
}

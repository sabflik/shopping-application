package nz.ac.auckland.shop.domain;

public class Address {
	
	private String _streetNumber;
	private String _streetName;
	private String _suburb;
	private String _city;
	private String _zipCode;
	
	public Address(String streetNumber, String streetName, String suburb, String city, String zipCode) {
		_streetNumber = streetNumber;
		_streetName = streetName;
		_suburb = suburb;
		_city = city;
		_zipCode = zipCode;
	}
	
	public String getStreetNumber() {
		return _streetNumber;
	}
	
	public void setStreetNumber(String streetNumber) {
		_streetNumber = streetNumber;
	}
	
	public String getStreetName() {
		return _streetName;
	}
	
	public void setStreetName(String streetName) {
		_streetName = streetName;
	}
	
	public String getSuburb() {
		return _suburb;
	}
	
	public void setSuburb(String suburb) {
		_suburb = suburb;
	}
	
	public String getCity() {
		return _city;
	}
	
	public void setCity(String city) {
		_city = city;
	}
	
	public String getZipcode() {
		return _zipCode;
	}
	
	public void setZipcode(String zipcode) {
		_zipCode = zipcode;
	}

}

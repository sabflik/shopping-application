package nz.ac.auckland.shop.domain;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlAccessType;

@XmlAccessorType(XmlAccessType.FIELD)
public class Address {
	
	@XmlElement(name="street-number")
	private String _streetNumber;
	
	@XmlElement(name="street-name")
	private String _streetName;
	
	@XmlElement(name="suburb")
	private String _suburb;
	
	@XmlElement(name="city")
	private String _city;
	
	@XmlElement(name="zipcode")
	private String _zipCode;
	
	protected Address() {
		
	}
	
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

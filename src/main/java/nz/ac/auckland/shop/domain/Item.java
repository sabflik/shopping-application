package nz.ac.auckland.shop.domain;

public class Item {
	
	private long _id;
	private String _name;
	private double _price;
	private String _description;

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
	
}

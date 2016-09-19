package nz.ac.auckland.shop.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="purchase")
@XmlAccessorType(XmlAccessType.FIELD)
public class Purchase {

	@XmlElement(name="date-of-purchase")
	private Date _dateOfPurchase;

	@XmlElement(name="item_id")
	private long _item_id;

	@XmlElement(name="item_name")
	private String _item_name;
	
	@XmlElement(name="item_price")
	private double _item_price;

	@XmlElement(name="item_description")
	private String _item_description;

	protected Purchase() {

	}

	public Purchase(Date dateOfPurchase, long item_id, String item_name, double item_price, String item_description) {
		this._dateOfPurchase = dateOfPurchase;
		this._item_id = item_id;
		this._item_name = item_name;
		this._item_price = item_price;
		this._item_description = item_description;
	}

	public Date get_dateOfPurchase() {
		return _dateOfPurchase;
	}

	public void set_dateOfPurchase(Date _dateOfPurchase) {
		this._dateOfPurchase = _dateOfPurchase;
	}

	public long get_item_id() {
		return _item_id;
	}

	public void set_item_id(long _item_id) {
		this._item_id = _item_id;
	}

	public String get_item_name() {
		return _item_name;
	}

	public void set_item_name(String _item_name) {
		this._item_name = _item_name;
	}

	public double get_item_price() {
		return _item_price;
	}

	public void set_item_price(double _item_price) {
		this._item_price = _item_price;
	}

	public String get_item_description() {
		return _item_description;
	}

	public void set_item_description(String _item_description) {
		this._item_description = _item_description;
	}

	@Override
	public String toString() {
		return "Purchase [_dateOfPurchase=" + _dateOfPurchase + ", _item_id=" + _item_id + ", _item_name=" + _item_name
				+ ", _item_price=" + _item_price + ", _item_description=" + _item_description + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_dateOfPurchase == null) ? 0 : _dateOfPurchase.hashCode());
		result = prime * result + ((_item_description == null) ? 0 : _item_description.hashCode());
		result = prime * result + (int) (_item_id ^ (_item_id >>> 32));
		result = prime * result + ((_item_name == null) ? 0 : _item_name.hashCode());
		long temp;
		temp = Double.doubleToLongBits(_item_price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Purchase other = (Purchase) obj;
		if (_dateOfPurchase == null) {
			if (other._dateOfPurchase != null)
				return false;
		} else if (!_dateOfPurchase.equals(other._dateOfPurchase))
			return false;
		if (_item_description == null) {
			if (other._item_description != null)
				return false;
		} else if (!_item_description.equals(other._item_description))
			return false;
		if (_item_id != other._item_id)
			return false;
		if (_item_name == null) {
			if (other._item_name != null)
				return false;
		} else if (!_item_name.equals(other._item_name))
			return false;
		if (Double.doubleToLongBits(_item_price) != Double.doubleToLongBits(other._item_price))
			return false;
		return true;
	}
}

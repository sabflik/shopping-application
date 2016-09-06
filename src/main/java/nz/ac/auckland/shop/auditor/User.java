package nz.ac.auckland.shop.auditor;

import javax.persistence.*;

@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long _id;
	@Column(unique=true)
	private String _username;
	@Column
	private String _lastname;
	@Column
	private String _firstname;
	
	public User(String username, String lastname, String firstname) {
		_username = username;
		_lastname = lastname;
		_firstname = firstname;
	}
	
	public User(String username) {
		this(username, null, null);
	}
	
	protected User() {}
	
	public Long getId() {
		return _id;
	}
	
	public String getUsername() {
		return _username;
	}
	
	public String getLastname() {
		return _lastname;
	}
	
	public String getFirstname() {
		return _firstname;
	}
}

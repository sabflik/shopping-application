package nz.ac.auckland.shop.auditor;

import java.util.Date;

import javax.persistence.*;

@Entity
public class AuditEntry {
	
	public enum CrudOperation {Create, Retrieve, Update, Delete}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long _id;
	@Enumerated
	private CrudOperation _crudOperator;
	@Column
	private String _uri;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.PERSIST)
	@JoinColumn(name="USER_ID", nullable=false)
	private User _user;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date _timestamp;
	
	public AuditEntry(CrudOperation crudOp, String uri, User user) {
		_crudOperator = crudOp;
		_uri = uri;
		_user = user;
		_timestamp = new Date();
	}
	
	protected AuditEntry() {}
	
	public Long getId() {
		return _id;
	}
	
	public CrudOperation getCrudOperation() {
		return _crudOperator;
	}
	
	public String getUri() {
		return _uri;
	}
	
	public User getUser() {
		return _user;
	}
	
	public Date getTimestamp() {
		return _timestamp;
	}
}

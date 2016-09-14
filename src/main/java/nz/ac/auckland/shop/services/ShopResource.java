package nz.ac.auckland.shop.services;

import java.net.URI;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nz.ac.auckland.shop.auditor.PersistenceManager;
import nz.ac.auckland.shop.auditor.User;
import nz.ac.auckland.shop.domain.Address;
import nz.ac.auckland.shop.domain.Customer;
import nz.ac.auckland.shop.domain.Item;
import nz.ac.auckland.shop.domain.Purchase;

@Path("/shop")
public class ShopResource {

	private static final Logger _logger = LoggerFactory.getLogger(ShopResource.class);

	private Map<Long, Customer> _customerDB;
	private Map<Long, Item> _itemDB;
	private AtomicLong _customerIdCounter;
	private AtomicLong _itemIdCounter;

	public ShopResource() {
//		reloadDatabase();
	}

	@PUT
	public void reloadData() {
//		reloadDatabase();
	}
	
	@POST
	@Path("customers")
	@Consumes("application/xml")
	public Response createCustomer(
			nz.ac.auckland.shop.dto.Customer dtoCustomer) {
		_logger.debug("Read customer: " + dtoCustomer);
		Customer customer = CustomerMapper.toDomainModel(dtoCustomer);
		customer.setId(_customerIdCounter.incrementAndGet());
		_customerDB.put(customer.getId(), customer);

		_logger.debug("Created customer: " + customer);
		
		return Response.created(URI.create("/shop/customers/" + customer.getId()))
				.build();
	}
	
//	@POST
//	@Path("customers")
//	@Consumes("application/xml")
//	public Response createCustomer(
//			nz.ac.auckland.shop.dto.Customer dtoCustomer) {
//		_logger.debug("Read customer: " + dtoCustomer);
//		Customer customer = CustomerMapper.toDomainModel(dtoCustomer);
//		customer.setId(_customerIdCounter.incrementAndGet());
//		_customerDB.put(customer.getId(), customer);
//
//		_logger.debug("Created customer: " + customer);
//		
//		return Response.created(URI.create("/shop/customers/" + customer.getId()))
//				.build();
//	}
	
//	@POST
//	@Path("customers/{id}/purchases")
//	@Consumes("application/xml")
//	public void createPurchaseForCustomer(@PathParam("id") long id,
//			Purchase purchase) {
//		Customer customer = findCustomer(id);
//		customer.addPurchase(purchase);
//	}
	
//	@PUT
//	@Path("customers/{id}")
//	@Consumes("application/xml")
//	public void updateCustomer(
//			nz.ac.auckland.shop.dto.Customer dtoCustomer) {
//		// Get the Customer object from the database.
//		Customer customer = findCustomer(dtoCustomer.getId());
//
//		customer.setName(dtoCustomer.getName());
//		customer.setAddress(dtoCustomer.getAddress());
//	}
	
	
	
//	/**
//	 * Updates the set of a dissassociate Parolees for a given Parolee.
//	 * 
//	 * @param id the Parolee whose dissassociates should be updated.
//	 * 
//	 * @param dissassociates the new set of dissassociates.
//	 */
//	@PUT
//	@Path("{id}/dissassociates")
//	@Consumes("application/xml")
//	public void updateDissassociates(@PathParam("id") long id, Set<nz.ac.auckland.parolee.dto.Parolee> dissassociates) {
//		// Get the Parolee object from the database.
//		Parolee parolee = findParolee(id);
//		
//		// Lookup the dissassociate Parolee instances in the database.
//		Set<Parolee> dissassociatesInDatabase = new HashSet<Parolee>();
//		for(nz.ac.auckland.parolee.dto.Parolee dtoParolee : dissassociates) {
//			Parolee dissassociate = findParolee(dtoParolee.getId());
//			dissassociatesInDatabase.add(dissassociate);
//		}
//		
//		// Update the Parolee by setting its dissassociates.
//		parolee.updateDissassociates(dissassociatesInDatabase);
//		
//		// JAX-RS will add the default response code (204 No Content) to the
//		// HTTP response message.
//	}
//	
//	/**
//	 * Updates a Parolee's CriminalProfile.
//	 * 
//	 * @param id the unique identifier of the Parolee.
//	 * 
//	 * @param profile the Parolee's updated criminal profile.
//	 */
//	@PUT
//	@Path("{id}/criminal-profile")
//	@Consumes("application/xml")
//	public void updateCriminalProfile(@PathParam("id") long id, CriminalProfile profile) {
//		// Get the Parolee object from the database.
//		Parolee parolee = findParolee(id);
//		
//		// Update the Parolee's criminal profile.
//		parolee.setCriminalProfile(profile);
//		
//		// JAX-RS will add the default response code (204 No Content) to the
//		// HTTP response message.
//	}
//

	
//	@GET
//	@Path("customers/{id}")
//	@Produces("application/xml")
//	public nz.ac.auckland.shop.dto.Customer getCustomer(
//			@PathParam("id")long id) {
//		// Get the Customer object from the database.
//		Customer customer = findCustomer(id);
//
//		// Convert the Customer to a Customer DTO.
//		nz.ac.auckland.shop.dto.Customer dtoCustomer = CustomerMapper.toDto(customer);
//		
//		// JAX-RS will processed the returned value, marshalling it and storing
//		// it in the HTTP response message body. It will use the default status 
//		// code of 200 Ok.
//		return dtoCustomer;
//	}
	
	
	@GET
	@Path("customers/{id}")
	@Produces("application/xml")
	public nz.ac.auckland.shop.dto.Customer getCustomer(@PathParam("id")long id) {
		nz.ac.auckland.shop.dto.Customer customer = null;
	
		EntityManager em = PersistenceManager.instance().createEntityManager();
		
		try {
			em.getTransaction().begin();
			
			customer = em.find(nz.ac.auckland.shop.dto.Customer.class, id);
			
			em.getTransaction().commit();
		} catch (Exception e) {
			//Handle exceptions
		} finally {
			if (em != null && em.isOpen()) {
				em.close();
			}
		}
		return customer;
	}

//	/**
//	 * Returns a view of the Parolee database, represented as a List of
//	 * nz.ac.auckland.parolee.dto.Parolee objects.
//	 * 
//	 */
//	@GET
//	@Produces("application/xml")
//	public Response getParolees(@DefaultValue("1") @QueryParam("start") int start, 
//			@DefaultValue("1") @QueryParam("size")int size,
//			@Context UriInfo uriInfo) {
//		URI uri = uriInfo.getAbsolutePath();
//		
//		Link previous = null;
//		Link next = null;
//		
//		if(start > 1) {
//			// There are previous Parolees - create a previous link.
//			previous = Link.fromUri(uri + "?start={start}&size={size}")
//					.rel("prev")
//					.build(start - 1, size);
//		}
//		if(start + size <= _paroleeDB.size()) {
//			// There are successive parolees - create a next link.
//			_logger.info("Making NEXT link");
//			next = Link.fromUri(uri + "?start={start}&size={size}")
//					.rel("next")
//					.build(start + 1, size);
//		}
//
//		// Create list of Parolees to return.
//		List<nz.ac.auckland.parolee.dto.Parolee> parolees = 
//				new ArrayList<nz.ac.auckland.parolee.dto.Parolee>();
//		long paroleeId = start;
//		for(int i = 0; i < size; i++) {
//			Parolee parolee = _paroleeDB.get(paroleeId);
//			parolees.add(ParoleeMapper.toDto(parolee));
//		}
//		
//		// Create a GenericEntity to wrap the list of Parolees to return. This
//		// is necessary to preserve generic type data when using any
//		// MessageBodyWriter to handle translation to a particular data format.
//		GenericEntity<List<nz.ac.auckland.parolee.dto.Parolee>> entity = 
//				new GenericEntity<List<nz.ac.auckland.parolee.dto.Parolee>>(parolees) {};
//		
//		// Build a Response that contains the list of Parolees plus the link 
//		// headers.
// 		ResponseBuilder builder = Response.ok(entity);
// 		if(previous != null) {
// 			builder.links(previous);
// 		}
// 		if(next != null) {
// 			builder.links(next);
// 		}
// 		Response response = builder.build();
// 		
// 		// Return the custom Response. The JAX-RS run-time will process this,
// 		// extracting the List of Parolee objects and marshalling them into the
// 		// HTTP response message body. In addition, since the Response object
// 		// contains headers (previous and/or next), these will be added to the 
// 		// HTTP response message. The Response object was created with the 200
// 		// Ok status code, and this too will be added for the status header.
// 		return response;
//	}
//
//	/**
//	 * Returns movement history for a particular Parolee.
//	 * 
//	 * @param id
//	 *            the unique identifier of the Parolee.
//	 * 
//	 */
//	@GET
//	@Path("{id}/movements")
//	@Produces("application/xml")
//	public List<Movement> getMovements(@PathParam("id") long id) {
//		// Get the Parolee object from the database.
//		Parolee parolee = findParolee(id);
//
//		// Return the Parolee's movements.
//		return parolee.getMovements();
//		
//		// JAX-RS will processed the returned value, marshalling it and storing
//		// it in the HTTP response message body. It will use the default status 
//		// code of 200 Ok.
//	}
//
//	/**
//	 * Returns the dissassociates associated directly with a particular Parolee.
//	 * Each dissassociate is represented as an instance of class
//	 * nz.ac.auckland.parolee.dto.Parolee.
//	 * 
//	 * @param id
//	 *            the unique identifier of the Parolee.
//	 */
//	@GET
//	@Path("{id}/dissassociates")
//	@Produces("application/xml")
//	public List<nz.ac.auckland.parolee.dto.Parolee> getParoleeDissassociates(
//			@PathParam("id") long id) {
//		// Get the Parolee object from the database.
//		Parolee parolee = findParolee(id);
//
//		List<nz.ac.auckland.parolee.dto.Parolee> dissassociates = new ArrayList<nz.ac.auckland.parolee.dto.Parolee>();
//
//		for (Parolee dissassociate : parolee.getDissassociates()) {
//			dissassociates.add(ParoleeMapper.toDto(dissassociate));
//		}
//		return dissassociates;
//		
//		// JAX-RS will processed the returned value, marshalling it and storing
//		// it in the HTTP response message body. It will use the default status 
//		// code of 200 Ok.
//	}
//
//	/**
//	 * Returns the CriminalProfile for a particular parolee.
//	 * 
//	 * @param id the unique identifier of the Parolee.
//	 */
//	@GET
//	@Path("{id}/criminal-profile")
//	@Produces("application/xml")
//	public CriminalProfile getCriminalProfile(@PathParam("id") long id) {
//		// Get the Parolee object from the database.
//		Parolee parolee = findParolee(id);
//		
//		return parolee.getCriminalProfile();
//		
//		// JAX-RS will processed the returned value, marshalling it and storing
//		// it in the HTTP response message body. It will use the default status 
//		// code of 200 Ok.
//	}

//	protected Customer findCustomer(long id) {
//		return _customerDB.get(id);
//	}
//	
//	protected Item findItem(long id) {
//		return _itemDB.get(id);
//	}
	
	protected void reloadDatabase() {
	_customerDB = new ConcurrentHashMap<Long, Customer>();
	_itemDB = new ConcurrentHashMap<Long, Item>();
	_customerIdCounter = new AtomicLong();
	_itemIdCounter = new AtomicLong();
	
	// === Initialise Item #1
	long iid = _itemIdCounter.incrementAndGet();
	Item item = new Item(iid, "American Sniper DVD", 15.00, null);
	_itemDB.put(iid, item);
	
	// === Initialise Item #2
	iid = _itemIdCounter.incrementAndGet();
	item = new Item(iid, "iPhone", 80.00, null);
	_itemDB.put(iid, item);

	// === Initialise Customer #1
	long cid = _customerIdCounter.incrementAndGet();
	Address address = new Address("15", "Bermuda road", "St Johns", "Auckland", "1071");
	Customer customer = new Customer(cid,
			"Oliver", 
			address);
	_customerDB.put(cid, customer);

	
	Calendar calendar = Calendar.getInstance();
    calendar.set(2016, Calendar.SEPTEMBER, 12, 1, 58, 0);
    Date date = calendar.getTime();
	
	customer.addPurchase(new Purchase(item, date));
	
	
	// === Initialise Customer #2
	cid = _customerIdCounter.incrementAndGet();
	address = new Address("22", "Tarawera Terrace", "St Heliers", "Auckland", "1071");
	customer = new Customer(cid,
			"Catherine", 
			address);
	_customerDB.put(cid, customer);
	
	// === Initialise Customer #3
	cid = _customerIdCounter.incrementAndGet();
	address = new Address("67", "Drayton Gardens", "Oraeki", "Auckland", "1071");
	customer = new Customer(cid,
			"Nasser", 
			address);
	_customerDB.put(cid, customer);
}
}

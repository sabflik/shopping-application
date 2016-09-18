package nz.ac.auckland.shop.services;

import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nz.ac.auckland.shop.auditor.PersistenceManager;
import nz.ac.auckland.shop.domain.Address;
import nz.ac.auckland.shop.domain.CreditCard;
import nz.ac.auckland.shop.domain.Customer;
import nz.ac.auckland.shop.domain.Item;
import nz.ac.auckland.shop.domain.Purchase;

@Path("/shop")
public class ShopResource {

	private static final Logger _logger = LoggerFactory.getLogger(ShopResource.class);

	public ShopResource() {
		reloadDatabase();
	}

	@POST
	@Path("customers")
	@Consumes("application/xml")
	public Response createCustomer(nz.ac.auckland.shop.dto.Customer dtoCustomer) {
		EntityManager em = PersistenceManager.instance().createEntityManager();

		_logger.debug("Read customer: " + dtoCustomer);
		Customer customer = CustomerMapper.toDomainModel(dtoCustomer);

		try {
			em.getTransaction().begin();

			em.persist(customer);

			em.getTransaction().commit();
		} catch (Exception e) {
			// Handle exceptions
		} finally {
			if (em != null && em.isOpen()) {
				em.close();
			}
		}
		_logger.debug("Created customer: " + customer);

		return Response.created(URI.create("/shop/customers/" + customer.getId())).build();
	}

	@GET
	@Path("customers/{id}")
	@Produces("application/xml")
	public nz.ac.auckland.shop.dto.Customer getCustomer(@PathParam("id") long id) {
		Customer customer = null;

		EntityManager em = PersistenceManager.instance().createEntityManager();

		try {
			em.getTransaction().begin();

			customer = em.find(Customer.class, id);
			_logger.debug("Found cutomer: " + customer);
			
			em.getTransaction().commit();
		} catch (Exception e) {
			// Handle exceptions
		} finally {
			if (em != null && em.isOpen()) {
				em.close();
			}
		}

		nz.ac.auckland.shop.dto.Customer dtoCustomer = CustomerMapper.toDto(customer);
		return dtoCustomer;
	}

	@POST
	@Path("items")
	@Consumes("application/xml")
	public Response createItem(Item item) {
		EntityManager em = PersistenceManager.instance().createEntityManager();

		try {
			em.getTransaction().begin();

			em.persist(item);

			em.getTransaction().commit();
		} catch (Exception e) {
			// Handle exceptions
		} finally {
			if (em != null && em.isOpen()) {
				em.close();
			}
		}

		return Response.created(URI.create("/shop/items/" + item.getId())).build();
	}

	@GET
	@Path("items/{id}")
	@Produces("application/xml")
	public Item getItem(@PathParam("id") long id) {
		Item item = null;

		EntityManager em = PersistenceManager.instance().createEntityManager();

		try {
			em.getTransaction().begin();

			item = em.find(Item.class, id);

			em.getTransaction().commit();
		} catch (Exception e) {
			// Handle exceptions
		} finally {
			if (em != null && em.isOpen()) {
				em.close();
			}
		}
		return item;
	}

	@POST
	@Path("customers/{id}/purchases")
	@Consumes("application/xml")
	public void createPurchaseForCustomer(@PathParam("id") long id, Purchase purchase) {
		EntityManager em = PersistenceManager.instance().createEntityManager();

		Customer customer = null;
		
		_logger.info("Read purchase: " + purchase);

		try {
			em.getTransaction().begin();

			customer = em.find(Customer.class, id);
			_logger.info("Found customer: " + customer);
			
			customer.addPurchase(purchase);
			
			em.persist(customer);
			em.getTransaction().commit();
		} catch (Exception e) {
			// Handle exceptions
		} finally {
			if (em != null && em.isOpen()) {
				em.close();
			}
		}
		_logger.info("Customer's purchases size: " + customer.getPurchases().size());
	}

	@POST
	@Path("customers/{id}/creditCards")
	@Consumes("application/xml")
	public void createCreditCardForCustomer(@PathParam("id") long id, CreditCard creditCard) {
		EntityManager em = PersistenceManager.instance().createEntityManager();

		Customer customer = null;

		try {
			em.getTransaction().begin();

			customer = em.find(Customer.class, id);
			customer.addCreditCard(creditCard);
			
			em.persist(customer);

			em.getTransaction().commit();
		} catch (Exception e) {
			// Handle exceptions
		} finally {
			if (em != null && em.isOpen()) {
				em.close();
			}
		}
	}

	@PUT
	@Path("customers/{id}")
	@Consumes("application/xml")
	public void updateCustomer(nz.ac.auckland.shop.dto.Customer dtoCustomer) {
		EntityManager em = PersistenceManager.instance().createEntityManager();

		try {
			em.getTransaction().begin();

			Customer customer = em.find(Customer.class, dtoCustomer.getId());
			customer.setName(dtoCustomer.getName());
			customer.setAddress(dtoCustomer.getAddress());
			
			em.persist(customer);

			em.getTransaction().commit();
		} catch (Exception e) {
			// Handle exceptions
		} finally {
			if (em != null && em.isOpen()) {
				em.close();
			}
		}
	}

	@PUT
	@Path("items/{id}")
	@Consumes("application/xml")
	public void updateItem(Item newItem) {
		EntityManager em = PersistenceManager.instance().createEntityManager();

		try {
			em.getTransaction().begin();

			Item item = em.find(Item.class, newItem.getId());
			item.setName(newItem.getName());
			item.setPrice(newItem.getPrice());
			item.setDescription(newItem.getDescription());
			
			em.persist(item);

			em.getTransaction().commit();
		} catch (Exception e) {
			// Handle exceptions
		} finally {
			if (em != null && em.isOpen()) {
				em.close();
			}
		}
	}

	// /**
	// * Updates the set of a dissassociate Parolees for a given Parolee.
	// *
	// * @param id the Parolee whose dissassociates should be updated.
	// *
	// * @param dissassociates the new set of dissassociates.
	// */
	// @PUT
	// @Path("{id}/dissassociates")
	// @Consumes("application/xml")
	// public void updateDissassociates(@PathParam("id") long id,
	// Set<nz.ac.auckland.parolee.dto.Parolee> dissassociates) {
	// // Get the Parolee object from the database.
	// Parolee parolee = findParolee(id);
	//
	// // Lookup the dissassociate Parolee instances in the database.
	// Set<Parolee> dissassociatesInDatabase = new HashSet<Parolee>();
	// for(nz.ac.auckland.parolee.dto.Parolee dtoParolee : dissassociates) {
	// Parolee dissassociate = findParolee(dtoParolee.getId());
	// dissassociatesInDatabase.add(dissassociate);
	// }
	//
	// // Update the Parolee by setting its dissassociates.
	// parolee.updateDissassociates(dissassociatesInDatabase);
	//
	// // JAX-RS will add the default response code (204 No Content) to the
	// // HTTP response message.
	// }
	//
	// /**
	// * Updates a Parolee's CriminalProfile.
	// *
	// * @param id the unique identifier of the Parolee.
	// *
	// * @param profile the Parolee's updated criminal profile.
	// */
	// @PUT
	// @Path("{id}/criminal-profile")
	// @Consumes("application/xml")
	// public void updateCriminalProfile(@PathParam("id") long id,
	// CriminalProfile profile) {
	// // Get the Parolee object from the database.
	// Parolee parolee = findParolee(id);
	//
	// // Update the Parolee's criminal profile.
	// parolee.setCriminalProfile(profile);
	//
	// // JAX-RS will add the default response code (204 No Content) to the
	// // HTTP response message.
	// }
	//

	/**
	 * Returns a view of the Customer database, represented as a List of
	 * nz.ac.auckland.shop.dto.Customer objects.
	 *
	 */
	@GET
	@Path("customers")
	@Produces("application/xml")
	public Response getCustomers(@DefaultValue("1") @QueryParam("start") int start,
			@DefaultValue("1") @QueryParam("size") int size, @Context UriInfo uriInfo) {
		URI uri = uriInfo.getAbsolutePath();

		// Create list of Customers to return.
		List<nz.ac.auckland.shop.dto.Customer> customers = new ArrayList<nz.ac.auckland.shop.dto.Customer>();

		EntityManager em = PersistenceManager.instance().createEntityManager();
		long customerId = start;
		Customer customer = null;
		Customer nextcustomer = null;
		long nextId = start + size;

		try {
			em.getTransaction().begin();

			for (int i = 0; i < size; i++) {
				customer = em.find(Customer.class, customerId + i);
				customers.add(CustomerMapper.toDto(customer));
			}
			
			nextcustomer = em.find(Customer.class, nextId);

			em.getTransaction().commit();
		} catch (Exception e) {
			// Handle exceptions
		} finally {
			if (em != null && em.isOpen()) {
				em.close();
			}
		}

		GenericEntity<List<nz.ac.auckland.shop.dto.Customer>> entity = new GenericEntity<List<nz.ac.auckland.shop.dto.Customer>>(
				customers) {
		};
		
		Link previous = null;
		Link next = null;

		if (start > 1) {
			// There are previous Customers - create a previous link.
			previous = Link.fromUri(uri + "?start={start}&size={size}").rel("prev").build(start - 1, size);
		}

		_logger.info("Found next cutomer: " + nextcustomer);
		
		// CHANGE THIS
		if (nextcustomer != null) {
			// There are successive customers - create a next link.
			_logger.info("Making NEXT link");
			next = Link.fromUri(uri + "?start={start}&size={size}").rel("next").build(start + 1, size);
		}
		
		// Build a Response that contains the list of Customers plus the link
		// headers.
		ResponseBuilder builder = Response.ok(entity);
		if (previous != null) {
			builder.links(previous);
		}
		if (next != null) {
			builder.links(next);
		}
		Response response = builder.build();

		return response;
	}
	
	/**
	 * Returns a view of the Item database, represented as a List of
	 * Item objects.
	 *
	 */
	@GET
	@Path("items")
	@Produces("application/xml")
	public Response getItems(@DefaultValue("1") @QueryParam("start") int start,
			@DefaultValue("1") @QueryParam("size") int size) {

		// Create list of Items to return.
		List<Item> items = new ArrayList<Item>();

		EntityManager em = PersistenceManager.instance().createEntityManager();
		long itemId = start;
		Item item = null;

		try {
			em.getTransaction().begin();

			for (int i = 0; i < size; i++) {
				item = em.find(Item.class, itemId + i);
				items.add(item);
			}
			
			em.getTransaction().commit();
		} catch (Exception e) {
			// Handle exceptions
		} finally {
			if (em != null && em.isOpen()) {
				em.close();
			}
		}

		GenericEntity<List<Item>> entity = new GenericEntity<List<Item>>(items) {
		};
		
		// Build a Response that contains the list of Customers plus the link
		// headers.
		ResponseBuilder builder = Response.ok(entity);
		Response response = builder.build();

		return response;
	}
	
	@GET
	@Path("customers/{id}/creditCards")
	@Produces("application/xml")
	public Response getCreditCards(@PathParam("id") long id) {
		Customer customer = null;
		Set<CreditCard> creditCards = new HashSet<CreditCard>();

		EntityManager em = PersistenceManager.instance().createEntityManager();

		try {
			em.getTransaction().begin();

			customer = em.find(Customer.class, id);
			creditCards = customer.getCreditCards();

			em.getTransaction().commit();
		} catch (Exception e) {
			// Handle exceptions
		} finally {
			if (em != null && em.isOpen()) {
				em.close();
			}
		}

		GenericEntity<Set<CreditCard>> entity = new GenericEntity<Set<CreditCard>>(creditCards) {
		};

		ResponseBuilder builder = Response.ok(entity);
		Response response = builder.build();

		return response;
	}
	
	@GET
	@Path("customers/{id}/purchases")
	@Produces("application/xml")
	public Response getPurchases(@PathParam("id") long id) {
		Customer customer = null;
		List<Purchase> purchases = new ArrayList<Purchase>();

		EntityManager em = PersistenceManager.instance().createEntityManager();

		try {
			em.getTransaction().begin();

			customer = em.find(Customer.class, id);
			purchases = customer.getPurchases();
			
			_logger.info("Found Customer: " + customer);
			_logger.info("Customer's purchases size: " + customer.getPurchases().size());
			
			em.getTransaction().commit();
		} catch (Exception e) {
			// Handle exceptions
		} finally {
			if (em != null && em.isOpen()) {
				em.close();
			}
		}

		GenericEntity<List<Purchase>> entity = new GenericEntity<List<Purchase>>(purchases) {
		};

		ResponseBuilder builder = Response.ok(entity);
		Response response = builder.build();

		return response;
	}
	
	/**
	 * Re-populates database with data every time the application runs
	 */
	protected void reloadDatabase() {

		EntityManager em = PersistenceManager.instance().createEntityManager();

		// === Initialise Item #1
		Item item1 = new Item("The Kite Runner", 12.00, "book");

		// === Initialise Item #2
		Item item2 = new Item("iPhone", 80.00, "");

		// === Initialise Customer #1
		Address address1 = new Address("15", "Bermuda road", "St Johns", "Auckland", "1071");
		Customer customer1 = new Customer("Oliver", address1);

		Calendar calendar = Calendar.getInstance();
		calendar.set(2016, Calendar.SEPTEMBER, 12, 1, 58, 0);
		Date date = calendar.getTime();

		customer1.addPurchase(new Purchase(new Item("speakers", 25.00, null), date));

		calendar.set(2016, Calendar.SEPTEMBER, 18, 1, 5, 0);
		date = calendar.getTime();

		customer1.addPurchase(new Purchase(new Item("headphones", 17.00, null), date));

		// === Initialise Customer #2
		Address address2 = new Address("22", "Tarawera Terrace", "St Heliers", "Auckland", "1071");
		Customer customer2 = new Customer("Catherine", address2);

		// === Initialise Customer #3
		Address address3 = new Address("67", "Drayton Gardens", "Oraeki", "Auckland", "1071");
		Customer customer3 = new Customer("Nasser", address3);

		try {
			em.getTransaction().begin();

			em.persist(item1);
			em.persist(item2);
			em.persist(customer1);
			em.persist(customer2);
			em.persist(customer3);

			em.getTransaction().commit();
		} catch (Exception e) {
			// Handle exceptions
		} finally {
			if (em != null && em.isOpen()) {
				em.close();
			}
		}
	}
}

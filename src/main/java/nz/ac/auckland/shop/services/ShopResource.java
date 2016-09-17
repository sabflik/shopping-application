package nz.ac.auckland.shop.services;

import java.net.URI;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

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

//	@PUT
//	public void reloadData() {
//		reloadDatabase();
//	}

	@POST
	@Path("customers")
	@Consumes("application/xml")
	public Response createCustomer(nz.ac.auckland.shop.dto.Customer dtoCustomer) {
		EntityManager em = PersistenceManager.instance().createEntityManager();

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

//	@POST
//	@Path("customers/{id}/purchases")
//	@Consumes("application/xml")
//	public void createPurchaseForCustomer(@PathParam("id") long id,
//		Purchase purchase) {
//		EntityManager em = PersistenceManager.instance().createEntityManager();
//		
//		Customer customer = null;
//		
//		try {
//			em.getTransaction().begin();
//
//			customer = em.find(Customer.class, id);
//			customer.addPurchase(purchase);
//
//			em.getTransaction().commit();
//		} catch (Exception e) {
//			// Handle exceptions
//		} finally {
//			if (em != null && em.isOpen()) {
//				em.close();
//			}
//		}
//	}
	
	@POST
	@Path("customers/{id}/creditCards")
	@Consumes("application/xml")
	public void createCreditCardForCustomer(@PathParam("id") long id,
		CreditCard creditCard) {
		EntityManager em = PersistenceManager.instance().createEntityManager();
		
		Customer customer = null;
		
		try {
			em.getTransaction().begin();

			customer = em.find(Customer.class, id);
			customer.addCreditCard(creditCard);

			em.getTransaction().commit();
		} catch (Exception e) {
			// Handle exceptions
		} finally {
			if (em != null && em.isOpen()) {
				em.close();
			}
		}
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
		
		GenericEntity<Set<CreditCard>> entity = 
				new GenericEntity<Set<CreditCard>>(creditCards) {};
				
		ResponseBuilder builder = Response.ok(entity);
		Response response = builder.build();
		
		return response;
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

	// /**
	// * Returns a view of the Parolee database, represented as a List of
	// * nz.ac.auckland.parolee.dto.Parolee objects.
	// *
	// */
	// @GET
	// @Produces("application/xml")
	// public Response getParolees(@DefaultValue("1") @QueryParam("start") int
	// start,
	// @DefaultValue("1") @QueryParam("size")int size,
	// @Context UriInfo uriInfo) {
	// URI uri = uriInfo.getAbsolutePath();
	//
	// Link previous = null;
	// Link next = null;
	//
	// if(start > 1) {
	// // There are previous Parolees - create a previous link.
	// previous = Link.fromUri(uri + "?start={start}&size={size}")
	// .rel("prev")
	// .build(start - 1, size);
	// }
	// if(start + size <= _paroleeDB.size()) {
	// // There are successive parolees - create a next link.
	// _logger.info("Making NEXT link");
	// next = Link.fromUri(uri + "?start={start}&size={size}")
	// .rel("next")
	// .build(start + 1, size);
	// }
	//
	// // Create list of Parolees to return.
	// List<nz.ac.auckland.parolee.dto.Parolee> parolees =
	// new ArrayList<nz.ac.auckland.parolee.dto.Parolee>();
	// long paroleeId = start;
	// for(int i = 0; i < size; i++) {
	// Parolee parolee = _paroleeDB.get(paroleeId);
	// parolees.add(ParoleeMapper.toDto(parolee));
	// }
	//
	// // Create a GenericEntity to wrap the list of Parolees to return. This
	// // is necessary to preserve generic type data when using any
	// // MessageBodyWriter to handle translation to a particular data format.
	// GenericEntity<List<nz.ac.auckland.parolee.dto.Parolee>> entity =
	// new GenericEntity<List<nz.ac.auckland.parolee.dto.Parolee>>(parolees) {};
	//
	// // Build a Response that contains the list of Parolees plus the link
	// // headers.
	// ResponseBuilder builder = Response.ok(entity);
	// if(previous != null) {
	// builder.links(previous);
	// }
	// if(next != null) {
	// builder.links(next);
	// }
	// Response response = builder.build();
	//
	// // Return the custom Response. The JAX-RS run-time will process this,
	// // extracting the List of Parolee objects and marshalling them into the
	// // HTTP response message body. In addition, since the Response object
	// // contains headers (previous and/or next), these will be added to the
	// // HTTP response message. The Response object was created with the 200
	// // Ok status code, and this too will be added for the status header.
	// return response;
	// }
	//
	// /**
	// * Returns movement history for a particular Parolee.
	// *
	// * @param id
	// * the unique identifier of the Parolee.
	// *
	// */
	// @GET
	// @Path("{id}/movements")
	// @Produces("application/xml")
	// public List<Movement> getMovements(@PathParam("id") long id) {
	// // Get the Parolee object from the database.
	// Parolee parolee = findParolee(id);
	//
	// // Return the Parolee's movements.
	// return parolee.getMovements();
	//
	// // JAX-RS will processed the returned value, marshalling it and storing
	// // it in the HTTP response message body. It will use the default status
	// // code of 200 Ok.
	// }
	//
	// /**
	// * Returns the dissassociates associated directly with a particular
	// Parolee.
	// * Each dissassociate is represented as an instance of class
	// * nz.ac.auckland.parolee.dto.Parolee.
	// *
	// * @param id
	// * the unique identifier of the Parolee.
	// */
	// @GET
	// @Path("{id}/dissassociates")
	// @Produces("application/xml")
	// public List<nz.ac.auckland.parolee.dto.Parolee> getParoleeDissassociates(
	// @PathParam("id") long id) {
	// // Get the Parolee object from the database.
	// Parolee parolee = findParolee(id);
	//
	// List<nz.ac.auckland.parolee.dto.Parolee> dissassociates = new
	// ArrayList<nz.ac.auckland.parolee.dto.Parolee>();
	//
	// for (Parolee dissassociate : parolee.getDissassociates()) {
	// dissassociates.add(ParoleeMapper.toDto(dissassociate));
	// }
	// return dissassociates;
	//
	// // JAX-RS will processed the returned value, marshalling it and storing
	// // it in the HTTP response message body. It will use the default status
	// // code of 200 Ok.
	// }
	//
	// /**
	// * Returns the CriminalProfile for a particular parolee.
	// *
	// * @param id the unique identifier of the Parolee.
	// */
	// @GET
	// @Path("{id}/criminal-profile")
	// @Produces("application/xml")
	// public CriminalProfile getCriminalProfile(@PathParam("id") long id) {
	// // Get the Parolee object from the database.
	// Parolee parolee = findParolee(id);
	//
	// return parolee.getCriminalProfile();
	//
	// // JAX-RS will processed the returned value, marshalling it and storing
	// // it in the HTTP response message body. It will use the default status
	// // code of 200 Ok.
	// }

	protected void reloadDatabase() {

		EntityManager em = PersistenceManager.instance().createEntityManager();

		 // === Initialise Item #1
		 Item item1 = new Item("The Kite Runner", 12.00, "book");
		
		 // === Initialise Item #2
		 Item item2 = new Item("iPhone", 80.00, null);
		
		 // === Initialise Customer #1
		 Address address1 = new Address("15", "Bermuda road", "St Johns",
		 "Auckland", "1071");
		 Customer customer1 = new Customer("Oliver", address1);
		
		
		 Calendar calendar = Calendar.getInstance();
		 calendar.set(2016, Calendar.SEPTEMBER, 12, 1, 58, 0);
		 Date date = calendar.getTime();
		
		 customer1.addPurchase(new Purchase(new Item("speakers", 25.00, null), date));
		
		
		 // === Initialise Customer #2
		 Address address2 = new Address("22", "Tarawera Terrace", "St Heliers",
		 "Auckland", "1071");
		 Customer customer2 = new Customer("Catherine", address2);
		
		 // === Initialise Customer #3
		 Address address3 = new Address("67", "Drayton Gardens", "Oraeki", "Auckland",
		 "1071");
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

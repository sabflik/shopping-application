package nz.ac.auckland.shop.services;

import java.net.URI;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nz.ac.auckland.shop.domain.Customer;
import nz.ac.auckland.shop.domain.Purchase;

@Path("/shop")
public class ShopResource {

	private static final Logger _logger = LoggerFactory.getLogger(ShopResource.class);

	private Map<Long, Customer> _customerDB;
	private AtomicLong _idCounter;

	public ShopResource() {
//		reloadDatabase();
	}

	@PUT
	public void reloadData() {
//		reloadDatabase();
	}
	
	@POST
	@Consumes("application/xml")
	public Response createCustomer(
			nz.ac.auckland.shop.dto.Customer dtoCustomer) {
		_logger.debug("Read customer: " + dtoCustomer);
		Customer customer = CustomerMapper.toDomainModel(dtoCustomer);
		customer.setId(_idCounter.incrementAndGet());
		_customerDB.put(customer.getId(), customer);

		_logger.debug("Created customer: " + customer);
		
		return Response.created(URI.create("/customers/" + customer.getId()))
				.build();
	}
	
	@POST
	@Path("{id}/purchases")
	@Consumes("application/xml")
	public void createPurchaseForCustomer(@PathParam("id") long id,
			Purchase purchase) {
		Customer customer = findCustomer(id);
		customer.addPurchase(purchase);
		
		// JAX-RS will add the default response code to the HTTP response 
		// message.
	}
	
	@PUT
	@Path("{id}")
	@Consumes("application/xml")
	public void updateCustomer(
			nz.ac.auckland.shop.dto.Customer dtoCustomer) {
		// Get the Parolee object from the database.
		Customer customer = findCustomer(dtoCustomer.getId());

		// Update the Parolee object in the database based on the data in
		// dtoParolee.
		customer.setName(dtoCustomer.getName());
		customer.setAddress(dtoCustomer.getAddress());
		
		// JAX-RS will add the default response code (204 No Content) to the
		// HTTP response message.
	}
	
	
	
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
//	/**
//	 * Returns a particular Parolee. The returned Parolee is represented by a
//	 * nz.ac.auckland.parolee.dto.Parolee object.
//	 * 
//	 * @param id
//	 *            the unique identifier of the Parolee.
//	 * 
//	 */
//	@GET
//	@Path("{id}")
//	@Produces("application/xml")
//	public nz.ac.auckland.parolee.dto.Parolee getParolee(
//			@PathParam("id")long id) {
//		// Get the Parolee object from the database.
//		Parolee parolee = findParolee(id);
//
//		// Convert the Parolee to a Parolee DTO.
//		nz.ac.auckland.parolee.dto.Parolee dtoParolee = ParoleeMapper.toDto(parolee);
//		
//		// JAX-RS will processed the returned value, marshalling it and storing
//		// it in the HTTP response message body. It will use the default status 
//		// code of 200 Ok.
//		return dtoParolee;
//	}
//
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

	protected Customer findCustomer(long id) {
		return _customerDB.get(id);
	}
	
//	protected void reloadDatabase() {
//	_paroleeDB = new ConcurrentHashMap<Long, Parolee>();
//	_idCounter = new AtomicLong();
//
//	// === Initialise Parolee #1
//	long id = _idCounter.incrementAndGet();
//	Address address = new Address("15", "Bermuda road", "St Johns", "Auckland", "1071");
//	Parolee parolee = new Parolee(id,
//			"Sinnen", 
//			"Oliver", 
//			Gender.MALE,
//			new LocalDate(1970, 5, 26),
//			address,
//			new Curfew(address, new LocalTime(20, 00),new LocalTime(06, 30)));
//	_paroleeDB.put(id, parolee);
//
//	CriminalProfile profile = new CriminalProfile();
//	profile.addConviction(new CriminalProfile.Conviction(new LocalDate(
//			1994, 1, 19), "Crime of passion", Offence.MURDER,
//			Offence.POSSESION_OF_OFFENSIVE_WEAPON));
//	parolee.setCriminalProfile(profile);
//
//	DateTime now = new DateTime();
//	DateTime earlierToday = now.minusHours(1);
//	DateTime yesterday = now.minusDays(1);
//	GeoPosition position = new GeoPosition(-36.852617, 174.769525);
//
//	parolee.addMovement(new Movement(yesterday, position));
//	parolee.addMovement(new Movement(earlierToday, position));
//	parolee.addMovement(new Movement(now, position));
//	
//	// === Initialise Parolee #2
//	id = _idCounter.incrementAndGet();
//	address = new Address("22", "Tarawera Terrace", "St Heliers", "Auckland", "1071");
//	parolee = new Parolee(id,
//			"Watson", 
//			"Catherine", 
//			Gender.FEMALE,
//			new LocalDate(1970, 2, 9),
//			address,
//			null);
//	_paroleeDB.put(id, parolee);
//	
//	// === Initialise Parolee #3
//	id = _idCounter.incrementAndGet();
//	address = new Address("67", "Drayton Gardens", "Oraeki", "Auckland", "1071");
//	parolee = new Parolee(id,
//			"Giacaman", 
//			"Nasser", 
//			Gender.MALE,
//			new LocalDate(1980, 10, 19),
//			address,
//			null);
//	_paroleeDB.put(id, parolee);
//}
}

package nz.ac.auckland.shop.services;


import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.Set;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nz.ac.auckland.shop.domain.Address;
import nz.ac.auckland.shop.dto.Customer;

public class ShopWebServiceTest {
	private static String WEB_SERVICE_URI = "http://localhost:10000/services/shop";
	
	private static final Logger _logger = LoggerFactory.getLogger(ShopWebServiceTest.class);

	private static Client _client;

	@BeforeClass
	public static void setUpClient() {
		_client = ClientBuilder.newClient();
	}

	@Before
	public void reloadServerData() {
		WEB_SERVICE_URI = "http://localhost:10000/services/shop";
		
		Response response = _client
				.target(WEB_SERVICE_URI).request()
				.put(null);
		response.close();

		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
		}
	}

	@AfterClass
	public static void destroyClient() {
		_client.close();
	}

//	/**
//	 * Tests that the Web service can create a new Customer.
//	 */
//	@Test
//	public void addCustomer() {
//		WEB_SERVICE_URI = "http://localhost:10000/services/shop/customers";
//		
//		Address homeAddress = new Address("34", "Appleby Road", "Remuera",
//				"Auckland", "1070");
//		Customer zoran = new Customer("Zoran", homeAddress);
//
//		Response response = _client
//				.target(WEB_SERVICE_URI).request()
//				.post(Entity.xml(zoran));
//		if (response.getStatus() != 201) {
//			fail("Failed to create new Customer");
//		} 
//
//		String location = response.getLocation().toString();
//		response.close();
//
//		// Query the Web service for the new Customer.
//		Customer zoranFromService = _client.target(location).request()
//				.accept("application/xml").get(Customer.class);
//
//		assertEquals(zoran.getName(), zoranFromService.getName());
//		assertEquals(zoran.getAddress(), zoranFromService.getAddress());
//		assertEquals(zoran.getLastPurchase(),
//				zoranFromService.getLastPurchase());
//	}
//
////	/**
////	 * Tests that the Web serves can process requests to record new Parolee
////	 * movements.
////	 */
////	@Test
////	public void addParoleeMovement() {
////		Movement newLocation = new Movement(new DateTime(), new GeoPosition(
////				-36.848238, 174.762212));
////
////		Response response = _client
////				.target(WEB_SERVICE_URI + "/1/movements")
////				.request().post(Entity.xml(newLocation));
////		if (response.getStatus() != 204) {
////			fail("Failed to create new Movement");
////		}
////		response.close();
////
////		// Query the Web service for the Parolee whose location has been
////		// updated.
////		Parolee oliver = _client
////				.target(WEB_SERVICE_URI + "/1").request()
////				.accept("application/xml").get(Parolee.class);
////		assertEquals(newLocation, oliver.getLastKnownPosition());
////	}
//
//	/**
//	 * Tests that the Web service can process Customer update requests.
//	 */
//	@Test
//	public void updateCustomer() {
//		WEB_SERVICE_URI = "http://localhost:10000/services/shop/customers";
//		final String targetUri = WEB_SERVICE_URI + "/2";
//
//		// Query a Customer (Catherine) from the Web service.
//		Customer catherine = _client.target(targetUri).request()
//				.accept("application/xml").get(Customer.class);
//
//		Address originalAddress = catherine.getAddress();
//
//		// Update some of Catherine's details.
//		Address newAddress = new Address("40", "Clifton Road", "Herne Bay",
//				"Auckland", "1022");
//		catherine.setAddress(newAddress);
//
//		Response response = _client.target(targetUri).request()
//				.put(Entity.xml(catherine));
//		if (response.getStatus() != 204)
//			fail("Failed to update Customer");
//		response.close();
//
//		// Requery Customer from the Web service.
//		Customer updatedCatherine = _client.target(targetUri).request()
//				.accept("application/xml").get(Customer.class);
//
//		// Parolee's home address should have changed.
//		assertFalse(originalAddress.equals(updatedCatherine.getAddress()));
//		assertEquals(newAddress, updatedCatherine.getAddress());
//	}
//
////	/**
////	 * Tests that the Web service can add dissassociates to a Parolee.
////	 */
////	@Test
////	public void updateDissassociates() {
////		// Query Parolee Catherine from the Web service.
////		Parolee catherine = _client
////				.target(WEB_SERVICE_URI + "/2").request()
////				.accept("application/xml").get(Parolee.class);
////
////		// Query a second Parolee, Nasser.
////		Parolee nasser = _client
////				.target(WEB_SERVICE_URI + "/3").request()
////				.accept("application/xml").get(Parolee.class);
////
////		// Query Catherines's dissassociates.
////		Set<Parolee> dissassociates = _client
////				.target(WEB_SERVICE_URI + "/1/dissassociates")
////				.request().accept("application/xml")
////				.get(new GenericType<Set<Parolee>>() {
////				});
////
////		// Catherine should not yet have any recorded dissassociates.
////		assertTrue(dissassociates.isEmpty());
////
////		// Request that Nasser is added as a dissassociate to Catherine. 
////		// Because an object of a parameterized type is being sent to the Web
////		// service, it must be wrapped in a GenericEntity, so that the generic
////		// type information necessary for marshalling is preserved.
////		dissassociates.add(nasser);
////		GenericEntity<Set<Parolee>> entity = new GenericEntity<Set<Parolee>>(
////				dissassociates) {
////		};
////		
////		Response response = _client
////				.target(WEB_SERVICE_URI + "/1/dissassociates")
////				.request().put(Entity.xml(entity));
////		if (response.getStatus() != 204)
////			fail("Failed to update Parolee");
////		response.close();
////
////		// Requery Catherine's dissassociates. The GET request is expected to
////		// return a List<Parolee> object; since this is a parameterized type, a
////		// GenericType wrapper is required so that the data can be 
////		// unmarshalled.
////		Set<Parolee> updatedDissassociates = _client
////				.target(WEB_SERVICE_URI + "/1/dissassociates")
////				.request().accept("application/xml")
////				.get(new GenericType<Set<Parolee>>() {
////				});
////		// The Set of Parolees returned in response to the request for
////		// Catherine's dissassociates should contain one object with the same
////		// state (value) as the Parolee instance representing Nasser.
////		assertTrue(updatedDissassociates.contains(nasser));
////		assertEquals(1, updatedDissassociates.size());
////	}
////
////	@Test
////	public void updateCriminalProfile() {
////		final String targetUri = WEB_SERVICE_URI + "/1/criminal-profile";
////
////		CriminalProfile profileForOliver = _client.target(targetUri).request()
////				.accept("application/xml").get(CriminalProfile.class);
////
////		// Amend the criminal profile.
////		profileForOliver.addConviction(new CriminalProfile.Conviction(
////				new LocalDate(), "Shoplifting", Offence.THEFT));
////
////		// Send a Web service request to update the profile.
////		Response response = _client.target(targetUri).request()
////				.put(Entity.xml(profileForOliver));
////		if (response.getStatus() != 204)
////			fail("Failed to update CriminalProfile");
////		response.close();
////
////		// Requery Oliver's criminal profile.
////		CriminalProfile reQueriedProfile = _client.target(targetUri).request()
////				.accept("application/xml").get(CriminalProfile.class);
////
////		// The locally updated copy of Oliver's CriminalProfile should have
////		// the same value as the updated profile obtained from the Web service.
////		assertEquals(profileForOliver, reQueriedProfile);
////	}
//
//	/**
//	 * Tests that the Web service can handle requests to query a particular
//	 * Customer.
//	 */
//	@Test
//	public void queryCustomer() {
//		WEB_SERVICE_URI = "http://localhost:10000/services/shop/customers";
//		
//		Customer customer = _client
//				.target(WEB_SERVICE_URI + "/1").request()
//				.accept("application/xml").get(Customer.class);
//
//		assertEquals(1, customer.getId());
//		assertEquals("Oliver", customer.getName());
//	}
//	
//	/**
//	 * Similar to queryParolee(), but this method retrieves the Parolee using
//	 * via a Response object. Because a Response object is used, headers and
//	 * other HTTP response message data can be examined.
//	 */
//	@Test
//	public void queryCustomerWithResponse() {
//		WEB_SERVICE_URI = "http://localhost:10000/services/shop/customers";
//		
//		Response response = _client
//				.target(WEB_SERVICE_URI + "/1").request( ).get( );
//		Customer customer = response.readEntity(Customer.class);
//		
//		_logger.info("Got customer: " + customer);
//		
//		// Get the headers and print them out.
//		MultivaluedMap<String,Object> headers = response.getHeaders( );
//		_logger.info("Dumping HTTP response message headers ...");
//		for(String key : headers.keySet()) {
//			_logger.info(key + ": " + headers.getFirst(key));
//		}
//		response.close( );
//	}
//
////	/**
////	 * Tests that the Web service processes requests for all Customers.
////	 */
////	@Test
////	public void queryAllCustomers() {
////		WEB_SERVICE_URI = "http://localhost:10000/services/shop/customers";
////		
////		List<Customer> customer = _client
////				.target(WEB_SERVICE_URI + "?start=1&size=3").request()
////				.accept("application/xml")
////				.get(new GenericType<List<Customer>>() {
////				});
////		assertEquals(3, customer.size());
////	}
//	
////	/**
////	 * Tests that the Web service processes requests for Parolees using header
////	 * links for HATEOAS.
////	 */
////	@Test
////	public void queryAllParoleesUsingHATEOAS() {
////		// Make a request for Parolees (note that the Web service has default 
////		// values of 1 for the query parameters start and size. 
////		Response response = _client
////				.target(WEB_SERVICE_URI).request().get();
////		
////		// Extract links and entity data from the response.
////		Link previous = response.getLink("prev");
////		Link next = response.getLink("next");
////		List<Parolee> parolees = response.readEntity(new GenericType<List<Parolee>>() {});
////		response.close();
////		
////		// The Web service should respond with a list containing only the 
////		// first Parolee.
////		assertEquals(1, parolees.size());
////		assertEquals(1, parolees.get(0).getId());
////		
////		// Having requested the only the first parolee (by default), the Web 
////		// service should respond with a Next link, but not a previous Link.
////		assertNull(previous);
////		assertNotNull(next);
////		
////		// Invoke next link and extract response data.
////		response = _client
////				.target(next).request().get();
////		previous = response.getLink("prev");
////		next = response.getLink("next");
////		parolees = response.readEntity(new GenericType<List<Parolee>>() {});
////		response.close();
////		
////		// The second Parolee should be returned along with Previous and Next 
////		// links to the adjacent Parolees.
////		assertEquals(1, parolees.size());
////		assertEquals(2, parolees.get(0).getId());
////		assertEquals("<" + WEB_SERVICE_URI + "?start=1&size=1>; rel=\"prev\"", previous.toString());
////		assertNotNull("<" + WEB_SERVICE_URI + "?start=1&size=1>; rel=\"prev\"", next.toString());
////	}
////
////	/**
////	 * Tests that the Web service can process requests for a particular
////	 * Parolee's movements.
////	 */
////	@Test
////	public void queryParoleeMovements() {
////		List<Movement> movementsForOliver = _client
////				.target(WEB_SERVICE_URI + "/1/movements")
////				.request().accept("application/xml")
////				.get(new GenericType<List<Movement>>() {
////				});
////
////		// Oliver has 3 recorded movements.
////		assertEquals(3, movementsForOliver.size());
////	}
}

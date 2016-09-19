package nz.ac.auckland.shop.services;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.Calendar;
import java.util.Date;
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
import nz.ac.auckland.shop.domain.CardType;
import nz.ac.auckland.shop.domain.CreditCard;
import nz.ac.auckland.shop.domain.Item;
import nz.ac.auckland.shop.dto.Purchase;
import nz.ac.auckland.shop.dto.Customer;

public class ShopWebServiceTest {
	private static String WEB_SERVICE_URI = "http://localhost:10000/services/shop";

	private static final Logger _logger = LoggerFactory.getLogger(ShopWebServiceTest.class);

	private static Client _client;

	@BeforeClass
	public static void setUpClient() {
		_client = ClientBuilder.newClient();
	}

	@AfterClass
	public static void destroyClient() {
		_client.close();
	}

	/**
	 * Tests that the Web service can create a new Item.
	 */
	@Test
	public void addItem() {
		WEB_SERVICE_URI = "http://localhost:10000/services/shop/items";

		Item dvd = new Item("American Sniper DVD", 15.00, "");

		Response response = _client.target(WEB_SERVICE_URI).request().post(Entity.xml(dvd));
		if (response.getStatus() != 201) {
			fail("Failed to create new Item");
		}

		String location = response.getLocation().toString();
		response.close();

		// Query the Web service for the new Item.
		Item dvdFromService = _client.target(location).request().accept("application/xml").get(Item.class);

		assertEquals(dvd.getName(), dvdFromService.getName());
		assertEquals(dvd.getPrice(), dvdFromService.getPrice(), 0.01);
		assertEquals(dvd.getDescription(), dvdFromService.getDescription());
	}

	/**
	 * Tests that the Web service can create a new Customer.
	 */
	@Test
	public void addCustomer() {
		WEB_SERVICE_URI = "http://localhost:10000/services/shop/customers";

		Address homeAddress = new Address("34", "Appleby Road", "Remuera", "Auckland", "1070");
		Customer zoran = new Customer("Zoran", homeAddress);

		Response response = _client.target(WEB_SERVICE_URI).request().post(Entity.xml(zoran));
		if (response.getStatus() != 201) {
			fail("Failed to create new Customer");
		}

		String location = response.getLocation().toString();
		response.close();

		// Query the Web service for the new Customer.
		Customer zoranFromService = _client.target(location).request().accept("application/xml").get(Customer.class);

		assertEquals(zoran.getName(), zoranFromService.getName());
		assertEquals(zoran.getAddress(), zoranFromService.getAddress());
	}

	/**
	 * Tests that the Web service can process Customer update requests.
	 */
	@Test
	public void updateCustomer() {
		WEB_SERVICE_URI = "http://localhost:10000/services/shop/customers";
		final String targetUri = WEB_SERVICE_URI + "/2";

		// Query a Customer (Catherine) from the Web service.
		Customer catherine = _client.target(targetUri).request().accept("application/xml").get(Customer.class);

		Address originalAddress = catherine.getAddress();

		// Update some of Catherine's details.
		Address newAddress = new Address("40", "Clifton Road", "Herne Bay", "Auckland", "1022");
		catherine.setAddress(newAddress);

		Response response = _client.target(targetUri).request().put(Entity.xml(catherine));
		if (response.getStatus() != 204)
			fail("Failed to update Customer");
		response.close();

		// Requery Customer from the Web service.
		Customer updatedCatherine = _client.target(targetUri).request().accept("application/xml").get(Customer.class);

		// Customer's address should have changed.
		assertFalse(originalAddress.equals(updatedCatherine.getAddress()));
		assertEquals(newAddress, updatedCatherine.getAddress());
	}

	/**
	 * Tests that the Web service can process Item update requests.
	 */
	@Test
	public void updateItem() {
		WEB_SERVICE_URI = "http://localhost:10000/services/shop/items";
		final String targetUri = WEB_SERVICE_URI + "/2";

		// Query an Item (iPhone) from the Web service.
		Item iPhone = _client.target(targetUri).request().accept("application/xml").get(Item.class);

		String originalDescription = iPhone.getDescription();
		String newDescription = "Apple";
		iPhone.setDescription(newDescription);

		Response response = _client.target(targetUri).request().put(Entity.xml(iPhone));
		if (response.getStatus() != 204)
			fail("Failed to update Item");
		response.close();

		// Requery Item from the Web service.
		Item updatediPhone = _client.target(targetUri).request().accept("application/xml").get(Item.class);

		// Item's description should have changed.
		assertFalse(originalDescription.equals(updatediPhone.getDescription()));
		assertEquals(newDescription, updatediPhone.getDescription());
	}

	/**
	 * Tests that the Web service can handle requests to query a particular
	 * Customer.
	 */
	@Test
	public void queryCustomer() {
		WEB_SERVICE_URI = "http://localhost:10000/services/shop/customers";

		Customer customer = _client.target(WEB_SERVICE_URI + "/1").request().accept("application/xml")
				.get(Customer.class);

		assertEquals(1, customer.getId());
		assertEquals("Oliver", customer.getName());
	}

	/**
	 * Similar to queryCustomer(), but this method retrieves the Customer using
	 * via a Response object. Because a Response object is used, headers and
	 * other HTTP response message data can be examined.
	 */
	@Test
	public void queryCustomerWithResponse() {
		WEB_SERVICE_URI = "http://localhost:10000/services/shop/customers";

		Response response = _client.target(WEB_SERVICE_URI + "/1").request().get();
		Customer customer = response.readEntity(Customer.class);

		_logger.info("Got customer: " + customer);

		// Get the headers and print them out.
		MultivaluedMap<String, Object> headers = response.getHeaders();
		_logger.info("Dumping HTTP response message headers ...");
		for (String key : headers.keySet()) {
			_logger.info(key + ": " + headers.getFirst(key));
		}
		response.close();
	}

	/**
	 * Tests that the Web service processes requests for all Customers.
	 */
	@Test
	public void queryAllCustomers() {
		WEB_SERVICE_URI = "http://localhost:10000/services/shop/customers";

		List<Customer> customer = _client.target(WEB_SERVICE_URI + "?start=1&size=3").request()
				.accept("application/xml").get(new GenericType<List<Customer>>() {
				});
		assertEquals(3, customer.size());
	}

	/**
	 * Tests that the Web service processes requests for Customer using header
	 * links for HATEOAS.
	 */
	@Test
	public void queryAllCustomerUsingHATEOAS() {
		WEB_SERVICE_URI = "http://localhost:10000/services/shop/customers";

		// Make a request for Customers (note that the Web service has default
		// values of 1 for the query parameters start and size.
		Response response = _client.target(WEB_SERVICE_URI).request().get();

		// Extract links and entity data from the response.
		Link previous = response.getLink("prev");
		Link next = response.getLink("next");
		List<Customer> customers = response.readEntity(new GenericType<List<Customer>>() {
		});
		response.close();

		// The Web service should respond with a list containing only the
		// first Customer.
		assertEquals(1, customers.size());
		assertEquals(1, customers.get(0).getId());

		// Having requested the only the first customer (by default), the Web
		// service should respond with a Next link, but not a previous Link.
		assertNull(previous);
		assertNotNull(next);

		// Invoke next link and extract response data.
		response = _client.target(next).request().get();
		previous = response.getLink("prev");
		next = response.getLink("next");
		customers = response.readEntity(new GenericType<List<Customer>>() {
		});
		response.close();

		// The second Customer should be returned along with Previous and Next
		// links to the adjacent Customer.
		assertEquals(1, customers.size());
		assertEquals(2, customers.get(0).getId());
		assertEquals("<" + WEB_SERVICE_URI + "?start=1&size=1>; rel=\"prev\"", previous.toString());
		assertNotNull("<" + WEB_SERVICE_URI + "?start=1&size=1>; rel=\"prev\"", next.toString());
	}

	/**
	 * Tests that the Web service can handle requests to query a particular
	 * Item.
	 */
	@Test
	public void queryItem() {
		WEB_SERVICE_URI = "http://localhost:10000/services/shop/items";

		Item item = _client.target(WEB_SERVICE_URI + "/1").request().accept("application/xml").get(Item.class);

		assertEquals(1, item.getId());
		assertEquals("The Kite Runner", item.getName());
	}
	
	/**
	 * Tests that the Web service processes requests for all Items.
	 */
	@Test
	public void queryAllItems() {
		WEB_SERVICE_URI = "http://localhost:10000/services/shop/items";

		List<Item> items = _client.target(WEB_SERVICE_URI + "?start=1&size=2").request()
				.accept("application/xml").get(new GenericType<List<Item>>() {
				});
		assertEquals(2, items.size());
	}

	/**
	 * Tests that the Web service can process requests for a particular
	 * Customer's purchases.
	 */
	@Test
	public void queryCustomerPurchases() {
		WEB_SERVICE_URI = "http://localhost:10000/services/shop/customers";

		List<Purchase> purchasesForOliver = _client.target(WEB_SERVICE_URI + "/1/purchases").request()
				.accept("application/xml").get(new GenericType<List<Purchase>>() {
				});

		// Oliver has 2 recorded purchases.
		assertEquals(2, purchasesForOliver.size());
	}

	

	@Test
	public void addCustomerCreditCard() {
		WEB_SERVICE_URI = "http://localhost:10000/services/shop/customers";

		Calendar calendar = Calendar.getInstance();
		calendar.set(2017, Calendar.JANUARY, 1, 0, 0, 0);
		Date date = calendar.getTime();

		CreditCard creditCard = new CreditCard(CardType.DEBIT, "4234 6743 8374 9284", date);

		Response response = _client.target(WEB_SERVICE_URI + "/3/creditCards").request().post(Entity.xml(creditCard));
		if (response.getStatus() != 204) {
			fail("Failed to create new Credit Card");
		}
		response.close();

		Set<CreditCard> ccFromService = _client.target(WEB_SERVICE_URI + "/3/creditCards").request()
				.accept("application/xml").get(new GenericType<Set<CreditCard>>() {
				});

		assertEquals(1, ccFromService.size());
		CreditCard[] array = new CreditCard[ccFromService.size()];
		ccFromService.toArray(array);

		assertEquals(creditCard, array[0]);
		// assertEquals(creditCard.getExpiryDate(), array[0].getExpiryDate());
		// System.out.println(creditCard.getExpiryDate().toString());
		// System.out.println(array[0].getExpiryDate().toString());
	}

	/**
	 * Tests that the Web service can delete creditCards of a Customer.
	 */
	@Test
	public void deleteCreditCards() {
		WEB_SERVICE_URI = "http://localhost:10000/services/shop/customers";
		
		_logger.info("Deleting credit card ...");
		
		// Send a HTTP DELETE request to the Web service.
		Response response = _client.target(WEB_SERVICE_URI + "/1/creditCards").request().delete();
		if (response.getStatus() != 204)
			fail("Failed to delete Credit Cards");
		response.close();
		
		// Query Oliver's credit cards.
		Set<CreditCard> creditCards = _client.target(WEB_SERVICE_URI + "/1/creditCards").request()
				.accept("application/xml").get(new GenericType<Set<CreditCard>>() {
				});

		// Oliver should not yet have any recorded credit cards.
		assertEquals(0, creditCards.size());

	}
	
	/**
	 * Tests that the Web serves can process requests to record new Customer
	 * purchases.
	 */
	@Test
	public void addCustomerPurchase() {
		WEB_SERVICE_URI = "http://localhost:10000/services/shop/items";

		Calendar calendar = Calendar.getInstance();
		calendar.set(2016, Calendar.SEPTEMBER, 16, 23, 0, 0);
		Date date = calendar.getTime();

		Purchase newPurchase = new Purchase(date, 1, "The Kite Runner", 12.00, "book");
		
		WEB_SERVICE_URI = "http://localhost:10000/services/shop/customers";

		Response response = _client.target(WEB_SERVICE_URI + "/3/purchases").request().post(Entity.xml(newPurchase));
		if (response.getStatus() != 204) {
			fail("Failed to create new Purchase");
		}
		response.close();

		List<Purchase> purchasesForNasser = _client.target(WEB_SERVICE_URI + "/3/purchases").request()
				.accept("application/xml").get(new GenericType<List<Purchase>>() {
				});

		System.out.println(purchasesForNasser.size());
		 assertEquals(1, purchasesForNasser.size());
		
		 assertEquals(newPurchase, purchasesForNasser.get(0));
	}
}

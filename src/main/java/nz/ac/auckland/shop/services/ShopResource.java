package nz.ac.auckland.shop.services;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nz.ac.auckland.shop.domain.Customer;

@Path("/customers")
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
}

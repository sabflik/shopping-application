package nz.ac.auckland.shop.services;

import nz.ac.auckland.shop.domain.Customer;

public class CustomerMapper {
	
	static Customer toDomainModel(nz.ac.auckland.shop.dto.Customer dtoCustomer) {
		Customer fullCustomer = new Customer(dtoCustomer.getId(),
				dtoCustomer.getName(),
				dtoCustomer.getAddress());
		return fullCustomer;
	}
	
	static nz.ac.auckland.shop.dto.Customer toDto(Customer customer) {
		nz.ac.auckland.shop.dto.Customer dtoCustomer = 
				new nz.ac.auckland.shop.dto.Customer(
						customer.getId(),
						customer.getName(),
						customer.getAddress(),
						customer.getLastPurchase());
		return dtoCustomer;
		
	}

}

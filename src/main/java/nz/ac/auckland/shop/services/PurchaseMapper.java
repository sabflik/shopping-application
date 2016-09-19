package nz.ac.auckland.shop.services;

import nz.ac.auckland.shop.domain.Item;
import nz.ac.auckland.shop.domain.Purchase;

public class PurchaseMapper {

	static Purchase toDomainModel(nz.ac.auckland.shop.dto.Purchase dtoPurchase) {
		Item item = new Item(dtoPurchase.get_item_id(),
				dtoPurchase.get_item_name(),
				dtoPurchase.get_item_price(),
				dtoPurchase.get_item_description());
		
		Purchase fullPurchase = new Purchase(item,
				dtoPurchase.get_dateOfPurchase());
		return fullPurchase;
	}
	
	static nz.ac.auckland.shop.dto.Purchase toDto(Purchase purchase) {
		nz.ac.auckland.shop.dto.Purchase dtoPurchase = 
				new nz.ac.auckland.shop.dto.Purchase(
						purchase.getDateOfPurchase(),
						purchase.getItem().getId(),
						purchase.getItem().getName(),
						purchase.getItem().getPrice(),
						purchase.getItem().getDescription());
		return dtoPurchase;
		
	}
}

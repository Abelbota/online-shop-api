package org.fasttrackit.onlineshopapi;

import org.fasttrackit.onlineshopapi.domain.Cart;
import org.fasttrackit.onlineshopapi.domain.Customer;
import org.fasttrackit.onlineshopapi.domain.Product;
import org.fasttrackit.onlineshopapi.service.CartService;
import org.fasttrackit.onlineshopapi.steps.CustomerSteps;
import org.fasttrackit.onlineshopapi.steps.ProductSteps;
import org.fasttrackit.onlineshopapi.transfer.cart.SaveCartRequest;
import org.fasttrackit.onlineshopapi.transfer.customer.CustomerIdentifier;
import org.fasttrackit.onlineshopapi.transfer.product.ProductIdentifier;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CartintegrationTests {

	@Autowired
	private CartService cartService;
	@Autowired
	private ProductSteps productSteps;
	@Autowired
	private CustomerSteps customerSteps;

	@Test
	public void testAddProductsToCart_WhenValidRequest_thenReturnCart() {
		Product product = productSteps.createProduct();
		Customer customer = customerSteps.createCustomer();

		ProductIdentifier productIdentifier = new ProductIdentifier();
		productIdentifier.setId(product.getId());

		CustomerIdentifier customerIdentifier = new CustomerIdentifier();
		customerIdentifier.setId(customer.getId());


		SaveCartRequest request = new SaveCartRequest();
		request.setCustomer(customerIdentifier);
		request.setProducts(Collections.singleton(productIdentifier));

		Cart cart = cartService.addProductsToCart(request);

		// TODO: 09/05/2019 add assertions

	}

}

package org.fasttrackit.onlineshopapi;

import org.fasttrackit.onlineshopapi.domain.Customer;
import org.fasttrackit.onlineshopapi.service.CustomerService;
import org.fasttrackit.onlineshopapi.transfer.customer.CreateCustomerRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.IsNull.notNullValue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerServiceIntegrationTest {
    @Autowired
    private CustomerService customerService;

    @Test
    public void testCreateCustomer_whenValidRequest_thenReturnCustomerWithId() {

        Customer customer = createCustomer();

        assertThat(customer, notNullValue());
        assertThat(customer.getId(),greaterThan(0L));
    }

    private Customer createCustomer() {
        CreateCustomerRequest request = new CreateCustomerRequest();
        request.setFirstName("Bota");
        request.setLastName("Abel");
        request.setAddress("Str.Principala , Nr.5");
        request.setEmail("123@gmail.com");
        request.setPassword("********");
        request.setPhone("0777***11");
        request.setUsername("Alibaba.1");

        return customerService.createCustomer(request);

    }
}

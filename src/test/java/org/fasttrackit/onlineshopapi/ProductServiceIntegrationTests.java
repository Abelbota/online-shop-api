package org.fasttrackit.onlineshopapi;

import org.fasttrackit.onlineshopapi.domain.Product;
import org.fasttrackit.onlineshopapi.exception.ResourceNotFoundException;
import org.fasttrackit.onlineshopapi.service.ProductService;
import org.fasttrackit.onlineshopapi.transfer.product.CreateProductRequest;
import org.fasttrackit.onlineshopapi.transfer.product.GetProductRequest;
import org.fasttrackit.onlineshopapi.transfer.product.UpdateProductRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.awt.print.Pageable;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceIntegrationTests {

    @Autowired
    private ProductService productService;

    @Test
    public void testCreateProduct_whenValidRequest_thenReturnProductWithId() {

        Product product = createProduct();

        assertThat(product, notNullValue());
        assertThat(product.getId(), greaterThan(0L));
    }

    private Product createProduct() {
        CreateProductRequest request = new CreateProductRequest();
        request.setName("Laptop");
        request.setPrice(10);
        request.setQuantity(3);
        request.setSku("wqr45nsoij8");

        return productService.createProduct(request);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetProduct_WhenProductNotFound_thenThrowResourceNotFoundException() throws ResourceNotFoundException {
        productService.getProduct(0);

    }

    @Test
    public void testGetProduct_WhenExistingId_ThenReturnMatchingProduct() throws ResourceNotFoundException {
        Product product = createProduct();

        Product retrieveProduct = productService.getProduct(product.getId());

        assertThat(retrieveProduct.getId(), is(product.getId()));
        assertThat(retrieveProduct.getName(), is(product.getName()));
    }

    @Test
    public void testUpdateProduct_WhenValidRequestWithAllFields_ThenReturnUpdatedProduct() throws ResourceNotFoundException {
        Product createdproduct = createProduct();

        UpdateProductRequest request = new UpdateProductRequest();
        request.setName(createdproduct.getName() + "Edited");
        request.setPrice(createdproduct.getPrice() + 10);
        request.setQuantity(createdproduct.getQuantity() + 10);
        request.setSku(createdproduct.getSku() + 1010101);
        Product updatedProduct =
                productService.updateProduct(createdproduct.getId(), request);

        assertThat(updatedProduct.getName(), is(request.getName()));
        assertThat(updatedProduct.getName(), not(createdproduct.getName()));
        assertThat(updatedProduct.getPrice(), is(request.getPrice()));
        assertThat(updatedProduct.getQuantity(), is(request.getQuantity()));
        assertThat(updatedProduct.getSku(), is(request.getSku()));


    }

    @Test(expected = ResourceNotFoundException.class)
    public void testDeleteProduct_WhenExistingId_ThenProductIsDeleted() throws ResourceNotFoundException {
        Product createdProduct = createProduct();

        productService.deleteProduct(createdProduct.getId());
        productService.getProduct(createdProduct.getId());
    }

    @Test
    public void testGetProducts_WhenAllCriterialProvideAndMatching_ThenReturn() {


        Product createdProduct = createProduct();

        GetProductRequest request = new GetProductRequest();
        request.setPartialName("top");
        request.setMinimumPrice(9.9);
        request.setMaximumPrice(10.1);
        request.setMinimumQuantity(1);

        Page<Product> products =
                productService.getProducts(request, PageRequest.of(0, 10));
        assertThat(products.getTotalElements(), greaterThanOrEqualTo(1L));

//       todo:for each product from the response assert that all criteria ar matched

    }
}

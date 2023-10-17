package com.GenerativeAI.T1.controller;

import com.GenerativeAI.T1.dto.ProductDTO;
import com.GenerativeAI.T1.exception.ProductNotFoundException;
import com.GenerativeAI.T1.model.Product;
import com.GenerativeAI.T1.service.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllProducts() {
        // Arrange
        List<Product> products = new ArrayList<>();
        when(productService.getAllProducts()).thenReturn(products);

        // Act
        List<Product> result = productController.getAllProducts();

        // Assert
        assertEquals(products, result);
    }

    @Test
    public void testGetProductById() {
        // Arrange
        Long productId = 1L;
        Product product = new Product();
        when(productService.getProductById(productId)).thenReturn(product);

        // Act
        ResponseEntity<?> response = productController.getProductById(productId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(product, response.getBody());
    }

    @Test
    public void testGetProductByIdNotFound() {
        // Arrange
        Long productId = 1L;
        when(productService.getProductById(productId)).thenThrow(ProductNotFoundException.class);

        // Act
        ResponseEntity<?> response = productController.getProductById(productId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testAddProduct() {
        // Arrange
        ProductDTO productDTO = new ProductDTO();
        Product addedProduct = new Product();
        when(productService.addProduct(productDTO)).thenReturn(addedProduct);

        // Act
        ResponseEntity<Product> response = productController.addProduct(productDTO);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(addedProduct, response.getBody());
    }

    @Test
    public void testUpdateProduct() {
        // Arrange
        Long productId = 1L;
        ProductDTO updatedProductDTO = new ProductDTO();
        Product updatedProduct = new Product();
        when(productService.updateProduct(productId, updatedProductDTO)).thenReturn(updatedProduct);

        // Act
        ResponseEntity<?> response = productController.updateProduct(productId, updatedProductDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedProduct, response.getBody());
    }

    @Test
    public void testUpdateProductNotFound() {
        // Arrange
        Long productId = 1L;
        ProductDTO updatedProductDTO = new ProductDTO();
        when(productService.updateProduct(productId, updatedProductDTO)).thenThrow(ProductNotFoundException.class);

        // Act
        ResponseEntity<?> response = productController.updateProduct(productId, updatedProductDTO);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteProduct() {
        // Arrange
        Long productId = 1L;
        Product deletedProduct = new Product();
        when(productService.deleteProduct(productId)).thenReturn(deletedProduct);

        // Act
        ResponseEntity<?> response = productController.deleteProduct(productId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(deletedProduct, response.getBody());
    }

    @Test
    public void testDeleteProductNotFound() {
        // Arrange
        Long productId = 1L;
        when(productService.deleteProduct(productId)).thenThrow(ProductNotFoundException.class);

        // Act
        ResponseEntity<?> response = productController.deleteProduct(productId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}

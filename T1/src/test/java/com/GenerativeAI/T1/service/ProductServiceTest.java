package com.GenerativeAI.T1.service;

import com.GenerativeAI.T1.config.TestConfig;
import com.GenerativeAI.T1.dto.ProductDTO;
import com.GenerativeAI.T1.exception.ProductNotFoundException;
import com.GenerativeAI.T1.model.Product;
import com.GenerativeAI.T1.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = TestConfig.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Spy
    private ModelMapper modelMapper;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetProductById() {
        // Arrange
        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // Act
        Product result = productService.getProductById(productId);

        // Assert
        assertNotNull(result);
        assertEquals(productId, result.getId());
    }

    @Test
    public void testGetProductByIdNotFound() {
        // Arrange
        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(productId));
    }

    @Test
    public void testAddProduct() {
        // Arrange
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Test Product");
        productDTO.setDescription("Test Description");
        productDTO.setPrice(10.0);
        productDTO.setQuantity(5);

        Product product = modelMapper.map(productDTO, Product.class);
        when(modelMapper.map(productDTO, Product.class)).thenReturn(product);
        when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);

        // Act
        Product result = productService.addProduct(productDTO);

        // Assert
        assertNotNull(result);
        assertEquals(productDTO.getName(), result.getName());
    }

    @Test
    public void testUpdateProduct() {
        // Arrange
        Long productId = 1L;
        ProductDTO updatedProductDTO = new ProductDTO();
        updatedProductDTO.setName("Updated Product");
        updatedProductDTO.setDescription("Updated Description");
        updatedProductDTO.setPrice(15.0);
        updatedProductDTO.setQuantity(8);

        Product existingProduct = new Product();
        existingProduct.setId(productId);

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(Mockito.any(Product.class))).thenReturn(existingProduct);

        // Act
        Product result = productService.updateProduct(productId, updatedProductDTO);

        // Assert
        assertNotNull(result);
        assertEquals(updatedProductDTO.getName(), result.getName());
    }

    @Test
    public void testUpdateProductNotFound() {
        // Arrange
        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ProductNotFoundException.class, () -> productService.updateProduct(productId, new ProductDTO()));
    }

    @Test
    public void testDeleteProduct() {
        // Arrange
        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // Act
        Product result = productService.deleteProduct(productId);

        // Assert
        assertNotNull(result);
        assertEquals(productId, result.getId());
    }

    @Test
    public void testDeleteProductNotFound() {
        // Arrange
        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ProductNotFoundException.class, () -> productService.deleteProduct(productId));
    }
}


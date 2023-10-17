package com.GenerativeAI.T1.service;

import com.GenerativeAI.T1.dto.ProductDTO;
import com.GenerativeAI.T1.exception.ProductNotFoundException;
import com.GenerativeAI.T1.model.Product;
import com.GenerativeAI.T1.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        return productOptional.orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + id));
    }

    public Product addProduct(ProductDTO productDTO) {
        Product product = modelMapper.map(productDTO, Product.class);
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, ProductDTO updatedProductDTO) {
        Optional<Product> existingProductOptional = productRepository.findById(id);
        if (existingProductOptional.isPresent()) {
            Product existingProduct = existingProductOptional.get();
            modelMapper.map(updatedProductDTO, existingProduct);
            return productRepository.save(existingProduct);
        } else {
            throw new ProductNotFoundException("Product not found with ID: " + id);
        }
    }

    public Product deleteProduct(Long id) {
        Product product = getProductById(id);
        if(product != null) {
            productRepository.deleteById(id);
            return product;
        }
        throw new ProductNotFoundException("Product not found with ID: " + id);
    }

}
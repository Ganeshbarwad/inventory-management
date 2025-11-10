package com.gt.inventory_management.service;

import com.gt.inventory_management.dto.ProductDTO;
import com.gt.inventory_management.model.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    ProductDTO getProductById(Long id);

    ProductDTO updateProduct(Long id, ProductDTO dto);

    void deleteProduct(Long id);

    void updateStock(Long id, int quantity);

    Page<ProductDTO> getAllProducts(String category, String brand, Double minPrice, Double maxPrice, int page, int size);

    Product addProduct(ProductDTO dto);

    List<Product> lowStockAlert();


}

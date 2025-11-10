package com.gt.inventory_management.service;

import com.gt.inventory_management.dto.ProductDTO;
import com.gt.inventory_management.exception.ProductNotFoundException;
import com.gt.inventory_management.mapper.ProductMapper;
import com.gt.inventory_management.model.Product;
import com.gt.inventory_management.model.TransactionLog;
import com.gt.inventory_management.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceimpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TransactionLogService transactionLogService;

     //Add Product
    public Product addProduct(ProductDTO productDTO) {
        Product product = ProductMapper.toProduct(productDTO);
        product.setCreatedAt(Instant.now());
        product.setUpdatedAt(Instant.now());

        return productRepository.save(product);
    }


    //Get Product by ID
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + id));
        return ProductMapper.toDTO(product);
    }

    //Get All Products
    public Page<ProductDTO> getAllProducts(String category, String brand, Double minPrice, Double maxPrice, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products;


        BigDecimal min = (minPrice != null) ? BigDecimal.valueOf(minPrice) : BigDecimal.ZERO;
        BigDecimal max = (maxPrice != null) ? BigDecimal.valueOf(maxPrice) : BigDecimal.valueOf(Double.MAX_VALUE);


        if (category != null && !category.isEmpty() &&
                brand != null && !brand.isEmpty() &&
                minPrice != null && maxPrice != null) {

            products = productRepository.findByCategoryContainingIgnoreCaseAndBrandContainingIgnoreCaseAndPriceBetween(
                    category, brand, min, max, pageable);

        } else if (category != null && !category.isEmpty()) {
            products = productRepository.findByCategoryContainingIgnoreCase(category, pageable);

        } else if (brand != null && !brand.isEmpty()) {
            products = productRepository.findByBrandContainingIgnoreCase(brand, pageable);

        } else if (minPrice != null && maxPrice != null) {
            products = productRepository.findByPriceBetween(min, max, pageable);

        } else {
            products = productRepository.findAll(pageable);
        }

        return products.map(ProductMapper::toDTO);
    }

    //Update Product
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + id));

        existing.setName(productDTO.getName());
        existing.setDescription(productDTO.getDescription());
        existing.setBrand(productDTO.getBrand());
        existing.setCategory(productDTO.getCategory());
        existing.setUpdatedAt(Instant.now());
        existing.setPrice(productDTO.getPrice());
        existing.setQuantity(productDTO.getQuantity());
        existing.setMinStockLevel(productDTO.getMinStockLevel());

        productRepository.save(existing);
        return ProductMapper.toDTO(existing);
    }

    //Delete Product
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Product not found with ID: " + id);
        }
        productRepository.deleteById(id);
    }

    //Update Stock
    public void updateStock(Long id, int quantity) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + id));

        product.setQuantity(product.getQuantity() + quantity);
        product.setUpdatedAt(Instant.now());

        TransactionLog log = new TransactionLog();
        log.setProductId(product.getId());
        log.setQuantityChanged(quantity);
        log.setCreatedAt(Instant.now());
        if (quantity<0){
            log.setChangeType("Stock-Decreased");
        } else if (quantity>0) {
            log.setChangeType("Stock-Increased");
        }
        else{
            log.setChangeType("Adjustment");
        }
        transactionLogService.saveTransaction(log);
        productRepository.save(product);
    }


    @Override
    public List<Product> lowStockAlert() {
        List<Product> products=productRepository.findAll();

        List<Product> lowStockProducts = new ArrayList<>();
        for(Product product : products){
            if (product.getQuantity()<product.getMinStockLevel()){
              lowStockProducts.add(product);
            }
        }
        if(lowStockProducts.isEmpty())
            throw new ProductNotFoundException("Low Stock Products not found");
        return lowStockProducts;
    }
}

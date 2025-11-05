package com.gt.inventory_management.repo;

import com.gt.inventory_management.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {


    Page<Product> findByCategoryContainingIgnoreCase(String category, Pageable pageable);


    Page<Product> findByBrandContainingIgnoreCase(String brand, Pageable pageable);


    Page<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);

    Page<Product> findByCategoryContainingIgnoreCaseAndBrandContainingIgnoreCaseAndPriceBetween(
            String category,
            String brand,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            Pageable pageable
    );
}

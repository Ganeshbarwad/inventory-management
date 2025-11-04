package com.gt.inventory_management.repo;

import com.gt.inventory_management.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // ✅ Filtering by Category (Paginated)
    Page<Product> findByCategoryContainingIgnoreCase(String category, Pageable pageable);

    // ✅ Filtering by Brand (Paginated)
    Page<Product> findByBrandContainingIgnoreCase(String brand, Pageable pageable);

    // ✅ Filtering by Price Range (Paginated)
    Page<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);

    // ✅ Combined Filter (Category + Brand + Price Range)
    Page<Product> findByCategoryContainingIgnoreCaseAndBrandContainingIgnoreCaseAndPriceBetween(
            String category,
            String brand,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            Pageable pageable
    );
}

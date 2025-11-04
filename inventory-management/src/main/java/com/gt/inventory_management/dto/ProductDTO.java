package com.gt.inventory_management.dto;


import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
    private Long id;
    private String name;
    private String category;
    private String brand;
    private String description;
    private BigDecimal price;
    private Integer quantity;
    private Integer minStockLevel;
    private Instant createdAt;
    private Instant updatedAt;
    private Long dealerId;
}

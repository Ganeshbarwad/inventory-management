package com.gt.inventory_management.model;


import jakarta.persistence.*;
import lombok.*;
import lombok.Data;

import java.time.Instant;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;
    private Long userId;

    private String changeType;
    private int quantityChanged;

    private Instant createdAt;
}

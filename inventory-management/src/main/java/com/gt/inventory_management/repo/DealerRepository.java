package com.gt.inventory_management.repo;

import com.gt.inventory_management.model.Dealer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DealerRepository extends JpaRepository<Dealer,Long> {
}

package com.gt.inventory_management.repo;

import com.gt.inventory_management.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin,Long> {
}

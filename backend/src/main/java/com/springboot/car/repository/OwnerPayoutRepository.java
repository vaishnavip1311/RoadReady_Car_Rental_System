// OwnerPayoutRepository.java
package com.springboot.car.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.car.model.OwnerPayout;

public interface OwnerPayoutRepository extends JpaRepository<OwnerPayout, Integer> {
    Page<OwnerPayout> findAll(Pageable pageable);
    Page<OwnerPayout> findByCarOwnerId(int ownerId, Pageable pageable);
}

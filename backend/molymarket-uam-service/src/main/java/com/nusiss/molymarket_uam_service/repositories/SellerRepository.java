package com.nusiss.molymarket_uam_service.repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import com.nusiss.molymarket_uam_service.entities.Seller;

import java.util.Optional;

public interface SellerRepository extends JpaRepository<Seller, Long> {
    Optional<Seller> findById(Long id);

    Optional<Seller> findByUsername(String username);
}
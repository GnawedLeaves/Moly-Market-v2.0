package com.nusiss.molymarket_uam_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nusiss.molymarket_uam_service.entities.Buyer;

import java.util.Optional;

public interface BuyerRepository extends JpaRepository<Buyer, Long> {
    Optional<Buyer> findById(Long id);

    Optional<Buyer> findByUsername(String username);
}
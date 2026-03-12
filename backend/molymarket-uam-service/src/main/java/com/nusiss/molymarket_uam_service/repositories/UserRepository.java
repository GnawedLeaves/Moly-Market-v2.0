package com.nusiss.molymarket_uam_service.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nusiss.molymarket_uam_service.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    
    Optional<User> findByUsername(String username);
}
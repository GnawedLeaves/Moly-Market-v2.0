package com.nusiss.molymarket_uam_service.factories;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.nusiss.molymarket_uam_service.dtos.BuyerSellerDto;
import com.nusiss.molymarket_uam_service.entities.Buyer;
import com.nusiss.molymarket_uam_service.entities.Seller;
import com.nusiss.molymarket_uam_service.entities.User;
import com.nusiss.molymarket_uam_service.repositories.BuyerRepository;
import com.nusiss.molymarket_uam_service.repositories.SellerRepository;

@Component
public class UserFactory {

    private final PasswordEncoder encoder;
    private final BuyerRepository buyerRepository;
    private final SellerRepository sellerRepository;

    public UserFactory(PasswordEncoder encoder, BuyerRepository buyerRepository, SellerRepository sellerRepository) {
        this.encoder = encoder;
        this.buyerRepository = buyerRepository;
        this.sellerRepository = sellerRepository;
    }

    public User createUser(BuyerSellerDto user) {

        Long id = user.getId();
        String username = user.getUsername();
        String password = user.getPassword();
        String role = user.getRole();
        
        // Encode password before saving the user
        String encodedPassword = encoder.encode(password);

        if (role.equals("ROLE_BUYER")) {
            Buyer buyer = new Buyer();
            buyer.setId(id);
            buyer.setUsername(username);
            buyer.setPassword(encodedPassword);
            buyer.setRole("ROLE_BUYER");
            buyer.setAddress(user.getAddress());
            buyer.setBalance(0.0);
            
            return buyerRepository.save(buyer);
        } else if (role.equals("ROLE_SELLER")) {
            Seller seller = new Seller();
            seller.setId(id);
            seller.setUsername(username);
            seller.setPassword(encodedPassword);
            seller.setRole("ROLE_SELLER");
            seller.setUen(user.getUen());
            seller.setBalance(0.0);
            return sellerRepository.save(seller);
        }
        throw new IllegalArgumentException("Unknown role: " + role);
    }
}

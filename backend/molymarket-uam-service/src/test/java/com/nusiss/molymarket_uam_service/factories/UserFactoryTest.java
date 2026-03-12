package com.nusiss.molymarket_uam_service.factories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.nusiss.molymarket_uam_service.dtos.BuyerSellerDto;
import com.nusiss.molymarket_uam_service.entities.Buyer;
import com.nusiss.molymarket_uam_service.entities.Seller;
import com.nusiss.molymarket_uam_service.entities.User;
import com.nusiss.molymarket_uam_service.repositories.BuyerRepository;
import com.nusiss.molymarket_uam_service.repositories.SellerRepository;

@ExtendWith(MockitoExtension.class)
public class UserFactoryTest {

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private BuyerRepository buyerRepository;

    @Mock
    private SellerRepository sellerRepository;

    @InjectMocks
    private UserFactory userFactory;

    @BeforeEach
    void setUp() {
        when(encoder.encode(any())).thenReturn("encodedPassword");
    }

    @Test
    void testCreateBuyer() {
        BuyerSellerDto dto = new BuyerSellerDto();
        dto.setUsername("buyer");
        dto.setPassword("pass");
        dto.setRole("ROLE_BUYER");
        
        Buyer buyer = new Buyer();
        buyer.setUsername("buyer");
        when(buyerRepository.save(any(Buyer.class))).thenReturn(buyer);
        
        User result = userFactory.createUser(dto);
        assertNotNull(result);
        assertEquals("buyer", result.getUsername());
    }

    @Test
    void testCreateSeller() {
        BuyerSellerDto dto = new BuyerSellerDto();
        dto.setUsername("seller");
        dto.setPassword("pass");
        dto.setRole("ROLE_SELLER");
        
        Seller seller = new Seller();
        seller.setUsername("seller");
        when(sellerRepository.save(any(Seller.class))).thenReturn(seller);
        
        User result = userFactory.createUser(dto);
        assertNotNull(result);
        assertEquals("seller", result.getUsername());
    }
}

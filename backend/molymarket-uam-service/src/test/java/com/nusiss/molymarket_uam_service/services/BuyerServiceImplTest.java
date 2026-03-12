package com.nusiss.molymarket_uam_service.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nusiss.molymarket_uam_service.dtos.BuyerDto;
import com.nusiss.molymarket_uam_service.entities.Buyer;
import com.nusiss.molymarket_uam_service.repositories.BuyerRepository;

@ExtendWith(MockitoExtension.class)
public class BuyerServiceImplTest {

    @Mock
    private BuyerRepository buyerRepository;

    @InjectMocks
    private BuyerServiceImpl buyerService;

    private Buyer buyer;

    @BeforeEach
    void setUp() {
        buyer = new Buyer();
        buyer.setId(1L);
        buyer.setUsername("buyer");
        buyer.setAddress("Old Address");
    }

    @Test
    void testGetBuyerByUsername() {
        when(buyerRepository.findByUsername("buyer")).thenReturn(Optional.of(buyer));
        Buyer result = buyerService.getBuyerByUsername("buyer");
        assertNotNull(result);
        assertEquals("buyer", result.getUsername());
    }

    @Test
    void testGetBuyerById() {
        when(buyerRepository.findById(1L)).thenReturn(Optional.of(buyer));
        BuyerDto result = buyerService.getBuyerById(1L);
        assertNotNull(result);
        assertEquals("buyer", result.getUsername());
    }

    @Test
    void testEditBuyerProfile() {
        BuyerDto dto = new BuyerDto();
        dto.setId(1L);
        dto.setUsername("buyer");
        dto.setAddress("New Address");
        when(buyerRepository.findById(1L)).thenReturn(Optional.of(buyer));
        when(buyerRepository.save(any(Buyer.class))).thenReturn(buyer);
        
        BuyerDto result = buyerService.editBuyerProfile(dto);
        assertNotNull(result);
        assertEquals("New Address", result.getAddress());
        verify(buyerRepository).save(any(Buyer.class));
    }
}

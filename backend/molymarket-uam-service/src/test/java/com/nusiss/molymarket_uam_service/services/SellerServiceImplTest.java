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

import com.nusiss.molymarket_uam_service.dtos.SellerDto;
import com.nusiss.molymarket_uam_service.entities.Seller;
import com.nusiss.molymarket_uam_service.repositories.SellerRepository;

@ExtendWith(MockitoExtension.class)
public class SellerServiceImplTest {

    @Mock
    private SellerRepository sellerRepository;

    @InjectMocks
    private SellerServiceImpl sellerService;

    private Seller seller;

    @BeforeEach
    void setUp() {
        seller = new Seller();
        seller.setId(1L);
        seller.setUsername("seller");
        seller.setUen("Old UEN");
    }

    @Test
    void testGetSellerByUsername() {
        when(sellerRepository.findByUsername("seller")).thenReturn(Optional.of(seller));
        Seller result = sellerService.getSellerByUsername("seller");
        assertNotNull(result);
        assertEquals("seller", result.getUsername());
    }

    @Test
    void testGetSellerById() {
        when(sellerRepository.findById(1L)).thenReturn(Optional.of(seller));
        SellerDto result = sellerService.getSellerById(1L);
        assertNotNull(result);
        assertEquals("seller", result.getUsername());
    }

    @Test
    void testEditSellerProfile() {
        SellerDto dto = new SellerDto();
        dto.setId(1L);
        dto.setUsername("seller");
        dto.setUen("New UEN");
        when(sellerRepository.findById(1L)).thenReturn(Optional.of(seller));
        when(sellerRepository.save(any(Seller.class))).thenReturn(seller);
        
        SellerDto result = sellerService.editSellerProfile(dto);
        assertNotNull(result);
        assertEquals("New UEN", result.getUen());
        verify(sellerRepository).save(any(Seller.class));
    }
}

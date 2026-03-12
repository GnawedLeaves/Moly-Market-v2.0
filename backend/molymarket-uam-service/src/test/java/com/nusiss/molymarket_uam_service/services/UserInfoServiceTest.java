package com.nusiss.molymarket_uam_service.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.nusiss.molymarket_uam_service.dtos.BuyerSellerDto;
import com.nusiss.molymarket_uam_service.entities.Buyer;
import com.nusiss.molymarket_uam_service.entities.User;
import com.nusiss.molymarket_uam_service.factories.UserFactory;
import com.nusiss.molymarket_uam_service.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserInfoServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserFactory userFactory;

    @InjectMocks
    private UserInfoService userInfoService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new Buyer();
        user.setUsername("testuser");
        user.setPassword("password");
        user.setRole("ROLE_BUYER");
        user.setBalance(100.0);
    }

    @Test
    void testLoadUserByUsername() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        UserDetails userDetails = userInfoService.loadUserByUsername("testuser");
        assertNotNull(userDetails);
        assertEquals("testuser", userDetails.getUsername());
    }

    @Test
    void testLoadUserByUsernameNotFound() {
        when(userRepository.findByUsername("unknown")).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> userInfoService.loadUserByUsername("unknown"));
    }

    @Test
    void testAddUser() throws Exception {
        BuyerSellerDto dto = new BuyerSellerDto();
        dto.setUsername("newuser");
        dto.setRole("ROLE_BUYER");
        
        when(userRepository.findByUsername("newuser")).thenReturn(Optional.empty());
        when(userFactory.createUser(any(BuyerSellerDto.class))).thenReturn(user);
        
        String result = userInfoService.addUser(dto);
        assertEquals("Buyer Added Successfully", result);
    }

    @Test
    void testUpdateUserBalance() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        userInfoService.updateUserBalance("testuser", 50.0, "ADD");
        assertEquals(150.0, user.getBalance());
        verify(userRepository).save(user);
    }

    @Test
    void testUpdateUserBalanceSubtract() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        userInfoService.updateUserBalance("testuser", 50.0, "SUBTRACT");
        assertEquals(50.0, user.getBalance());
        verify(userRepository).save(user);
    }

    @Test
    void testUpdateUserBalanceLowBalance() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        assertThrows(RuntimeException.class, () -> userInfoService.updateUserBalance("testuser", 150.0, "SUBTRACT"));
    }
}

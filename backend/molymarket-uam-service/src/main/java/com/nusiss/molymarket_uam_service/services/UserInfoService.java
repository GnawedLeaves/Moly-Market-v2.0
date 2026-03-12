package com.nusiss.molymarket_uam_service.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nusiss.molymarket_uam_service.constants.Roles;
import com.nusiss.molymarket_uam_service.dtos.BuyerSellerDto;
import com.nusiss.molymarket_uam_service.entities.User;
import com.nusiss.molymarket_uam_service.entities.UserInfoDetails;
import com.nusiss.molymarket_uam_service.factories.UserFactory;
import com.nusiss.molymarket_uam_service.repositories.UserRepository;

import java.util.Optional;

@Service
public class UserInfoService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserFactory userFactory;  

    public UserInfoService(UserRepository userRepository, UserFactory userFactory) {
        this.userRepository = userRepository;
        this.userFactory = userFactory;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);

        // Converting User to UserDetails
        return user.map(UserInfoDetails::new)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    public User findUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
    
        return user.orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }
    
    public String addUser(BuyerSellerDto userInfo) throws Exception {
        Optional<User> existingUser = userRepository.findByUsername(userInfo.getUsername());
        
        if (existingUser.isPresent()){
            throw new Exception("Username " + userInfo.getUsername() + " already exists!");
        }

        User createdUser = userFactory.createUser(userInfo);
        String createdUserRole = createdUser.getRole();

        if (Roles.BUYER.equals(createdUserRole)) {
            return "Buyer Added Successfully";
        } else if (Roles.SELLER.equals(createdUserRole)) {
            return "Seller Added Successfully";
        } else {
            return "Unknown Role Added Successfully";  
        }
    }

    public void updateUserBalance(String username, Double amount, String operation){
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User " + username + " does not exist!"));
        updateBalance(user, amount, operation);
    }

    public void updateUserBalanceById(Long id, Double amount, String operation){
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User with ID " + id + " does not exist!"));
        updateBalance(user, amount, operation);
    }

    private void updateBalance(User user, Double amount, String operation){
        if (operation.equals("SUBTRACT") && user.getBalance() < amount){
            throw new RuntimeException("Balance is low!");
        }

        if (operation.equals("ADD")){
            user.setBalance(user.getBalance() + amount);
        } else if (operation.equals("SUBTRACT")){
            user.setBalance(user.getBalance() - amount);
        } else{
            throw new IllegalArgumentException("Wrong operation!");
        }

        userRepository.save(user);
    }
}
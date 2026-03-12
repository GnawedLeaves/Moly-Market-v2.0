package com.nusiss.molymarket_uam_service.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nusiss.molymarket_uam_service.constants.Roles;
import com.nusiss.molymarket_uam_service.dtos.BuyerDto;
import com.nusiss.molymarket_uam_service.dtos.BuyerSellerDto;
import com.nusiss.molymarket_uam_service.dtos.SellerDto;
import com.nusiss.molymarket_uam_service.dtos.AuthRequestDto;
import com.nusiss.molymarket_uam_service.entities.User;
import com.nusiss.molymarket_uam_service.entities.Buyer;
import com.nusiss.molymarket_uam_service.entities.Seller;
import com.nusiss.molymarket_uam_service.services.interfaces.BuyerService;
import com.nusiss.molymarket_uam_service.services.JwtService;
import com.nusiss.molymarket_uam_service.services.interfaces.SellerService;
import com.nusiss.molymarket_uam_service.services.UserInfoService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.security.core.Authentication;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UserController {

    private final UserInfoService userService;
    private final SellerService sellerService;
    private final JwtService jwtService;
    private final BuyerService buyerService;
    private final AuthenticationManager authenticationManager;

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome this endpoint is not securesss";
    }

    @PostMapping("/addNewUser")
    public ResponseEntity<String> addNewUser(@RequestBody BuyerSellerDto userInfo) {
        try {
            return new ResponseEntity<>(userService.addUser(userInfo), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/user/userProfile")
    @PreAuthorize("hasAnyAuthority('ROLE_BUYER', 'ROLE_SELLER')")
    public String userProfile() {
        return "Welcome to User Profile";
    }

    @GetMapping("/admin/adminProfile")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String adminProfile() {
        return "Welcome to Admin Profile";
    }

    @PostMapping("/generateToken")
    public BuyerSellerDto authenticateAndGetToken(@RequestBody AuthRequestDto authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            String username = authRequest.getUsername();
            User user = userService.findUserByUsername(username);
            String token = jwtService.generateToken(username, user.getRole());
            Long id = user.getId();

            BuyerSellerDto buyerSellerDto = new BuyerSellerDto();
            buyerSellerDto.setId(id);
            buyerSellerDto.setToken(token);
            buyerSellerDto.setUsername(user.getUsername());
            buyerSellerDto.setRole(user.getRole());
            buyerSellerDto.setBalance(user.getBalance());

            switch (user.getRole()) {
                case Roles.BUYER:
                    Buyer buyer = buyerService.getBuyerByUsername(username);
                    buyerSellerDto.setAddress(buyer.getAddress());
                    break;

                case Roles.SELLER:
                    Seller seller = sellerService.getSellerByUsername(username);
                    buyerSellerDto.setUen(seller.getUen());
                    break;

                default:
                    throw new IllegalArgumentException("Unsupported role: " + user.getRole());
            }

            return buyerSellerDto;
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }

    @GetMapping("/buyerProfile")
    @PreAuthorize("hasAnyAuthority('ROLE_BUYER', 'ROLE_SELLER')")
    public ResponseEntity<BuyerDto> getBuyerProfile(@RequestParam Long buyerId) {
        BuyerDto buyer = this.buyerService.getBuyerById(buyerId);

        if (buyer == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(buyer);
    }

    @PostMapping("/buyerProfile")
    @PreAuthorize("hasAnyAuthority('ROLE_BUYER')")
    public ResponseEntity<BuyerDto> editBuyerProfile(@RequestBody BuyerDto user) {
        BuyerDto buyer = this.buyerService.editBuyerProfile(user);
        if (buyer == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(buyer);
    }

    @DeleteMapping("/buyerProfile")
    @PreAuthorize("hasAnyAuthority('ROLE_BUYER')")
    public ResponseEntity<String> deleteBuyerProfile(@RequestParam Long buyerId) {
        BuyerDto buyer = this.buyerService.deleteBuyerProfile(buyerId);

        if (buyer == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok("Buyer deleted successfully");
    }

    @GetMapping("/sellerProfile")
    @PreAuthorize("hasAnyAuthority('ROLE_SELLER')")
    public ResponseEntity<SellerDto> getSellerProfile(@RequestParam Long sellerId) {
        SellerDto seller = this.sellerService.getSellerById(sellerId); // Handle if buyer not found
        if (seller == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(seller);
    }

    @PostMapping("/sellerProfile")
    @PreAuthorize("hasAnyAuthority('ROLE_SELLER')")
    public ResponseEntity<SellerDto> editSellerProfile(@RequestBody SellerDto user) {
        SellerDto seller = this.sellerService.editSellerProfile(user);

        if (seller == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(seller);
    }

    @DeleteMapping("/sellerProfile")
    @PreAuthorize("hasAnyAuthority('ROLE_SELLER')")
    public ResponseEntity<String> deleteSellerProfile(@RequestParam Long sellerId) {
        SellerDto seller = this.sellerService.deleteSellerProfile(sellerId);

        if (seller == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok("Seller deleted successfully");
    }

    @PostMapping("/updateBalance")
    public ResponseEntity<String> updateBalance(@RequestParam String username, @RequestParam Double amount, @RequestParam String operation) {
        try {
            userService.updateUserBalance(username, amount, operation);
            return ResponseEntity.ok("Balance updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PostMapping("/updateBalanceById")
    public ResponseEntity<String> updateBalanceById(@RequestParam Long userId, @RequestParam Double amount, @RequestParam String operation) {
        try {
            userService.updateUserBalanceById(userId, amount, operation);
            return ResponseEntity.ok("Balance updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @GetMapping("/getUserId")
    public ResponseEntity<Long> getUserIdByUsername(@RequestParam String username) {
        try {
            User user = userService.findUserByUsername(username);
            return ResponseEntity.ok(user.getId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}

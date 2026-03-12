package com.nusiss.molymarket_uam_service.mappers;


import com.nusiss.molymarket_uam_service.constants.Roles;
import com.nusiss.molymarket_uam_service.dtos.BuyerSellerDto;
import com.nusiss.molymarket_uam_service.entities.User;

public class UserMapper {
    public static BuyerSellerDto toBuyerSellerDto(User user) {
        if (user == null) {
            return null;
        }

        BuyerSellerDto buyerSellerDto = new BuyerSellerDto();
        buyerSellerDto.setUsername(user.getUsername());
        buyerSellerDto.setRole(user.getRole());
        
        if (user.getRole() == Roles.SELLER) {
            
        
        }
        
        return buyerSellerDto;
    }

    public static User toEntity(BuyerSellerDto buyerSellerDto) {
        if (buyerSellerDto == null) {
            return null;
        }

        User user = new User();

        user.setUsername(buyerSellerDto.getUsername());
        user.setRole(buyerSellerDto.getRole());
        user.setPassword(buyerSellerDto.getPassword());

        return user;
    }
}

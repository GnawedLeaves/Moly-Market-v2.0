package com.nusiss.molymarket_uam_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuyerDto { 
    
    private Long id;
    private String username;
    private String address;
    private Double balance;

}
package com.nusiss.molymarket_uam_service.services.interfaces;

import com.nusiss.molymarket_uam_service.dtos.BuyerDto;
import com.nusiss.molymarket_uam_service.entities.Buyer;

public interface BuyerService {

    BuyerDto getBuyerById(Long id);
    
    Buyer getBuyerByUsername(String username);  
    
    BuyerDto editBuyerProfile(BuyerDto buyerDto);
    
    BuyerDto deleteBuyerProfile(Long buyerId);

    String getBuyerAddress(Long buyerId);
    
}

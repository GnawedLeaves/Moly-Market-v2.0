package com.nusiss.molymarket_uam_service.services.interfaces;

import com.nusiss.molymarket_uam_service.dtos.SellerDto;
import com.nusiss.molymarket_uam_service.entities.Seller;

public interface SellerService {

    SellerDto getSellerById(Long id);
    
    Seller getSellerByUsername(String username);
    
    SellerDto editSellerProfile(SellerDto sellerDto);
    
    SellerDto deleteSellerProfile(Long sellerId);
}

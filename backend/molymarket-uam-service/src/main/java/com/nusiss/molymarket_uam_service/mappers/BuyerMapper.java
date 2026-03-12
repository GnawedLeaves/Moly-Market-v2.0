package com.nusiss.molymarket_uam_service.mappers;

import com.nusiss.molymarket_uam_service.dtos.BuyerDto;
import com.nusiss.molymarket_uam_service.entities.Buyer;

public class BuyerMapper {

    public static BuyerDto toDto(Buyer buyer) {
        if (buyer == null) {
            return null;
        }
        
        BuyerDto buyerDTO = new BuyerDto();
        buyerDTO.setId(buyer.getId());
        buyerDTO.setUsername(buyer.getUsername());
        buyerDTO.setAddress(buyer.getAddress());

        return buyerDTO;
    }

    public static Buyer toEntity(BuyerDto buyerDto) {
        if (buyerDto == null) {
            return null;
        }
        
        Buyer buyer = new Buyer();
        buyer.setId(buyerDto.getId());
        buyer.setUsername(buyerDto.getUsername());
        buyer.setAddress(buyerDto.getAddress());

        return buyer;
    }
}

package com.nusiss.molymarket_uam_service.services;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nusiss.molymarket_uam_service.dtos.BuyerDto;
import com.nusiss.molymarket_uam_service.entities.Buyer;
import com.nusiss.molymarket_uam_service.mappers.BuyerMapper;
import com.nusiss.molymarket_uam_service.repositories.BuyerRepository;
import com.nusiss.molymarket_uam_service.services.interfaces.BuyerService;

@Service
@RequiredArgsConstructor
public class BuyerServiceImpl implements BuyerService {
    private final BuyerRepository buyerRepository;
    
    @Override
    public BuyerDto getBuyerById(Long id) {
        Optional<Buyer> buyerOptional = buyerRepository.findById(id);
        
        if (buyerOptional.isPresent()) {
            return BuyerMapper.toDto(buyerOptional.get());
        } else {
            throw new RuntimeException("Buyer not found");
        }
    }

    @Override
    @Transactional
    public Buyer getBuyerByUsername(String username) {
        Optional<Buyer> buyerOptional = buyerRepository.findByUsername(username);
        
        if (buyerOptional.isPresent()) {
            return buyerOptional.get();
        } else {
            throw new RuntimeException("Buyer not found");
        }
    }

    @Override
    @Transactional
    public BuyerDto editBuyerProfile(BuyerDto buyerDto) {
        Long buyerId = buyerDto.getId();
        Optional<Buyer> buyerOptional = buyerRepository.findById(buyerId);
        
        if (buyerOptional.isPresent()) {
            Buyer buyer = buyerOptional.get();
            buyer.setUsername(buyerDto.getUsername());
            buyer.setAddress(buyerDto.getAddress());
            buyerRepository.save(buyer);
            return buyerDto;
        } else {
            throw new RuntimeException("Buyer not found");
        }
    }

    @Override
    @Transactional
    public BuyerDto deleteBuyerProfile(Long buyerId) {
        Optional<Buyer> buyerOptional = buyerRepository.findById(buyerId);

        if (buyerOptional.isPresent()) {
            Buyer buyer = buyerOptional.get();
            buyerRepository.delete(buyer);
            return BuyerMapper.toDto(buyer);
        } else {
            throw new RuntimeException("Buyer not found");
        }
    }

    @Override
    @Transactional
    public String getBuyerAddress(Long buyerId) {
        Optional<Buyer> buyerOptional = buyerRepository.findById(buyerId);
        
        if (buyerOptional.isPresent()) {
            Buyer buyer = buyerOptional.get();
            return buyer.getAddress();
        } else {
            throw new RuntimeException("Buyer not found");
        }
    }
}

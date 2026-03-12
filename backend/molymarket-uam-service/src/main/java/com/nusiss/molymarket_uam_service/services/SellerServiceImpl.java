package com.nusiss.molymarket_uam_service.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.nusiss.molymarket_uam_service.dtos.SellerDto;
import com.nusiss.molymarket_uam_service.entities.Seller;
import com.nusiss.molymarket_uam_service.mappers.SellerMapper;
import com.nusiss.molymarket_uam_service.repositories.SellerRepository;
import com.nusiss.molymarket_uam_service.services.interfaces.SellerService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService{
    private final SellerRepository sellerRepository;

    @Override
    public SellerDto getSellerById(Long id) {
        Optional<Seller> sellerOptional = sellerRepository.findById(id);
        
        if (sellerOptional.isPresent()) {
            return SellerMapper.toDto(sellerOptional.get());
        } else {
            throw new RuntimeException("Seller not found");
        }
    }

    @Override
    public Seller getSellerByUsername(String username) {
        Optional<Seller> sellerOptional = sellerRepository.findByUsername(username);
        
        if (sellerOptional.isPresent()) {
            return sellerOptional.get();
        } else {
            throw new RuntimeException("Seller not found");
        }
    }

    @Override
    @Transactional
    public SellerDto editSellerProfile(SellerDto sellerDto) {
        Long sellerId = sellerDto.getId();
        Optional<Seller> sellerOptional = sellerRepository.findById(sellerId);
        
        if (sellerOptional.isPresent()) {
            Seller seller = sellerOptional.get();
            seller.setUsername(sellerDto.getUsername());
            seller.setUen(sellerDto.getUen());
            seller.setBalance(sellerDto.getBalance());
            sellerRepository.save(seller);
            return SellerMapper.toDto(seller);
        } else {
            throw new RuntimeException("Seller not found");
        }
    }

    @Override
    @Transactional
    public SellerDto deleteSellerProfile(Long sellerId) {
        Optional<Seller> sellerOptional = sellerRepository.findById(sellerId);

        if (sellerOptional.isPresent()) {
            Seller seller = sellerOptional.get();
            sellerRepository.delete(seller);
            return SellerMapper.toDto(seller);
        } else {
            throw new RuntimeException("Seller not found");
        }
    }
    
}

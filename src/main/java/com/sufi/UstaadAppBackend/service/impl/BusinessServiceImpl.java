package com.sufi.UstaadAppBackend.service.impl;

import com.sufi.UstaadAppBackend.model.Business;
import com.sufi.UstaadAppBackend.repository.BusinessRepository;
import com.sufi.UstaadAppBackend.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BusinessServiceImpl implements BusinessService {

    @Autowired
    private BusinessRepository businessRepo;

    @Override
    public List<Business> findAll() {
        return businessRepo.findAll();
    }

    @Override
    public List<Business> findByCategory(String category) {
        return businessRepo.findByCategoryContainingIgnoreCase(category);
    }

    @Override
    public List<Business> findByCategoryAndCity(String category, String city) {
        return businessRepo.findByCategoryContainingIgnoreCaseAndCityContainingIgnoreCase(category, city);
    }

    @Override
    public List<Business> findTopRated() {
        return businessRepo.findAllByOrderByAvgRatingDesc();
    }

    @Override
    public Business save(Business business) {
        if (business.getName() == null || business.getName().isBlank()) {
            throw new IllegalArgumentException("Business name is required");
        }
        return businessRepo.save(business);
    }
    @Override
    public Optional<Business> findById(Long id) {
        return businessRepo.findById(id);
    }
    
    @Override
    public List<Business> findByCategoryContainingIgnoreCase(String category) {
        return businessRepo.findByCategoryContainingIgnoreCase(category);
    }


    

}

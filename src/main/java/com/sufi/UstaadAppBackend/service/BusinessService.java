package com.sufi.UstaadAppBackend.service;

import com.sufi.UstaadAppBackend.model.Business;
import java.util.List;
import java.util.Optional;

public interface BusinessService {
	    List<Business> findAll();
	    List<Business> findByCategory(String category);
	    List<Business> findByCategoryAndCity(String category, String city);
	    List<Business> findTopRated();
	    Business save(Business business);
	    
	    List<Business> findByCategoryContainingIgnoreCase(String category);

	    Optional<Business> findById(Long id);

}

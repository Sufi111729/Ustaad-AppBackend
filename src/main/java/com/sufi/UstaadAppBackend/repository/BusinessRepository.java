package com.sufi.UstaadAppBackend.repository;

import com.sufi.UstaadAppBackend.model.Business;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessRepository extends JpaRepository<Business, Long> {
	
	List<Business> findByCategoryContainingIgnoreCase(String category);
	
	List<Business> findByCategoryContainingIgnoreCaseAndCityContainingIgnoreCase(String category, String city);
	List<Business> findAllByOrderByAvgRatingDesc();
	

}

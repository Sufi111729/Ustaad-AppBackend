package com.sufi.UstaadAppBackend.repository;


import com.sufi.UstaadAppBackend.model.Review;
import com.sufi.UstaadAppBackend.model.Business;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByBusiness(Business business);
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.business.id = :businessId")
    Double getAverageRating(@Param("businessId") Long businessId);

}


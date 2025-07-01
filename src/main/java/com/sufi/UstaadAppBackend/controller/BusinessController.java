package com.sufi.UstaadAppBackend.controller;

import com.sufi.UstaadAppBackend.model.Business;
import com.sufi.UstaadAppBackend.model.Review;
import com.sufi.UstaadAppBackend.repository.BusinessRepository;
import com.sufi.UstaadAppBackend.repository.ReviewRepository;
import com.sufi.UstaadAppBackend.service.BusinessService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Configuration
@RestController
@RequestMapping("/api/businesses")
public class BusinessController {

    @Autowired
    private BusinessService businessService;

    @Autowired
    private BusinessRepository businessRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    private static final String SECRET_KEY = "ustaad@786";

    @GetMapping("/{id}")
    public Business getBusinessById(@PathVariable Long id) {
        return businessService.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Business not found"));
    }

    @GetMapping("/all")
    public List<Business> getAllBusinesses() {
        return businessService.findAll();
    }

    @GetMapping("/search")
    public List<Business> searchByCategory(@RequestParam String category) {
        return businessService.findByCategoryContainingIgnoreCase(category);
    }

    @GetMapping("/search-by-city")
    public List<Business> searchByCategoryAndCity(@RequestParam String category, @RequestParam String city) {
        return businessService.findByCategoryAndCity(category, city);
    }

    @GetMapping("/top-rated")
    public List<Business> getTopRatedBusinesses() {
        return businessService.findTopRated();
    }

    @PostMapping
    public Business addBusiness(@RequestBody Business business, @RequestParam String accessKey) {
        if (!SECRET_KEY.equals(accessKey)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Access Denied: Invalid Access Key");
        }
        if (business.getName() == null || business.getName().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Business name is required");
        }
        if (business.getPhone() == null || !business.getPhone().matches("\\d{10}")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Phone number must be 10 digits");
        }
        if (business.getCategory() == null || business.getCategory().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category is required");
        }
        if (business.getCity() == null || business.getCity().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "City is required");
        }
        return businessService.save(business);
    }

    @PostMapping("/upload-image")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        String uploadDir = "uploads/";
        String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename();
        Path path = Paths.get(uploadDir + fileName);
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        String imageUrl = "http://localhost:8080/" + fileName + "," +
                "http://ustaad-appbackend-production.up.railway.app/" + fileName;
return ResponseEntity.ok(imageUrl);

    }

    @PostMapping("/{id}/rate")
    public ResponseEntity<?> rateBusiness(
            @PathVariable Long id,
            @RequestParam int rating,
            @AuthenticationPrincipal User user) {

        Business business = businessRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Business not found"));

        Review review = new Review();
        review.setRating(rating);
        // Set user info as needed in your Review entity
        review.setBusiness(business);
        reviewRepository.save(review);

        business.setAvgRating(reviewRepository.getAverageRating(business.getId()));
        businessRepository.save(business);

        return ResponseEntity.ok().build();
    }
}

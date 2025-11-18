package com.example.demo.service;

import com.example.demo.model.Review;
import com.example.demo.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public void saveReview(Review review) {
        review.setDate(java.time.LocalDate.now());
        reviewRepository.save(review);
    }

    public List<Review> getLatestReviews(int count) {
        return reviewRepository.findTopNByOrderByDateDesc(count);
    }

    public List<Review> getRecentReviews(int count) {
        return reviewRepository.findTopNByOrderByDateDesc(count);
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Optional<Review> getReviewById(Long id) {
        return reviewRepository.findById(id);
    }

    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }
}

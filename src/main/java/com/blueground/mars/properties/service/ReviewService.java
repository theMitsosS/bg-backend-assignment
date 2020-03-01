package com.blueground.mars.properties.service;

import com.blueground.mars.properties.model.Review;

import java.util.List;
import java.util.UUID;

public interface ReviewService {

  Review getReviewById(UUID reviewId);

  void deleteReview(UUID reviewId);

  Review updateReview(Review review) throws CloneNotSupportedException;

  Review createReview(Review review);

  List<Review> getReviewsByUnitId(String reviewId);
}

package com.blueground.mars.properties.service;

import com.blueground.mars.properties.error.EntityNotFoundException;
import com.blueground.mars.properties.model.Review;
import com.blueground.mars.properties.model.Unit;
import com.blueground.mars.properties.repository.ReviewRepository;
import com.blueground.mars.properties.repository.UnitRepository;
import com.blueground.mars.properties.util.UnitScoreManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.blueground.mars.properties.constants.UnitsConstants.UNIT_ID;

@Service
public class ReviewServiceImpl implements ReviewService {

  @Autowired
  ReviewRepository reviewRepository;

  @Autowired
  UnitRepository unitRepository;

  @Autowired
  UnitScoreManager unitScoreManager;

  @Override
  public Review getReviewById(UUID reviewId) {
    return reviewRepository.findById(reviewId)
            .orElseThrow(() -> new EntityNotFoundException(Review.class, "reviewId", reviewId.toString()));
  }

  @Override
  public List<Review> getReviewsByUnitId(String unitId) {
    return reviewRepository.findAllByUnitId(unitId);
  }

  @Override
  public void deleteReview(UUID reviewId) {
    Review reviewToBeDeleted = reviewRepository.findById(reviewId).orElseThrow(
            () -> new EntityNotFoundException(Review.class, "reviewId", reviewId.toString()));
    int reviewStarsToBeRemoved = reviewToBeDeleted.getReviewStars();
    String matchingUnitId = reviewToBeDeleted.getUnitId();
    Unit matchingUnit = unitRepository.findById(matchingUnitId).get();
    reviewRepository.deleteById(reviewId);
    Unit updatedUnit = unitScoreManager.updateScoreOnReviewDelete(matchingUnit, reviewStarsToBeRemoved);
    unitRepository.save(updatedUnit);
  }

  @Override
  public Review updateReview(Review newReview) {
    Unit unit = unitRepository.findById(newReview.getUnitId()).orElseThrow(
            () -> new EntityNotFoundException(Review.class, UNIT_ID, newReview.getUnitId()));
    int oldReviewStars = reviewRepository.findById(newReview.getReviewId()).get().getReviewStars();
    int newReviewStars = newReview.getReviewStars();
    Unit updatedUnit = unitScoreManager.updateScoreOnReviewChange(oldReviewStars, newReviewStars, unit);
    Review updatedReview = reviewRepository.save(newReview);
    unitRepository.save(updatedUnit);
    return updatedReview;

  }

  @Override
  public Review createReview(Review review) {
    Unit unit = unitRepository.findById(review.getUnitId()).orElseThrow(
            () -> new EntityNotFoundException(Review.class, UNIT_ID, review.getUnitId()));
    Review newReview = reviewRepository.save(review);
    Unit updatedUnit = unitScoreManager.updateScoreOnNewReview(unit, review);
    unitRepository.save(updatedUnit);
    return newReview;
  }
}

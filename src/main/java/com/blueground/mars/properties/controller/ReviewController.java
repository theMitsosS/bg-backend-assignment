package com.blueground.mars.properties.controller;

import com.blueground.mars.properties.model.Review;
import com.blueground.mars.properties.service.ReviewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Api(value = "Review Management API")
@RestController
@RequestMapping("/reviews")
public class ReviewController {

  @Autowired
  ReviewService reviewService;

  @ApiOperation(value = "Gets a single review that is searched using a reviewId", response = Review.class)
  @GetMapping("/{reviewId}")
  public Review getReview(
          @PathVariable(value = "reviewId") final UUID reviewId) {
    return reviewService.getReviewById(reviewId);
  }

  @ApiOperation(value = "Gets all the reviews for a specific unit", response = Review.class, responseContainer = "List")
  @GetMapping("/unit/{unitId}")
  public List<Review> getReviewsByUnitId(
          @PathVariable(value = "unitId") final String reviewId) {
    return reviewService.getReviewsByUnitId(reviewId);
  }

  @ApiOperation(value = "Creates a new review for a specific unit and recalculates the new average score", response = Review.class)
  @PostMapping
  public ResponseEntity<Review> createReview(@Valid @RequestBody Review review) {
    Review createdReview = reviewService.createReview(review);
    return new ResponseEntity<>(createdReview, HttpStatus.CREATED);
  }

  @ApiOperation(value = "Updates an existing review for a specific unit and recalculates the new average score", response = Review.class)
  @PutMapping
  public ResponseEntity<Review> updateReview(@Valid @RequestBody Review review) throws CloneNotSupportedException {
    Review createdReview = reviewService.updateReview(review);
    return new ResponseEntity<>(createdReview, HttpStatus.OK);
  }

  @ApiOperation(value = "Deletes a review for a specific unit and recalculates the new average score", response = void.class)
  @DeleteMapping("/{reviewId}")
  public ResponseEntity<Void> deleteReview(
          @Valid @PathVariable(value = "reviewId") final UUID reviewId) {
    reviewService.deleteReview(reviewId);
    return ResponseEntity.noContent().build();
  }
}

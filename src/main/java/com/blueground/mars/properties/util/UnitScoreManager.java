package com.blueground.mars.properties.util;

import com.blueground.mars.properties.model.Review;
import com.blueground.mars.properties.model.Unit;
import org.springframework.stereotype.Component;

@Component
public class UnitScoreManager {


  public Unit updateScoreOnNewReview(Unit matchingUnit, Review newReview) {
    float newAverage = calculateNewAverageReviewScore(matchingUnit, newReview);
    int newReviewCount = matchingUnit.getTotalReviews() + 1;
    matchingUnit.setScore(newAverage);
    matchingUnit.setTotalReviews(newReviewCount);
    return matchingUnit;
  }

  public Unit updateScoreOnReviewChange(int oldReview, int updatedReview, Unit unit) {
    float currentScore = unit.getScore() * unit.getTotalReviews();
    unit.setScore((currentScore - oldReview + updatedReview) / unit.getTotalReviews());
    return unit;
  }

  public Unit updateScoreOnReviewDelete(Unit unit, int starsToBeRemoved) {
    int newReviewCount = unit.getTotalReviews() - 1;
    float newScore = newReviewCount > 0 ? ((unit.getTotalReviews() * unit.getScore()) - starsToBeRemoved) / newReviewCount : 0;
    unit.setScore(newScore);
    unit.setTotalReviews(newReviewCount);
    return unit;
  }

  private Float calculateNewAverageReviewScore(Unit matchingUnit, Review newReview) {
    int totalReviews = matchingUnit.getTotalReviews();
    int newReviewStar = newReview.getReviewStars();
    float currentTotal = matchingUnit.getScore() * matchingUnit.getTotalReviews();
    return (currentTotal + newReviewStar) / (totalReviews + 1);
  }


}

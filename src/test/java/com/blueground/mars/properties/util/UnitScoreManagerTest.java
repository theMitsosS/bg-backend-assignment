package com.blueground.mars.properties.util;

import com.blueground.mars.properties.model.Review;
import com.blueground.mars.properties.model.Unit;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

import static org.junit.Assert.assertEquals;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:spring/applicationContext.xml"})
public class UnitScoreManagerTest {

  @InjectMocks
  UnitScoreManager unitScoreManager;


  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void updateScoreOnNewReview_expectedBehaviour() {
    //GIVEN
    Unit mockUnit = getMockUnit();
    Review mockReview = getMockReview();
    //WHEN
    Unit resultingUnit = unitScoreManager.updateScoreOnNewReview(mockUnit, mockReview);
    Float newScore = resultingUnit.getScore();
    int newReviewCount = resultingUnit.getTotalReviews();
    //THEN
    assertEquals(4F, newScore, 4);
    assertEquals(3, newReviewCount);
  }


  @Test
  public void updateScoreOnReviewChange_expectedBehaviour() {
    //GIVEN
    int mockOldReview = 3;
    int mockUpdatedReview = 5;
    Unit unit = getMockUnit();
    //WHEN
    Unit resultingUnit = unitScoreManager.updateScoreOnReviewChange(mockOldReview, mockUpdatedReview, unit);
    int totalReviews = resultingUnit.getTotalReviews();
    //THEN
    assertEquals(4.5F, resultingUnit.getScore(), 4);
    assertEquals(2, totalReviews);
  }

  @Test
  public void updateScoreOnReviewDelete_expectedBehaviour() {
    //GIVEN
    int mockStarsToBeDeleted = 4;
    Unit mockUnit = getMockUnit();
    //WHEN
    Unit resultingUnit = unitScoreManager.updateScoreOnReviewDelete(mockUnit, mockStarsToBeDeleted);
    //THEN
    float newScore = resultingUnit.getScore();
    int newReviewCount = resultingUnit.getTotalReviews();
    assertEquals(1, newReviewCount);
    System.out.println(newScore);
    assertEquals(3F, newScore, 4);
  }

  private Review getMockReview() {
    Review review = new Review();
    review.setReviewId(UUID.randomUUID());
    review.setReviewStars(5);
    review.setUnitId("AFLGOT48");
    return review;
  }

  private Unit getMockUnit() {
    Unit unit = new Unit();
    unit.setScore(3.5F);
    unit.setTotalReviews(2);
    unit.setColonistId("ABCDLRf");
    unit.setUnitId("ALFOE34S");
    return unit;
  }
}
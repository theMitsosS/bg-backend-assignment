package com.blueground.mars.properties.service;

import com.blueground.mars.properties.error.EntityNotFoundException;
import com.blueground.mars.properties.model.Review;
import com.blueground.mars.properties.model.Unit;
import com.blueground.mars.properties.repository.ReviewRepository;
import com.blueground.mars.properties.repository.UnitRepository;
import com.blueground.mars.properties.util.UnitScoreManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:spring/applicationContext.xml"})
public class ReviewServiceImplTest {

  @Mock
  ReviewRepository reviewRepository;

  @Mock
  UnitRepository unitRepository;

  @Mock
  UnitScoreManager unitScoreManager;

  @InjectMocks
  ReviewServiceImpl mockReviewServiceImpl;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }


  @Test
  public void getReviewById_existingId_returnMockReview() {
    //GIVEN
    Optional<Review> expectedReview = Optional.of(new Review());
    when(reviewRepository.findById(any())).thenReturn(expectedReview);
    //WHEN
    Review actualReview = mockReviewServiceImpl.getReviewById(UUID.randomUUID());
    //THEN
    assertEquals(expectedReview.get(), actualReview);
  }

  @Test(expected = EntityNotFoundException.class)
  public void getReviewById_notExistingId_throwEntityNotFoundException() {
    //GIVEN
    when(reviewRepository.findById(any())).thenReturn(Optional.empty());
    //WHEN
    mockReviewServiceImpl.getReviewById(UUID.randomUUID());
  }

  @Test()
  public void getReviewsByUnitId() {
    mockReviewServiceImpl.getReviewsByUnitId("input");
    verify(reviewRepository).findAllByUnitId("input");
  }

  private Optional<Review> getMockReviewOptioanl() {
    Review review = new Review();
    review.setReviewId(UUID.randomUUID());
    review.setReviewStars(5);
    review.setUnitId("AFLGOT48");
    return Optional.of(review);
  }

  private Optional<Unit> getMockUnitOptional() {
    Unit unit = new Unit();
    unit.setScore(3.5F);
    unit.setTotalReviews(2);
    unit.setColonistId("ABCDLRf");
    unit.setUnitId("ALFOE34S");
    return Optional.of(unit);
  }

  @Test
  public void deleteReview_validId_expectedBehaviour() {
    //GIVEN
    Optional<Review> expectedReview = getMockReviewOptioanl();
    Optional<Unit> expectedUnit = getMockUnitOptional();
    Unit finalUnit = new Unit();
    when(reviewRepository.findById(any())).thenReturn(expectedReview);
    when(unitRepository.findById(any())).thenReturn(expectedUnit);
    when(unitScoreManager.updateScoreOnReviewDelete(expectedUnit.get(), expectedReview.get().getReviewStars())).thenReturn(finalUnit);
    UUID uuid = UUID.randomUUID();
    //WHEN
    mockReviewServiceImpl.deleteReview(uuid);
    //THEN
    verify(reviewRepository).deleteById(uuid);
    verify(unitRepository).save(finalUnit);
  }

  @Test(expected = EntityNotFoundException.class)
  public void deleteReview_notExistingId_throwEntityNotFoundException() {
    //GIVEN
    when(reviewRepository.findById(any())).thenReturn(Optional.empty());
    //WHEN
    mockReviewServiceImpl.deleteReview(UUID.randomUUID());
  }


  @Test(expected = EntityNotFoundException.class)
  public void updateReview_notExistingId_throwEntityNotFoundException() {
    //GIVEN
    when(unitRepository.findById(any())).thenReturn(Optional.empty());
    //WHEN
    mockReviewServiceImpl.updateReview(new Review());
  }


  @Test
  public void updateReview() {
    //GIVEN
    Optional<Review> newReview = getMockReviewOptioanl();
    Optional<Review> oldReview = getMockReviewOptioanl();
    Optional<Unit> expectedUnit = getMockUnitOptional();
    when(reviewRepository.findById(any())).thenReturn(oldReview);
    when(unitRepository.findById(any())).thenReturn(expectedUnit);
    //WHEN
    mockReviewServiceImpl.updateReview(newReview.get());
    //THEN
    verify(unitScoreManager).updateScoreOnReviewChange(oldReview.get().getReviewStars(), newReview.get().getReviewStars(), expectedUnit.get());

  }

  @Test
  public void createReview() {
    //GIVEN
    Optional<Review> expectedReview = getMockReviewOptioanl();
    Optional<Unit> expectedUnit = getMockUnitOptional();
    Unit unit = new Unit();
    when(unitRepository.findById(expectedReview.get().getUnitId())).thenReturn(expectedUnit);
    when(unitScoreManager.updateScoreOnNewReview(expectedUnit.get(), expectedReview.get())).thenReturn(unit);
    //WHEN
    mockReviewServiceImpl.createReview(expectedReview.get());
    //THEN
    verify(unitRepository).save(unit);
  }

  @Test(expected = EntityNotFoundException.class)
  public void createReview_notExistingId_throwEntityNotFoundException() {
    //GIVEN
    when(unitRepository.findById(any())).thenReturn(Optional.empty());
    //WHEN
    mockReviewServiceImpl.createReview(new Review());
  }
}
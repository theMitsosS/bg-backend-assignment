package com.blueground.mars.properties.service;

import com.blueground.mars.properties.error.EntityNotFoundException;
import com.blueground.mars.properties.model.Unit;
import com.blueground.mars.properties.model.UnitSearchCriteria;
import com.blueground.mars.properties.repository.ReviewRepository;
import com.blueground.mars.properties.repository.UnitRepository;
import com.blueground.mars.properties.repository.UserRepository;
import com.blueground.mars.properties.util.UnitScoreManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:spring/applicationContext.xml"})
public class UnitServiceImplTest {

  @Mock
  ReviewRepository reviewRepository;

  @Mock
  UnitRepository unitRepository;

  @Mock
  UserRepository userRepository;

  @Mock
  UnitScoreManager unitScoreManager;

  @InjectMocks
  UnitServiceImpl unitService;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void getUnitById_validUnitId_returnMock() {
    //GIVEN
    String inputString = "inputString";
    Optional<Unit> expectedUnit = getMockUnitOptional();
    when(unitRepository.findById(inputString)).thenReturn(expectedUnit);
    //WHEN
    Unit actualUnit = unitService.getUnitById(inputString);
    //THEN
    assertEquals(expectedUnit.get(), actualUnit);

  }

  @Test
  public void getUnits_containsUnitId_searchById() {
    //GIVEN
    UnitSearchCriteria unitSearchCriteria = new UnitSearchCriteria();
    Optional<Unit> unitOptional = getMockUnitOptional();
    unitSearchCriteria.setUnitId("ADLGO438");
    unitSearchCriteria.setRegion("region");
    unitSearchCriteria.setTitle("title");
    when(unitRepository.findById("ADLGO438")).thenReturn(unitOptional);
    //WHEN
    unitService.getUnits(unitSearchCriteria, null);
    //THEN
    verify(unitRepository).findById(unitSearchCriteria.getUnitId());
  }

  @Test
  public void getUnits_containsOnlytitle_searchBytitle() {
    //GIVEN
    UnitSearchCriteria unitSearchCriteria = new UnitSearchCriteria();
    unitSearchCriteria.setTitle("title");
    //WHEN
    unitService.getUnits(unitSearchCriteria, null);
    //THEN
    verify(unitRepository).findAllByTitleContainsIgnoreCaseOrderByScoreDesc(eq(unitSearchCriteria.getTitle()), any());
  }

  @Test
  public void getUnits_containsOnlyRegion_searchByTitle() {
    //GIVEN
    UnitSearchCriteria unitSearchCriteria = new UnitSearchCriteria();
    unitSearchCriteria.setRegion("region");
    //WHEN
    unitService.getUnits(unitSearchCriteria, null);
    //THEN
    verify(unitRepository).findAllByRegionContainsIgnoreCaseOrderByScoreDesc(eq(unitSearchCriteria.getRegion()), any());
  }

  @Test
  public void getUnits_containsBothRegionAndTitle_searchByBoth() {
    //GIVEN
    UnitSearchCriteria unitSearchCriteria = new UnitSearchCriteria();
    unitSearchCriteria.setRegion("region");
    unitSearchCriteria.setTitle("title");
    //WHEN
    unitService.getUnits(unitSearchCriteria, null);
    //THEN
    verify(unitRepository).findAllByTitleAndRegion(eq(unitSearchCriteria.getTitle()), eq(unitSearchCriteria.getRegion()), any());
  }


  @Test(expected = EntityNotFoundException.class)
  public void getUnits_containsNotExistentUnitId_entityNotFoundException() {
    //GIVEN
    UnitSearchCriteria unitSearchCriteria = new UnitSearchCriteria();
    unitSearchCriteria.setUnitId("ADLGO438");
    when(unitRepository.findById("ADLGO438")).thenReturn(Optional.empty());
    //WHEN
    unitService.getUnits(unitSearchCriteria, null);
  }

  @Test
  public void getAllUnitsBySlice() {
    Pageable pageable = PageRequest.of(0, 20);
    unitService.getAllUnitsBySlice(pageable);
    verify(unitRepository).findAll(pageable);
  }

  @Test
  public void getAllUnits() {
    unitService.getAllUnits();
    verify(unitRepository).findAll();
  }

  @Test(expected = EntityNotFoundException.class)
  public void updateUnit_containsNotExistentUnitId_entityNotFoundException() {
    //GIVEN
    Optional<Unit> inputUnit = getMockUnitOptional();
    when(unitRepository.existsById(inputUnit.get().getUnitId())).thenReturn(false);
    unitService.updateUnit(inputUnit.get());
  }

  @Test(expected = EntityNotFoundException.class)
  public void updateUnit_containsNotExistentReviewId_entityNotFoundException() {
    //GIVEN
    Optional<Unit> inputUnit = getMockUnitOptional();
    when(unitRepository.existsById(inputUnit.get().getUnitId())).thenReturn(true);
    when(userRepository.existsById(inputUnit.get().getColonistId())).thenReturn(false);
    unitService.updateUnit(inputUnit.get());

  }

  @Test(expected = EntityNotFoundException.class)
  public void deleteUnit_containsNotExistentUnitId_entityNotFoundException() {
    //GIVEN
    Optional<Unit> inputUnit = getMockUnitOptional();
    when(unitRepository.existsById(inputUnit.get().getUnitId())).thenReturn(false);
    unitService.deleteUnit(inputUnit.get().getUnitId());
  }

  @Test()
  public void deleteUnit_containsExistingUnitId_DeleteUnit() {
    //GIVEN
    Optional<Unit> inputUnit = getMockUnitOptional();
    when(unitRepository.existsById(inputUnit.get().getUnitId())).thenReturn(true);
    unitService.deleteUnit(inputUnit.get().getUnitId());
    verify(reviewRepository).deleteAllByUnitId(inputUnit.get().getUnitId());
    verify(unitRepository).deleteById(inputUnit.get().getUnitId());
  }

  @Test
  public void createUnit_ExistingId_KeepGenerating() {
    ArgumentCaptor<Unit> argument = ArgumentCaptor.forClass(Unit.class);
    Unit inputUnit = new Unit();
    inputUnit.setUnitId("ABCD1234");
    when(unitRepository.existsById(inputUnit.getUnitId())).thenReturn(true);
    unitService.createUnit(inputUnit);
    verify(unitRepository).save(argument.capture());
    assertNotEquals("ABCD1234", argument.getValue().getUnitId());
  }

  @Test
  public void createUnit() {
    Unit inputUnit = new Unit();
    unitService.createUnit(inputUnit);
    verify(unitRepository).save(inputUnit);
  }

  private Optional<Unit> getMockUnitOptional() {
    Unit unit = new Unit();
    unit.setScore(3.5F);
    unit.setTotalReviews(2);
    unit.setColonistId("ABCDLRf");
    unit.setUnitId("ALFOE34S");
    return Optional.of(unit);
  }
}
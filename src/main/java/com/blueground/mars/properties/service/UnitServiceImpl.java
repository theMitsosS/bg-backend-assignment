package com.blueground.mars.properties.service;

import com.blueground.mars.properties.error.EntityNotFoundException;
import com.blueground.mars.properties.model.ApplicationUser;
import com.blueground.mars.properties.model.Unit;
import com.blueground.mars.properties.model.UnitSearchCriteria;
import com.blueground.mars.properties.repository.ReviewRepository;
import com.blueground.mars.properties.repository.UnitRepository;
import com.blueground.mars.properties.repository.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

import static com.blueground.mars.properties.constants.ApplicationUsersConstants.USER_ID;
import static com.blueground.mars.properties.constants.UnitsConstants.UNIT_ID;
import static com.blueground.mars.properties.constants.UnitsConstants.UNIT_ID_LENGTH;
import static com.blueground.mars.properties.constants.UnitsConstants.UNIT_ID_USE_LETTERS;
import static com.blueground.mars.properties.constants.UnitsConstants.UNIT_ID_USE_NUMBERS;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;


@Service
public class UnitServiceImpl implements UnitService {

  @Autowired
  UnitRepository unitRepository;

  @Autowired
  ReviewRepository reviewRepository;

  @Autowired
  UserRepository userRepository;


  @Override
  public Unit getUnitById(String unitId) {
    return unitRepository.findById(unitId)
            .orElseThrow(() -> new EntityNotFoundException(Unit.class, "reviewId", unitId));
  }

  @Override
  public Slice<Unit> getUnits(
          UnitSearchCriteria unitSearchCriteria, Pageable pageable) {
    String regionSearch = unitSearchCriteria.getRegion();
    String titleSearch = unitSearchCriteria.getTitle();
    String unitIdSearch = unitSearchCriteria.getUnitId();


    if (nonNull(unitSearchCriteria.getUnitId())) {
      return new SliceImpl<>(Collections.singletonList(unitRepository.findById(unitIdSearch)
              .orElseThrow(() -> new EntityNotFoundException(Unit.class, "reviewId", unitIdSearch))));
    } else if (isNull(titleSearch)) {
      return unitRepository.findAllByRegionContainsIgnoreCaseOrderByScoreDesc(regionSearch, pageable);
    } else if (isNull(regionSearch)) {
      return unitRepository.findAllByTitleContainsIgnoreCaseOrderByScoreDesc(titleSearch, pageable);
    } else {
      return unitRepository.findAllByTitleAndRegion(titleSearch, regionSearch, pageable);
    }
  }

  @Override
  public Slice<Unit> getAllUnitsBySlice(Pageable pageable) {
    return unitRepository.findAll(pageable);
  }


  @Override
  public List<Unit> getAllUnits() {
    return unitRepository.findAll();
  }


  @Override
  public Unit updateUnit(Unit unit) {
    if (!unitRepository.existsById(unit.getUnitId())) {
      throw new EntityNotFoundException(Unit.class, UNIT_ID, unit.getUnitId());
    }
    if (!userRepository.existsById(unit.getColonistId())) {
      throw new EntityNotFoundException(ApplicationUser.class, USER_ID, unit.getColonistId());
    }
    Unit currentUnit = getUnitById(unit.getUnitId());
    Float score = currentUnit.getScore();
    Integer totalReviews = currentUnit.getTotalReviews();
    unit.setScore(score);
    unit.setTotalReviews(totalReviews);
    return unitRepository.save(unit);
  }

  @Transactional
  @Override
  public void deleteUnit(String unitId) {
    if (unitRepository.existsById(unitId)) {
      reviewRepository.deleteAllByUnitId(unitId);
      unitRepository.deleteById(unitId);
    } else {
      throw new EntityNotFoundException(Unit.class, UNIT_ID, unitId);
    }
  }

  @Override
  public Unit createUnit(Unit unit) {
    while (unitRepository.existsById(unit.getUnitId())) {
      String newId = RandomStringUtils.random(UNIT_ID_LENGTH, UNIT_ID_USE_LETTERS, UNIT_ID_USE_NUMBERS).toUpperCase();
      unit.setUnitId(newId);
    }
    return unitRepository.save(unit);
  }
}

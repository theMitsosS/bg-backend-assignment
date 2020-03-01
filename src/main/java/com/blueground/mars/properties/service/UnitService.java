package com.blueground.mars.properties.service;

import com.blueground.mars.properties.model.Unit;
import com.blueground.mars.properties.model.UnitSearchCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface UnitService {

  Unit getUnitById(String unitId);

  Slice<Unit> getUnits(UnitSearchCriteria unitSearchCriteria, Pageable pageable);

  Slice<Unit> getAllUnitsBySlice(Pageable pageable);

  List<Unit> getAllUnits();

  Unit updateUnit(Unit unit);

  void deleteUnit(String unitId);

  Unit createUnit(Unit unit);
}

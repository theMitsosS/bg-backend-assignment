package com.blueground.mars.properties.controller;

import com.blueground.mars.properties.model.Unit;
import com.blueground.mars.properties.model.UnitSearchCriteria;
import com.blueground.mars.properties.service.UnitService;
import com.blueground.mars.properties.util.PaginationUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpHeaders;
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

@RestController
@RequestMapping("/units")
@Api(value = "Unit Management API")
public class UnitController {

  @Autowired
  UnitService unitService;

  @GetMapping("/{unitId}")
  @ApiOperation(value = "Gets a unit searching by a unitId", response = Unit.class)
  public ResponseEntity<Unit> getUnit(
          @PathVariable(value = "unitId") final String unitId) {
    Unit requestedUnit = unitService.getUnitById(unitId);
    return ResponseEntity.ok(requestedUnit);
  }

  @GetMapping
  @ApiOperation(value = "View all the units stored in the database", response = Unit.class, responseContainer = "List")
  public List<Unit> getAllUnits() {
    return unitService.getAllUnits();
  }

  @GetMapping("/pages")
  @ApiOperation(value = "Gets all units stored in the database with pagination implemented (2)", response = Unit.class, responseContainer = "List")
  public ResponseEntity<List<Unit>> getUnitsByPage(
          @SortDefault(sort = "score", direction = Sort.Direction.DESC) Pageable pageable) {
    Slice<Unit> slice = unitService.getAllUnitsBySlice(pageable);
    //populate  the header to let the front end know if there is a next page.
    HttpHeaders headers = PaginationUtil.generateSliceHttpHeaders(slice);
    return new ResponseEntity<>(slice.getContent(), headers, HttpStatus.OK);
  }

  @PostMapping
  @ApiOperation(value = "Creates a new unit", response = Unit.class)
  public ResponseEntity<Unit> createUnit(@Valid @RequestBody Unit unit) {
    Unit createdUnit = unitService.createUnit(unit);
    return new ResponseEntity<>(createdUnit, HttpStatus.CREATED);
  }

  @PutMapping
  @ApiOperation(value = "Updates a unit", response = Unit.class)
  public ResponseEntity<Unit> updateUnit(@Valid @RequestBody Unit unit) {
    Unit updateUnit = unitService.updateUnit(unit);
    return new ResponseEntity<>(updateUnit, HttpStatus.OK);
  }


  @DeleteMapping("/{unitId}")
  @ApiOperation(value = "Deletes a unit", response = void.class)
  public ResponseEntity<Void> deleteUnit(
          @Valid @PathVariable(value = "unitId") final String unitId) {
    unitService.deleteUnit(unitId);
    return ResponseEntity.noContent().build();
  }


  @PostMapping("/search")
  @ApiOperation(value = "Searches for a unit, with region, title or unitId criteria & pagination implemented", response = Unit.class, responseContainer = "List")
  public ResponseEntity<List<Unit>> getUnitsSearchResults(@SortDefault(sort = "score", direction = Sort.Direction.DESC)
                                                          @Valid @RequestBody UnitSearchCriteria unitSearchCriteria,
                                                          Pageable pageable) {
    Slice<Unit> unitSearchResults = unitService.getUnits(unitSearchCriteria, pageable);
    //populate  the header to let the front end know if there is a next page  ("Has-Next-Page" header)
    HttpHeaders headers = PaginationUtil.generateSliceHttpHeaders(unitSearchResults);
    return new ResponseEntity<>(unitSearchResults.getContent(), headers, HttpStatus.OK);
  }
}

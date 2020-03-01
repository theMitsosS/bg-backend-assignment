package com.blueground.mars.properties.repository;

import com.blueground.mars.properties.model.Unit;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UnitRepository extends JpaRepository<Unit, String> {


  Slice<Unit> findAllByTitleContainsIgnoreCaseOrderByScoreDesc(String title, Pageable pageable);

  Slice<Unit> findAllByRegionContainsIgnoreCaseOrderByScoreDesc(String title, Pageable pageable);

  @Query("select u from units u where upper(u.title) like upper(concat('%', ?1,'%')) and upper(u.region) like upper(concat('%', ?2,'%')) ")
  Slice<Unit> findAllByTitleAndRegion(String title, String region, Pageable pageable);

  List<Unit> findAll();

}

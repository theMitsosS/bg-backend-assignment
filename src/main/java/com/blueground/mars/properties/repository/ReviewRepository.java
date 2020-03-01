package com.blueground.mars.properties.repository;

import com.blueground.mars.properties.model.Review;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface ReviewRepository extends CrudRepository<Review, UUID> {

  @Modifying
  void deleteAllByUnitId(String unitId);

  List<Review> findAllByUnitId(String unitId);
}

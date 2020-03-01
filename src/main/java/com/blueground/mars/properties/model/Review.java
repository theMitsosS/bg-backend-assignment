package com.blueground.mars.properties.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

import static com.blueground.mars.properties.constants.UnitsConstants.MAX_REVIEW_STAR;
import static com.blueground.mars.properties.constants.UnitsConstants.MAX_REVIEW_STAR_MESSAGE;
import static com.blueground.mars.properties.constants.UnitsConstants.MIN_REVIEW_STAR;
import static com.blueground.mars.properties.constants.UnitsConstants.MIN_REVIEW_STAR_MESSAGE;

@Entity(name = "reviews")
@Table(name = "reviews")
@Getter
@Setter
@ApiModel(description = "The \"Review\" object of the application")
public class Review {

  @Id
  @ApiModelProperty(value = "The automatically generated reviewId", example = "731efc78-5738-11ea-8e2d-0242ac130003")
  private UUID reviewId = UUID.randomUUID();

  @NotNull(message = "A unit ID must be provided")
  @ApiModelProperty(value = "The unitId of the unit", example = "FJVLTO81")
  private String unitId;

  @NotNull(message = "A score for the review must be provided")
  @Min(value = MIN_REVIEW_STAR, message = MIN_REVIEW_STAR_MESSAGE)
  @Max(value = MAX_REVIEW_STAR, message = MAX_REVIEW_STAR_MESSAGE)
  @ApiModelProperty(value = "The score (review stars) of the unit", example = "5")
  private Integer reviewStars;
  @ApiModelProperty(value = "The optional comment about the unit", example = "This place is amazing", allowEmptyValue = true)
  private String comment;

}

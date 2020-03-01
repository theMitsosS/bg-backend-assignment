package com.blueground.mars.properties.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;

import javax.persistence.Entity;
import javax.persistence.Id;

import static com.blueground.mars.properties.constants.UnitsConstants.UNIT_ID_LENGTH;
import static com.blueground.mars.properties.constants.UnitsConstants.UNIT_ID_USE_LETTERS;
import static com.blueground.mars.properties.constants.UnitsConstants.UNIT_ID_USE_NUMBERS;

@Entity(name = "units")
@Getter
@Setter
@ApiModel(description = "The \"Unit\" object of the application")
public class Unit {

  @Id
  @ApiModelProperty(value = "The automatically generated unitId", example = "FLSOW424")
  private String unitId = RandomStringUtils.random(UNIT_ID_LENGTH, UNIT_ID_USE_LETTERS, UNIT_ID_USE_NUMBERS).toUpperCase();

  @ApiModelProperty(value = "The userId of the application User", example = "ABCDEFGK")
  private String colonistId;

  @ApiModelProperty(value = "The desired title of the unit", example = "Blue Moon")
  private String title;

  @ApiModelProperty(value = "The image url of the unit", example = "/photos/12345/main.png", allowEmptyValue = true)
  private String imageUrl;

  @ApiModelProperty(value = "The region of the unit", example = "Arcadia", allowEmptyValue = true)
  private String region;

  @ApiModelProperty(value = "A short description of  the unit", example = "A description of the unit Red Moon", allowEmptyValue = true)
  private String description;

  @ApiModelProperty(value = "The cancellation policy of the unit", example = "Non refundable", allowEmptyValue = true)
  private String cancellationPolicy;

  @ApiModelProperty(value = "The price of the unit", example = "1500", allowEmptyValue = true)
  private Integer price;

  //configuration to ignore reviewId on deserialization but include it on serialization
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private Float score;

  //configuration to ignore reviewId on deserialization but include it on serialization
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private Integer totalReviews;
}

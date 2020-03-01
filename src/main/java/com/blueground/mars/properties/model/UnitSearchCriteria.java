package com.blueground.mars.properties.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.AssertTrue;

@Getter
@Setter
@ApiModel(description = "Object used to search with combined title & region criteria or unitId")
public class UnitSearchCriteria {


  @ApiModelProperty(value = "The optional unitId", example = "FJG8567A", allowEmptyValue = true)
  String unitId;

  @ApiModelProperty(value = "The optional search parameter: Title", example = "Yellow Moon", allowEmptyValue = true)
  String title;

  @ApiModelProperty(value = "The optional search parameter: region", example = "Alba Mons", allowEmptyValue = true)
  String region;

  //Logic to enforce search with either title or region
  @AssertTrue(message = "Title or region is required for a search operation")
  private boolean getTitleOrRegionIsRequired() {
    return title != null || region != null;
  }


}

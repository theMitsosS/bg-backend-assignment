package com.blueground.mars.properties.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import static com.blueground.mars.properties.constants.ApplicationUsersConstants.COLONIST_ID_LENGTH;
import static com.blueground.mars.properties.constants.ApplicationUsersConstants.COLONIST_ID_USE_LETTERS;
import static com.blueground.mars.properties.constants.ApplicationUsersConstants.COLONIST_ID_USE_NUMBERS;

@Getter
@Setter
@Entity(name = "users")
@ApiModel(description = "The \"ApplicationUser\" object of the application")
public class ApplicationUser {

  @Id
  //configuration to ignore reviewId on deserialization but include it on serialization
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  @NotNull(message = "Email is mandatory")
  @ApiModelProperty(value = "The automatically generated user ID", example = "ABCDEFGK", allowEmptyValue = true)
  private String colonistId = RandomStringUtils.random(COLONIST_ID_LENGTH, COLONIST_ID_USE_LETTERS, COLONIST_ID_USE_NUMBERS).toUpperCase();

  @Column(unique = true)
  @ApiModelProperty(value = "The email of the user", example = "jim.1924@hotmail.com")
  private String username;

  //configuration to ignore reviewId on deserialization but include it on serialization
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  @NotNull(message = "Password is mandatory")
  @ApiModelProperty(value = "The password of the user", example = "abcd1234")
  private String password;


}

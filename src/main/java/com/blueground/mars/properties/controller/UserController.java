package com.blueground.mars.properties.controller;

import com.blueground.mars.properties.model.ApplicationUser;
import com.blueground.mars.properties.repository.UserRepository;
import com.blueground.mars.properties.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
@Api(value = "Unit Management API")
public class UserController {


  @Autowired
  UserService userService;

  @Autowired
  UserRepository userRepository;


  @GetMapping("/{email}")
  @ApiOperation(value = "Searches for a user by e-mail", response = ApplicationUser.class)
  public ResponseEntity<ApplicationUser> getUserByEmail(
          @PathVariable(value = "email") final String username) {
    ApplicationUser requestedUser = userService.getUserByUsername(username);
    return ResponseEntity.ok(requestedUser);
  }

  @GetMapping
  @ApiOperation(value = "Gets all users stored in the database", response = ApplicationUser.class, responseContainer = "List")
  public List<ApplicationUser> getAllUsers() {
    return userService.getAllUsers();
  }

  @PostMapping("/sign-up")
  @ApiOperation(value = "Signs up a new user", notes = "This endpoint will return a JWT authentication token that must be added to all other request, as part of the requirements", response = ApplicationUser.class)
  public ResponseEntity<ApplicationUser> signUp(@Valid @RequestBody ApplicationUser applicationUser) {
    ApplicationUser newUser = userService.createUser(applicationUser);
    return new ResponseEntity<>(newUser, HttpStatus.CREATED);
  }
}

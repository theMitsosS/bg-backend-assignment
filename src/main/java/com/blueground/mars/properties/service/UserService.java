package com.blueground.mars.properties.service;

import com.blueground.mars.properties.model.ApplicationUser;

import java.util.List;

public interface UserService {

  ApplicationUser getUserByUsername(String email);

  ApplicationUser createUser(ApplicationUser applicationUser);

  List<ApplicationUser> getAllUsers();
}

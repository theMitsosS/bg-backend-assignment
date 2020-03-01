package com.blueground.mars.properties.service;

import com.blueground.mars.properties.error.EntityNotFoundException;
import com.blueground.mars.properties.error.UserAlreadyExistsException;
import com.blueground.mars.properties.model.ApplicationUser;
import com.blueground.mars.properties.repository.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  UserRepository userRepository;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;


  @Override
  public ApplicationUser getUserByUsername(String email) {
    ApplicationUser requestedUser = userRepository.findByUsername(email);
    if (isNull(requestedUser)) {
      throw new EntityNotFoundException(ApplicationUser.class, "email", email);
    }
    return requestedUser;
  }

  @Override
  public ApplicationUser createUser(ApplicationUser applicationUser) {
    applicationUser.setPassword(bCryptPasswordEncoder.encode(applicationUser.getPassword()));
    if (nonNull(userRepository.findByUsername(applicationUser.getUsername()))) {
      throw new UserAlreadyExistsException(ApplicationUser.class, "email", applicationUser.getUsername());
    }
    while (userRepository.existsById(applicationUser.getColonistId())) {
      applicationUser.setColonistId(RandomStringUtils.random(7, true, false).toUpperCase());
    }
    return userRepository.save(applicationUser);
  }

  @Override
  public List<ApplicationUser> getAllUsers() {
    return userRepository.findAll();
  }
}



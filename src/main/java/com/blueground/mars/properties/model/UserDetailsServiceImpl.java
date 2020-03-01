package com.blueground.mars.properties.model;

import com.blueground.mars.properties.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.util.Collections.emptyList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    ApplicationUser applicationUser = userRepository.findByUsername(username);
    if (applicationUser == null) {
      throw new UsernameNotFoundException(username);


    }
    return new User(applicationUser.getUsername(), applicationUser.getPassword(), emptyList());
  }
}
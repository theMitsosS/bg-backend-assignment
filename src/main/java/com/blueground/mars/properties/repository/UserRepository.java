package com.blueground.mars.properties.repository;

import com.blueground.mars.properties.model.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<ApplicationUser, String> {

  ApplicationUser findByUsername(String username);

}

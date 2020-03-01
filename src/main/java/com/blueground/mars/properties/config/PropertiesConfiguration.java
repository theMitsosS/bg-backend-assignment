package com.blueground.mars.properties.config;

import com.blueground.mars.properties.util.UnitScoreManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class PropertiesConfiguration {


  @Bean
  public UnitScoreManager reviewManagerService() {
    return new UnitScoreManager();
  }

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }
}

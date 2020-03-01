package com.blueground.mars.properties.service;

import com.blueground.mars.properties.error.EntityNotFoundException;
import com.blueground.mars.properties.error.UserAlreadyExistsException;
import com.blueground.mars.properties.model.ApplicationUser;
import com.blueground.mars.properties.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:spring/applicationContext.xml"})
public class UserServiceImplTest {

  @Mock
  UserRepository userRepository;

  @Mock
  BCryptPasswordEncoder bCryptPasswordEncoder;

  @InjectMocks
  UserServiceImpl userService;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void getUserByUsername() {
    //GIVEN
    String input = "email";
    ApplicationUser expectedUser = new ApplicationUser();
    when(userRepository.findByUsername(input)).thenReturn(expectedUser);
    //WHEN
    ApplicationUser actualUser = userService.getUserByUsername(input);
    //THEN
    assertEquals(expectedUser, actualUser);
  }

  @Test(expected = EntityNotFoundException.class)
  public void getUserByUsername_containsNotExistingEmail_entityNotFoundException() {
    when(userRepository.findByUsername("input")).thenReturn(null);
    userService.getUserByUsername("input");
  }

  @Test(expected = UserAlreadyExistsException.class)
  public void createUser_UserNameAlreadyExist_UserAleadyExistsException() {
    //GIVEN
    ApplicationUser input = new ApplicationUser();
    input.setColonistId("AGDERJFS");
    input.setPassword("password");
    input.setUsername("jim.1924@hotmail.com");
    when(userRepository.findByUsername(input.getUsername())).thenReturn(new ApplicationUser());
    //WHEN
    userService.createUser(input);

  }

  @Test()
  public void createUser_UserIdExists_keepGenerating() {
    //GIVEN
    ArgumentCaptor<ApplicationUser> argument = ArgumentCaptor.forClass(ApplicationUser.class);
    ApplicationUser input = new ApplicationUser();
    input.setColonistId("AGDERJFS");
    input.setPassword("password");
    input.setUsername("jim.1924@hotmail.com");
    when(userRepository.findByUsername(input.getUsername())).thenReturn(null);
    when(bCryptPasswordEncoder.encode(any())).thenReturn("password");
    when(userRepository.existsById(input.getColonistId())).thenReturn(true);
    //WHEN
    userService.createUser(input);
    //THEN
    verify(userRepository).save(argument.capture());
    assertNotEquals("AGDERJFS", argument.getValue().getColonistId());


  }


  @Test
  public void getAllUsers() {
    userService.getAllUsers();
    verify(userRepository).findAll();
  }
}
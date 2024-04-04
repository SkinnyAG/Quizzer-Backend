package edu.ntnu.fullstack.prosjekt.quizzer.controllers;

import edu.ntnu.fullstack.prosjekt.quizzer.domain.dto.LoginDTO;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.dto.UserDto;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.UserEntity;
import edu.ntnu.fullstack.prosjekt.quizzer.mappers.Mapper;
import edu.ntnu.fullstack.prosjekt.quizzer.services.UserService;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest Controller used for managing requests relating to user database operations.
 * Base endpoint is /api/users/
 */
@CrossOrigin(origins = "http://localhost:5174")
@RequestMapping("/api/users")
@RestController
public class UserController {
  /**
   * Used for Dependency Injection.
   */
  private UserService userService;

  /**
   * Used for Dependency Injection.
   */
  private Mapper<UserEntity, UserDto> userMapper;

  /**
   * Used for Dependency Injection.
   */
  private PasswordEncoder passwordEncoder;

  /**
   * Used for Dependency Injection.
   *
   * @param userService The injected UserService object.
   * @param userMapper The injected UserMapper object.
   * @param passwordEncoder The injected PasswordEncoder object for comparing passwords.
   */
  public UserController(UserService userService, Mapper<UserEntity, UserDto> userMapper,
                        PasswordEncoder passwordEncoder) {
    this.userService = userService;
    this.userMapper = userMapper;
    this.passwordEncoder = passwordEncoder;
  }

  /**
   * Endpoint for registering a new user.
   *
   * @param user The user attempting registration.
   * @return A response with a status code and message. Fails if user already exists.
   */
  @PostMapping(path = "/register")
  public ResponseEntity<?> createUser(@RequestBody UserDto user) {
    try {
      UserEntity userEntity = userMapper.mapFrom(user);
      //Check if user already exists
      if (userService.userExists(userEntity)) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User already exists!");
      }
      UserEntity savedUserEntity = userService.createUser(userEntity);
      UserDto savedUserDto = userMapper.mapTo(savedUserEntity);
      return new ResponseEntity<>(savedUserDto, HttpStatus.CREATED);

    } catch (Exception e) {
      return ResponseEntity.badRequest().body("An error occurred");
    }
  }

  /**
   * Endpoint for signing in with a user.
   *
   * @param loginUser The sign in information of the user.
   * @return A response with a status code and message. Fails if credentials are incorrect.
   */
  @PostMapping(path = "/login")
  public ResponseEntity<?> loginUser(@RequestBody LoginDTO loginUser) {
    try {
      Optional<UserEntity> userEntity = userService.findByUsername(loginUser.getUsername());
      if (passwordEncoder.matches(loginUser.getPassword(), userEntity.get().getPassword())) {
        return ResponseEntity.ok("User authenticated successfully");
      } else {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
      }
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("An error occurred");
    }
  }
}

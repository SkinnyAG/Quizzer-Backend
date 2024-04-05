package edu.ntnu.fullstack.prosjekt.quizzer.controllers;

import edu.ntnu.fullstack.prosjekt.quizzer.domain.dto.LoginDto;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.dto.UserDto;
import edu.ntnu.fullstack.prosjekt.quizzer.services.UserService;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * Rest Controller used for managing requests relating to user database operations.
 * Base endpoint is /api/users/
 */
@Log
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/users")
@RestController
public class UserController {
  /**
   * Used for Dependency Injection.
   */
  private UserService userService;

  /**
   * Used for Dependency Injection.
   *
   * @param userService The injected UserService object.
   */
  public UserController(UserService userService) {
    this.userService = userService;
  }

  /**
   * Endpoint for registering a new user.
   *
   * @param user The user attempting registration.
   * @return A response with a status code and message. Fails if user already exists.
   */
  @PostMapping(path = "/register")
  public ResponseEntity<UserDto> createUser(@RequestBody UserDto user) {
    log.info("Received register request from user: " + user);
    try {
      UserDto savedUserDto = userService.createUser(user);
      return new ResponseEntity<>(savedUserDto, HttpStatus.CREATED);

    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
    }
  }

  /**
   * Endpoint for signing in with a user.
   *
   * @param loginUser The sign in information of the user.
   * @return A response with a status code and message. Fails if credentials are incorrect.
   */

  @PostMapping(path = "/login")
  public ResponseEntity<String> loginUser(@RequestBody LoginDto loginUser) {
    try {
      if (userService.checkCredentials(loginUser)) {
        return ResponseEntity.ok("User authenticated successfully");
      } else {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
      }
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
    }
  }

  /**
   * Endpoint for getting user information.
   *
   * @param username the chosen user to gather information from.
   * @return A response entity with either a not authorized message, or the user.
   */
  @GetMapping("/{username}")
  public ResponseEntity<UserDto> getUser(@PathVariable("username") String username) {
    String authenticatedUsername = SecurityContextHolder.getContext().getAuthentication().getName();
    if (!username.equals(authenticatedUsername)) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not authorized to view this information");
    }
    UserDto foundUserDto = userService.findDtoByUsername(username);
    return new ResponseEntity<>(foundUserDto, HttpStatus.OK);
  }

  @PostMapping("/{username}/update-email")
  public ResponseEntity<String> updateUserEmail(@PathVariable("username") String username, @RequestBody UserDto userDto) {
    String authenticatedUsername = SecurityContextHolder.getContext().getAuthentication().getName();
    if (!username.equals(authenticatedUsername)) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not authorized to update this information");
    }
    try {
      userService.updateUserEmail(username, userDto.getEmail());
      return ResponseEntity.ok("User email updated successfully");
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while updating the email", e);
    }
  }

}


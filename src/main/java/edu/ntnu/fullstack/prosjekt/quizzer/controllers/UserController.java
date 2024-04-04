package edu.ntnu.fullstack.prosjekt.quizzer.controllers;

import edu.ntnu.fullstack.prosjekt.quizzer.domain.dto.LoginDTO;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.dto.UserDto;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.UserEntity;
import edu.ntnu.fullstack.prosjekt.quizzer.mappers.Mapper;
import edu.ntnu.fullstack.prosjekt.quizzer.services.UserService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5175")
@RequestMapping("/api/users")
@RestController
public class UserController {
    private UserService userService;

    private Mapper<UserEntity, UserDto> userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserController(UserService userService, Mapper<UserEntity, UserDto> userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/register")
    public ResponseEntity<?> createUser(@RequestBody UserDto user) {
        try {
            UserEntity userEntity = userMapper.mapFrom(user);
            //Check if user already exists
            if (userService.userExists(userEntity)){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User already exists!");
            }
            UserEntity savedUserEntity = userService.createUser(userEntity);
            UserDto savedUserDto = userMapper.mapTo(savedUserEntity);
            return ResponseEntity.ok("User registered successfully");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error occurred");
        }
    }

    @PostMapping(path = "/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDTO loginUser) {
        try {
            if (userService.checkCredentials(loginUser)) {
                return ResponseEntity.ok("User authenticated successfully");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error occurred");
        }
    }
}

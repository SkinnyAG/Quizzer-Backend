package edu.ntnu.fullstack.prosjekt.quizzer.controllers;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.dto.LoginDTO;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.dto.UserDto;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.UserEntity;
import edu.ntnu.fullstack.prosjekt.quizzer.mappers.Mapper;
import edu.ntnu.fullstack.prosjekt.quizzer.services.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.Instant;

@RestController
@RequestMapping(value = "/token")
@EnableAutoConfiguration
@CrossOrigin
public class TokenController {

  // keyStr is hardcoded here for testing purpose
  // in a real scenario, it should either be stored in a database or injected from the environment
  public static final String keyStr = "changemeinprod";
  private static final Duration JWT_TOKEN_VALIDITY = Duration.ofMinutes(5);

  private UserService userService;
  private Mapper<UserEntity, UserDto> userMapper;

  @Value("${app.issuer.name}")
  private String projectIssuer;

  public TokenController (UserService userService, Mapper<UserEntity, UserDto> userMapper) {
    this.userService = userService;
    this.userMapper = userMapper;
  }


  @PostMapping(value = "")
  @ResponseStatus(value = HttpStatus.CREATED)
  public String generateToken(final @RequestBody LoginDTO loginRequest) throws Exception {
    // if username and password are valid, issue an access token
    // note that subsequent requests need this token

    if (userService.checkCredentials(loginRequest)) {
      return generateToken(loginRequest.getUsername());
    }

    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Access denied, wrong credentials...");
  }

  public String generateToken(final String userId) {
    final Instant now = Instant.now();
    final Algorithm hmac512 = Algorithm.HMAC512(keyStr);;
    final JWTVerifier verifier = JWT.require(hmac512).build();
    return JWT.create()
        .withSubject(userId)
        .withIssuer(projectIssuer)
        .withIssuedAt(now)
        .withExpiresAt(now.plusMillis(JWT_TOKEN_VALIDITY.toMillis()))
        .sign(hmac512);
  }
}
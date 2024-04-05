package edu.ntnu.fullstack.prosjekt.quizzer.controllers;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.dto.LoginDto;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.dto.TokenDto;
import edu.ntnu.fullstack.prosjekt.quizzer.services.UserService;
import java.time.Duration;
import java.time.Instant;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * Endpoint responsible for providing users with tokens.
 * Base endpoint is /api/token/
 */
@RestController
@RequestMapping(value = "api/token")
@EnableAutoConfiguration
@CrossOrigin
public class TokenController {

  // keyStr is hardcoded here for testing purpose
  // in a real scenario, it should either be stored in a database or injected from the environment
  public static final String keyStr = "changemeinprod";
  private static final Duration JWT_TOKEN_VALIDITY = Duration.ofMinutes(5);

  private UserService userService;


  public TokenController(UserService userService) {
    this.userService = userService;
  }

  /**
   * Generates an authentication token (JWT) for users.
   *
   * @param loginRequest A dto representing the login attempt from a user.
   * @return A JWT used to authenticate the user.
   * @throws Exception Throws an exception if the request is denied.
   */
  @PostMapping()
  @ResponseStatus(value = HttpStatus.CREATED)
  public ResponseEntity<TokenDto> generateToken(final @RequestBody LoginDto loginRequest) throws Exception {
    // if username and password are valid, issue an access token

    if (userService.checkCredentials(loginRequest)) {
      return new ResponseEntity<>(new TokenDto(generateToken(loginRequest.getUsername())), HttpStatus.OK);
    }

    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
            "Access denied, wrong credentials...");
  }

  /**
   * Method used to generate a valid jwt.
   *
   * @param userId Unique identifier (username) for the user that receives the token.
   * @return A String containing the authentication token.
   */
  public String generateToken(final String userId) {
    final Instant now = Instant.now();
    final Algorithm hmac512 = Algorithm.HMAC512(keyStr);
    final JWTVerifier verifier = JWT.require(hmac512).build();
    return JWT.create()
        .withSubject(userId)
        .withIssuer("QuizzerBackend")
        .withIssuedAt(now)
        .withExpiresAt(now.plusMillis(JWT_TOKEN_VALIDITY.toMillis()))
        .sign(hmac512);
  }
}
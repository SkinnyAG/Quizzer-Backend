package edu.ntnu.fullstack.prosjekt.quizzer.controllers;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.dto.LoginDto;
import edu.ntnu.fullstack.prosjekt.quizzer.services.UserService;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
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
  private static final Duration JWT_ACCESS_TOKEN_VALIDITY = Duration.ofMinutes(100);
  private static final Duration JWT_REFRESH_TOKEN_VALIDITY = Duration.ofDays(30);

  private UserService userService;


  /**
   * Endpoint for generating a pair of access and refresh tokens.
   *
   * @param userService the user who is getting the tokens.
   */
  public TokenController(UserService userService) {
    this.userService = userService;
  }
  @PostMapping()
  @ResponseStatus(value = HttpStatus.CREATED)
  public ResponseEntity<Map<String, String>> authenticateUser(final @RequestBody LoginDto loginRequest) throws Exception {
    if (userService.checkCredentials(loginRequest)) {
      String accessToken = generateAccessToken(loginRequest.getUsername());
      String refreshToken = generateRefreshToken(loginRequest.getUsername());
      Map<String, String> tokens = new HashMap<>();
      tokens.put("accessToken", accessToken);
      tokens.put("refreshToken", refreshToken);
      return new ResponseEntity<>(tokens, HttpStatus.OK);
    }
    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Access denied, wrong credentials...");
  }

  /**
   * Endpoint for refreshing an access token with a valid refresh token.
   *
   * @param refreshToken the refresh token for the user.
   * @return the new token.
   */
  @PostMapping("/refresh")
  public ResponseEntity<Map<String, String>> refreshAccessToken(@RequestBody String refreshToken) {
    try {
      Algorithm algorithm = Algorithm.HMAC512(keyStr);
      JWTVerifier verifier = JWT.require(algorithm)
          .withIssuer("QuizzerBackend")
          .build();
      DecodedJWT jwt = verifier.verify(refreshToken);
      String userId = jwt.getSubject();

      // Generate new tokens
      String newAccessToken = generateAccessToken(userId);

      Map<String, String> tokens = new HashMap<>();
      tokens.put("accessToken", newAccessToken);

      return ResponseEntity.ok(tokens);
    } catch (JWTVerificationException exception){
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid refresh token");
    }
  }

  /**
   * Generates access token
   *
   * @param userId the user who wants to generate a token.
   * @return the token.
   */
  public String generateAccessToken(final String userId) {
    final Instant now = Instant.now();
    final Algorithm hmac512 = Algorithm.HMAC512(keyStr);
    return JWT.create()
        .withSubject(userId)
        .withIssuer("QuizzerBackend")
        .withIssuedAt(now)
        .withExpiresAt(now.plusMillis(JWT_ACCESS_TOKEN_VALIDITY.toMillis()))
        .sign(hmac512);
  }

  /**
   * Generates a refresh token.
   *
   * @param userId the user who wants to generate a token.
   * @return the token.
   */
  public String generateRefreshToken(final String userId) {
    final Instant now = Instant.now();
    final Algorithm hmac512 = Algorithm.HMAC512(keyStr);
    return JWT.create()
        .withSubject(userId)
        .withIssuer("QuizzerBackend")
        .withIssuedAt(now)
        .withExpiresAt(now.plusMillis(JWT_REFRESH_TOKEN_VALIDITY.toMillis()))
        .sign(hmac512);
  }
}
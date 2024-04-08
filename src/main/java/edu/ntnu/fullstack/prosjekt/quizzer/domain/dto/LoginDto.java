package edu.ntnu.fullstack.prosjekt.quizzer.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A loginDto representing a login attempt containing username and password.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {

  /**
   * The username field represents the username.
   */
  private String username;

    /**
     * The password field represents the password.
     */
  private String password;
}

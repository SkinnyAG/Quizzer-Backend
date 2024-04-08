package edu.ntnu.fullstack.prosjekt.quizzer.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The UserDto class is a mirror of the userEntity class, with the intention of creating
 * a separation between user input/output and database objects.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

  /**
   * The username field is a unique identifier for a user, and its primary key in the database.
   */
  private String username;

  /**
   * The fullName field represents the full name of the user.
   */
  private String fullName;

  /**
   * The email field represents the email of the user.
   */
  private String email;

  /**
   * The password field represents the salted and hashed password of the user.
   */
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String password;
}

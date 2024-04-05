package edu.ntnu.fullstack.prosjekt.quizzer.services;

import edu.ntnu.fullstack.prosjekt.quizzer.domain.dto.LoginDto;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.dto.UserDto;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.UserEntity;

/**
 * Interface providing services between the user database table and user requests.
 */
public interface UserService {
  /**
   * Service for adding users to the database.
   *
   * @param userDto The UserDto object to be added.
   * @return The added UserDto object.
   */
  UserDto createUser(UserDto userDto);

  /**
   * Service for checking if a user exists in the database.
   *
   * @param userDto The UserDto object to check for.
   * @return A true or false value for the user existing.
   */
  Boolean userExists(UserDto userDto);

  /**
   * Service for finding a user by its unique username.
   *
   * @param username Username of the user to locate.
   * @return A value containing the UserEntity object.
   */
  UserDto findDtoByUsername(String username);

  UserEntity findEntityByUsername(String username);

  Boolean checkCredentials(LoginDto userToBeChecked);

  /**
   * Service for updating a users email.
   *
   * @param username The chosen user.
   * @param newEmail The new mail for the chosen user.
   */
  void updateUserEmail(String username, String newEmail);

  /**
   * Service for updating a users full name.
   *
   * @param username The chosen user.
   * @param newFullName The new full name for the chosen user.
   */
  void updateUserFullName(String username, String newFullName);

  /**
   * Service for updating a users' password.
   *
   * @param username The chosen user.
   * @param newPassword The new full name for the chosen user.
   */
  void updateUserPassword(String username, String newPassword);
}

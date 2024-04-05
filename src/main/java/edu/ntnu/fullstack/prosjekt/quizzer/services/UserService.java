package edu.ntnu.fullstack.prosjekt.quizzer.services;

import edu.ntnu.fullstack.prosjekt.quizzer.domain.dto.LoginDTO;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.UserEntity;

/**
 * Interface providing services between the user database table and user requests.
 */
public interface UserService {
  /**
   * Service for adding users to the database.
   *
   * @param userEntity The UserEntity object to be added.
   * @return The added UserEntity object.
   */
  UserEntity createUser(UserEntity userEntity);

  /**
   * Service for checking if a user exists in the database.
   *
   * @param userEntity The UserEntity object to check for.
   * @return A true or false value for the user existing.
   */
  Boolean userExists(UserEntity userEntity);

  /**
   * Service for finding a user by its unique username.
   *
   * @param username Username of the user to locate.
   * @return A value containing the UserEntity object.
   */
  UserEntity findByUsername(String username);

    Boolean checkCredentials (LoginDTO userToBeChecked);
}

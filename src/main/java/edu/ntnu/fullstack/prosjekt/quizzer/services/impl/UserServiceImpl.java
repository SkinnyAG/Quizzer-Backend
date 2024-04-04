package edu.ntnu.fullstack.prosjekt.quizzer.services.impl;

import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.UserEntity;
import edu.ntnu.fullstack.prosjekt.quizzer.repositories.UserRepository;
import edu.ntnu.fullstack.prosjekt.quizzer.services.UserService;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * A class implementing the methods specified in its interface.
 * Provides services between user requests and database operations.
 */
@Service
public class UserServiceImpl implements UserService {

  /**
   * Used for Dependency Injection.
   */
  private UserRepository userRepository;

  /**
   * Used for Dependency Injection.
   */
  private PasswordEncoder passwordEncoder;

  /**
   * Used for Dependency Injection.
   *
   * @param userRepository The injected UserRepository object.
   * @param passwordEncoder The injected PasswordEncoder object,
   *                        used for salting and hashing passwords.
   */
  public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  /**
   * Creates a user and hashes its password before storing it in the database.
   *
   * @param userEntity The UserEntity object to be added.
   * @return The created UserEntity object.
   */
  @Override
  public UserEntity createUser(UserEntity userEntity) {
    String hashedPassword = passwordEncoder.encode(userEntity.getPassword());
    userEntity.setPassword(hashedPassword);
    return userRepository.save(userEntity);
  }

  /**
   * Checks if a user exists in the database.
   *
   * @param userEntity The UserEntity object to check for.
   * @return A true of false value for the user existing.
   */
  @Override
  public Boolean userExists(UserEntity userEntity) {
    return userRepository.existsById(userEntity.getUsername());
  }

  /**
   * Finds a user by its unique username.
   *
   * @param username Username of the user to locate.
   * @return An optional value containing the UserEntity object.
   */
  @Override
  public UserEntity findByUsername(String username) {
    if (userRepository.findById(username).isPresent()) {
      return userRepository.findById(username).get();
    }
    return null;
  }
}

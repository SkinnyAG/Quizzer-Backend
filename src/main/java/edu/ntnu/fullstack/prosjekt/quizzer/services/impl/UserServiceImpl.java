package edu.ntnu.fullstack.prosjekt.quizzer.services.impl;

import edu.ntnu.fullstack.prosjekt.quizzer.domain.dto.LoginDto;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.dto.UserDto;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.UserEntity;
import edu.ntnu.fullstack.prosjekt.quizzer.mappers.Mapper;
import edu.ntnu.fullstack.prosjekt.quizzer.repositories.UserRepository;
import edu.ntnu.fullstack.prosjekt.quizzer.services.UserService;
import lombok.extern.java.Log;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


/**
 * A class implementing the methods specified in its interface.
 * Provides services between user requests and database operations.
 */
@Log
@Service
public class UserServiceImpl implements UserService {

  /**
   * Used for Dependency Injection.
   */
  private UserRepository userRepository;

  /**
   * Used for Dependency Injection.
   */
  private Mapper<UserEntity, UserDto> userMapper;

  /**
   * Used for Dependency Injection.
   */
  private PasswordEncoder passwordEncoder;

  /**
   * Used for Dependency Injection.
   *
   * @param userRepository  The injected UserRepository object.
   * @param passwordEncoder The injected PasswordEncoder object,
   *                        used for salting and hashing passwords.
   */
  public UserServiceImpl(UserRepository userRepository,
                         Mapper<UserEntity, UserDto> userMapper, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.userMapper = userMapper;
    this.passwordEncoder = passwordEncoder;
  }


  /**
   * Method for checking a loginDTOs credentials against a user.
   *
   * @param userToBeChecked the user to be checked.
   * @return true if hashed + salted password match, false if not.
   */
  @Override
  public Boolean checkCredentials(LoginDto userToBeChecked) {
    //TODO: fikse litt exception handling
    UserEntity userEntity = findEntityByUsername(userToBeChecked.getUsername());
    return passwordEncoder.matches(userToBeChecked.getPassword(),
            userEntity.getPassword());
  }

  /**
   * Creates a user and hashes its password before storing it in the database.
   *
   * @param userDto The UserDto object to be added.
   * @return The created UserDto object.
   */
  @Override
  public UserDto createUser(UserDto userDto) {
    if (userExists(userDto)) {
      UserEntity userEntity = userMapper.mapFrom(userDto);
      String hashedPassword = passwordEncoder.encode(userEntity.getPassword());
      userEntity.setPassword(hashedPassword);
      UserEntity savedUserEntity = userRepository.save(userEntity);
      return userMapper.mapTo(savedUserEntity);
    }
    throw new IllegalArgumentException("User already exists");
  }

  /**
   * Checks if a user exists in the database.
   *
   * @param userDto The UserDto object to check for.
   * @return A true of false value for the user existing.
   */
  @Override
  public Boolean userExists(UserDto userDto) {
    UserEntity userEntity = userMapper.mapFrom(userDto);
    return userRepository.existsById(userEntity.getUsername());
  }

  /**
   * Finds a user by its unique username.
   *
   * @param username Username of the user to locate.
   * @return A Dto representing the found user.
   */
  @Override
  public UserDto findDtoByUsername(String username) {
    if (userRepository.findById(username).isPresent()) {
      UserEntity foundUserEntity = userRepository.findById(username).get();
      return userMapper.mapTo(foundUserEntity);
    }
    return null;
  }

  @Override
  public UserEntity findEntityByUsername(String username) {
    if (userRepository.findById(username).isPresent()) {
      return userRepository.findById(username).get();
    }
    return null;
  }
}

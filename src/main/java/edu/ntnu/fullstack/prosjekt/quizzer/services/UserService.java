package edu.ntnu.fullstack.prosjekt.quizzer.services;

import edu.ntnu.fullstack.prosjekt.quizzer.domain.dto.LoginDTO;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.UserEntity;
import java.util.Optional;

public interface UserService {
    UserEntity createUser(UserEntity userEntity);

    Boolean userExists(UserEntity userEntity);

    Optional<UserEntity> findByUsername(String username);

    Boolean checkCredentials (LoginDTO userToBeChecked);
}

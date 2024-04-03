package edu.ntnu.fullstack.prosjekt.quizzer.services;

import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.UserEntity;

public interface UserService {
    UserEntity createUser(UserEntity userEntity);

    Boolean userExists(UserEntity userEntity);

}

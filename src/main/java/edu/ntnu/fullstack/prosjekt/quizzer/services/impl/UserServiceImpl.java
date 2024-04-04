package edu.ntnu.fullstack.prosjekt.quizzer.services.impl;

import edu.ntnu.fullstack.prosjekt.quizzer.domain.dto.LoginDTO;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.UserEntity;
import edu.ntnu.fullstack.prosjekt.quizzer.repositories.UserRepository;
import edu.ntnu.fullstack.prosjekt.quizzer.services.UserService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserEntity createUser(UserEntity userEntity) {
        String hashedPassword = passwordEncoder.encode(userEntity.getPassword());
        userEntity.setPassword(hashedPassword);
        return userRepository.save(userEntity);
    }

    @Override
    public Boolean userExists(UserEntity userEntity) {
        return userRepository.existsById(userEntity.getUsername());
    }

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        return userRepository.findById(username);
    }

    @Override
    public Boolean checkCredentials(LoginDTO userToBeChecked) {
        Optional<UserEntity> userEntity = findByUsername(userToBeChecked.getUsername());
        return passwordEncoder.matches(userToBeChecked.getPassword(),
            userEntity.get().getPassword());
    }
}

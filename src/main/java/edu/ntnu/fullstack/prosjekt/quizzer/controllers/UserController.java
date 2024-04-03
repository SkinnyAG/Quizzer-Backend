package edu.ntnu.fullstack.prosjekt.quizzer.controllers;

import edu.ntnu.fullstack.prosjekt.quizzer.domain.dto.UserDto;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.UserEntity;
import edu.ntnu.fullstack.prosjekt.quizzer.mappers.Mapper;
import edu.ntnu.fullstack.prosjekt.quizzer.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/users")
@RestController
public class UserController {
    private UserService userService;

    private Mapper<UserEntity, UserDto> userMapper;

    public UserController(UserService userService, Mapper<UserEntity, UserDto> userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/register")
    public UserDto createUser(@RequestBody UserDto user) {
        UserEntity userEntity = userMapper.mapFrom(user);
        UserEntity savedUserEntity = userService.createUser(userEntity);
        UserDto savedUserDto = userMapper.mapTo(savedUserEntity);
        return savedUserDto;
    }
}

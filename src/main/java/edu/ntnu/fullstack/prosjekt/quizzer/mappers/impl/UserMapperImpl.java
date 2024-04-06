package edu.ntnu.fullstack.prosjekt.quizzer.mappers.impl;

import edu.ntnu.fullstack.prosjekt.quizzer.domain.dto.UserDto;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.UserEntity;
import edu.ntnu.fullstack.prosjekt.quizzer.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * A class implementing the Mapper interface, to map between UserDTOs and UserEntities.
 */
@Component
public class UserMapperImpl implements Mapper<UserEntity, UserDto> {

  /**
   * Used for Dependency Injection.
   */
  private ModelMapper modelMapper;

  /**
   * Used for Dependency Injection.
   *
   * @param modelMapper The injected ModelMapper Object.
   */
  public UserMapperImpl(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }

  /**
   * Method used for mapping from a UserEntity to a UserDTO.
   *
   * @param userEntity UserEntity object that should be mapped.
   * @return Mapped UserDTO object.
   */
  @Override
  public UserDto mapTo(UserEntity userEntity) {
    return modelMapper.map(userEntity, UserDto.class);
  }

  /**
   * Method used for mapping from a UserDTO to a UserEntity.
   *
   * @param userDto UserDto object that should be mapped.
   * @return Mapped UserEntity object.
   */
  @Override
  public UserEntity mapFrom(UserDto userDto) {
    return modelMapper.map(userDto, UserEntity.class);
  }
}

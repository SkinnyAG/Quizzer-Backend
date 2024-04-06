package edu.ntnu.fullstack.prosjekt.quizzer.mappers.impl;

import edu.ntnu.fullstack.prosjekt.quizzer.domain.dto.QuizDetailsDto;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.dto.UserDto;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.QuizEntity;
import edu.ntnu.fullstack.prosjekt.quizzer.mappers.Mapper;
import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * A class implementing the Mapper interface, to map between QuizDTOs and QuizEntities.
 */
@Log
@Component
public class QuizMapperImpl implements Mapper<QuizEntity, QuizDetailsDto> {

  /**
   * Used for Dependency Injection.
   */
  private ModelMapper modelMapper;

  /**
   * Used for Dependency Injection.
   *
   * @param modelMapper The injected ModelMapper Object.
   */
  public QuizMapperImpl(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }

  /**
   * Method used for mapping from a QuizEntity to a QuizDTO.
   *
   * @param quizEntity QuizEntity object that should be mapped.
   * @return Mapped QuizDTO object.
   */
  @Override
  public QuizDetailsDto mapTo(QuizEntity quizEntity) {
    QuizDetailsDto quizDetailsDto = modelMapper.map(quizEntity, QuizDetailsDto.class);
    log.info("Quiz mapped: " + quizDetailsDto);
    if (quizEntity.getOwner() != null) {
      quizDetailsDto.setOwner(modelMapper.map(quizEntity.getOwner(), UserDto.class));
    }
    return quizDetailsDto;
  }

  /**
   * Method used for mapping from a QuizDTO to a QuizEntity.
   *
   * @param quizDetailsDto QuizDetailsDto object that should be mapped.
   * @return Mapped QuizEntity object.
   */
  @Override
  public QuizEntity mapFrom(QuizDetailsDto quizDetailsDto) {
    return modelMapper.map(quizDetailsDto, QuizEntity.class);
  }
}

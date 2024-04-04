package edu.ntnu.fullstack.prosjekt.quizzer.mappers.impl;

import edu.ntnu.fullstack.prosjekt.quizzer.domain.dto.QuizDto;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.QuizEntity;
import edu.ntnu.fullstack.prosjekt.quizzer.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * A class implementing the Mapper interface, to map between QuizDTOs and QuizEntities.
 */
@Component
public class QuizMapperImpl implements Mapper<QuizEntity, QuizDto> {

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
  public QuizDto mapTo(QuizEntity quizEntity) {
    return modelMapper.map(quizEntity, QuizDto.class);
  }

  /**
   * Method used for mapping from a QuizDTO to a QuizEntity.
   *
   * @param quizDto QuizDto object that should be mapped.
   * @return Mapped QuizEntity object.
   */
  @Override
  public QuizEntity mapFrom(QuizDto quizDto) {
    return modelMapper.map(quizDto, QuizEntity.class);
  }
}

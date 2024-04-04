package edu.ntnu.fullstack.prosjekt.quizzer.mappers.impl;

import edu.ntnu.fullstack.prosjekt.quizzer.domain.dto.QuestionDto;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.QuestionEntity;
import edu.ntnu.fullstack.prosjekt.quizzer.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * A class implementing the Mapper interface, to map between QuestionDTOs and QuestionEntities.
 */
@Component
public class QuestionMapperImpl implements Mapper<QuestionEntity, QuestionDto> {

  /**
   * Used for Dependency Injection.
   */
  private ModelMapper modelMapper;

  /**
   * Used for Dependency Injection.
   *
   * @param modelMapper The injected ModelMapper Object.
   */
  public QuestionMapperImpl(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }

  /**
   * Method used for mapping from a QuestionEntity to a QuestionDTO.
   *
   * @param questionEntity QuestionEntity object that should be mapped.
   * @return Mapped QuestionDTO object.
   */
  @Override
  public QuestionDto mapTo(QuestionEntity questionEntity) {
    return modelMapper.map(questionEntity, QuestionDto.class);
  }

  /**
   * Method used for mapping from a QuestionDTO to a QuestionEntity.
   *
   * @param questionDto QuestionDto object that should be mapped.
   * @return Mapped QuestionEntity object.
   */
  @Override
  public QuestionEntity mapFrom(QuestionDto questionDto) {
    return modelMapper.map(questionDto, QuestionEntity.class);
  }
}

package edu.ntnu.fullstack.prosjekt.quizzer.mappers.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.dto.QuestionAnswersDto;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.dto.QuestionDto;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.QuestionEntity;
import edu.ntnu.fullstack.prosjekt.quizzer.mappers.Mapper;
import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * A class implementing the Mapper interface, to map between QuestionDTOs and QuestionEntities.
 */
@Log
@Component
public class QuestionMapperImpl implements Mapper<QuestionEntity, QuestionDto> {

  /**
   * Used for Dependency Injection.
   */
  private ModelMapper modelMapper;

  /**
   * Used for Dependency Injection.
   */
  private ObjectMapper objectMapper;

  /**
   * Used for Dependency Injection.
   *
   * @param modelMapper The injected ModelMapper Object.
   */
  public QuestionMapperImpl(ModelMapper modelMapper, ObjectMapper objectMapper) {
    this.modelMapper = modelMapper;
    this.objectMapper = objectMapper;
  }

  /**
   * Method used for mapping from a QuestionEntity to a QuestionDTO.
   *
   * @param questionEntity QuestionEntity object that should be mapped.
   * @return Mapped QuestionDTO object.
   */
  @Override
  public QuestionDto mapTo(QuestionEntity questionEntity) {
    QuestionDto questionDto = modelMapper.map(questionEntity, QuestionDto.class);
    if (questionEntity.getAlternatives() != null) {
      try {
        questionDto.setAlternatives(objectMapper.readValue(questionEntity.getAlternatives(), List.class));
        log.info("Mapper dto: " + questionDto);
        return questionDto;
      } catch (JsonProcessingException e) {
        throw new RuntimeException(e);
      }
    }
    return questionDto;
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

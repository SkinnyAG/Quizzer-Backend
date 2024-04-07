package edu.ntnu.fullstack.prosjekt.quizzer.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionAnswersDto {

  private String answer;

  //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private Boolean isCorrect;
}

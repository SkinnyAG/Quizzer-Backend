package edu.ntnu.fullstack.prosjekt.quizzer.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionAnswersDto {

  private String answer;

  //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private Boolean isCorrect;
}

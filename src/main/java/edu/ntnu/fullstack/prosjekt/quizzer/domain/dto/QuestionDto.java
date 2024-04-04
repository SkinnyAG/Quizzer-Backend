package edu.ntnu.fullstack.prosjekt.quizzer.domain.dto;

import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.QuizEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDto {
    private Long questionId;

    private String label;

    private Short position;

    private String alternatives;

    private QuizEntity quiz;
}

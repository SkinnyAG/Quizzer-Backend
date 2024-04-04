package edu.ntnu.fullstack.prosjekt.quizzer.domain.dto;

import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.QuestionEntity;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizDto {
    private Long quizId;

    private String title;

    private String description;

    private UserEntity owner;

    private List<QuestionEntity> questions;
}

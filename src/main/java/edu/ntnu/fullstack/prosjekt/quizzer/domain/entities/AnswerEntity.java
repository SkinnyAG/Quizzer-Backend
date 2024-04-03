package edu.ntnu.fullstack.prosjekt.quizzer.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerEntity {
    @Id
    private Long answerId;

    private String answer;

    private Boolean isCorrect;

    private Short index;
}

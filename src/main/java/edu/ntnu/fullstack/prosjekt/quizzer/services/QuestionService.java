package edu.ntnu.fullstack.prosjekt.quizzer.services;

import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.QuestionEntity;

public interface QuestionService {
    QuestionEntity addQuestion(QuestionEntity questionEntity);
}

package edu.ntnu.fullstack.prosjekt.quizzer.services;

import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.QuestionEntity;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.models.AnswerModel;

public interface QuestionService {
    QuestionEntity createQuestion(QuestionEntity questionEntity);


}

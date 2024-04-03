package edu.ntnu.fullstack.prosjekt.quizzer.services;

import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.QuizEntity;

public interface QuizService {
    QuizEntity createQuiz(QuizEntity quizEntity);
}

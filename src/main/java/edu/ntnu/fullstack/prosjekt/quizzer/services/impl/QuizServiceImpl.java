package edu.ntnu.fullstack.prosjekt.quizzer.services.impl;

import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.QuizEntity;
import edu.ntnu.fullstack.prosjekt.quizzer.repositories.QuizRepository;
import edu.ntnu.fullstack.prosjekt.quizzer.services.QuizService;
import org.springframework.stereotype.Service;

@Service
public class QuizServiceImpl implements QuizService {
    private QuizRepository quizRepository;

    public QuizServiceImpl(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }
    @Override
    public QuizEntity createQuiz(QuizEntity quizEntity) {
        return quizRepository.save(quizEntity);
    }
}

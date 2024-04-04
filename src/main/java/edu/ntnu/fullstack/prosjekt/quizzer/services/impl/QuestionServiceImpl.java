package edu.ntnu.fullstack.prosjekt.quizzer.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.QuestionEntity;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.models.AnswerModel;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.models.QuestionModel;
import edu.ntnu.fullstack.prosjekt.quizzer.repositories.QuestionRepository;
import edu.ntnu.fullstack.prosjekt.quizzer.services.QuestionService;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceImpl implements QuestionService {
    private QuestionRepository questionRepository;

    private ObjectMapper objectMapper;

    public QuestionServiceImpl(QuestionRepository questionRepository, ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.questionRepository = questionRepository;
    }

    @Override
    public QuestionEntity createQuestion(QuestionEntity questionEntity) {
        return questionRepository.save(questionEntity);
    }

}

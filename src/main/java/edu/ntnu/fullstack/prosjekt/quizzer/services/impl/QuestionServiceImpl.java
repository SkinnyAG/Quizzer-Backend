package edu.ntnu.fullstack.prosjekt.quizzer.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.QuestionEntity;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.models.AnswerModel;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.models.QuestionModel;
import edu.ntnu.fullstack.prosjekt.quizzer.repositories.QuestionRepository;
import edu.ntnu.fullstack.prosjekt.quizzer.services.QuestionService;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Service
@Log
public class QuestionServiceImpl implements QuestionService {
    private QuestionRepository questionRepository;

    private ObjectMapper objectMapper;

    public QuestionServiceImpl(QuestionRepository questionRepository, ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.questionRepository = questionRepository;
    }

    @Override
    public QuestionEntity createQuestion(QuestionEntity questionEntity) {
        log.info("Question received in Service, attempting to add to database.");
        return questionRepository.save(questionEntity);
    }

}

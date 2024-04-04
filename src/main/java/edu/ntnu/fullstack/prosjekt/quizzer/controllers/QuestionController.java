package edu.ntnu.fullstack.prosjekt.quizzer.controllers;

import edu.ntnu.fullstack.prosjekt.quizzer.domain.dto.QuestionDto;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.dto.QuizDto;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.dto.UserDto;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.QuestionEntity;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.QuizEntity;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.UserEntity;
import edu.ntnu.fullstack.prosjekt.quizzer.mappers.Mapper;
import edu.ntnu.fullstack.prosjekt.quizzer.services.QuestionService;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/questions")
@RestController
@Log
public class QuestionController {
    private QuestionService questionService;

    private Mapper<QuestionEntity, QuestionDto> questionMapper;

    public QuestionController(QuestionService questionService, Mapper<QuestionEntity, QuestionDto> questionMapper) {
        this.questionService = questionService;
        this.questionMapper = questionMapper;
    }

    @PostMapping("/create")
    public ResponseEntity<?> addQuestion(@RequestBody QuestionDto questionDto) {
        log.info("Received request addQuestion for question: " + questionDto);
        try {
            QuestionEntity questionEntity = questionMapper.mapFrom(questionDto);

            if (questionEntity.getLabel() == null || questionEntity.getLabel().isEmpty()) {
                return ResponseEntity.badRequest().body("The question must have a label");
            }
            /*if (questionEntity.getQuiz() == null) {
                return ResponseEntity.badRequest().body("No quiz assigned to question.");
            }*/
            QuestionEntity savedQuestionEntity = questionService.createQuestion(questionEntity);
            return ResponseEntity.ok("Ok");
        } catch (Exception e) {

        }
        return null;
    }
}

package edu.ntnu.fullstack.prosjekt.quizzer.controllers;

import edu.ntnu.fullstack.prosjekt.quizzer.domain.dto.QuestionDto;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.dto.QuizDto;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.QuestionEntity;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.QuizEntity;
import edu.ntnu.fullstack.prosjekt.quizzer.mappers.Mapper;
import edu.ntnu.fullstack.prosjekt.quizzer.services.QuizService;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quizzes")
@Log
public class QuizController {
    private QuizService quizService;

    private Mapper<QuizEntity, QuizDto> quizMapper;

    public QuizController(QuizService quizService, Mapper<QuizEntity, QuizDto> quizMapper) {
        this.quizService = quizService;
        this.quizMapper = quizMapper;
    }

    @PostMapping(path= "/create")
    public ResponseEntity<?> createQuiz(@RequestBody QuizDto quizDto) {
        log.info("Request to createQuiz received with quiz: " + quizDto);
        try {
            QuizEntity quizEntity = quizMapper.mapFrom(quizDto);
            if (quizEntity.getTitle().isEmpty()) {
                return ResponseEntity.badRequest().body("Invalid quiz name");
            }
            if (quizEntity.getOwner().getUsername().isEmpty()) {
                return ResponseEntity.badRequest().body("No user associated with quiz");
            }
            //QuizEntity savedQuizEntity = quizService.createQuiz(quizEntity);
            //QuizDto savedQuizDto = quizMapper.mapTo(savedQuizEntity);
            return ResponseEntity.ok("Quiz created successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error occurred.");
        }
    }
}

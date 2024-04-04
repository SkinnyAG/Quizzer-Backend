package edu.ntnu.fullstack.prosjekt.quizzer.controllers;

import edu.ntnu.fullstack.prosjekt.quizzer.domain.dto.QuestionDto;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.dto.QuizDto;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.QuestionEntity;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.QuizEntity;
import edu.ntnu.fullstack.prosjekt.quizzer.mappers.Mapper;
import edu.ntnu.fullstack.prosjekt.quizzer.services.QuizService;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
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
            log.info("Attempting to map quizDto to quizEntity");
            QuizEntity quizEntity = quizMapper.mapFrom(quizDto);
            log.info("Successfully mapped");
            log.info("Check if necessary fields are set");
            if (quizEntity.getTitle() == null || quizEntity.getTitle().isEmpty()) {
                log.info("Missing or illegal quiz title");
                return ResponseEntity.badRequest().body("Invalid quiz name");
            }
            /*if (quizEntity.getOwner() == null) {
                log.info("Missing or invalid quiz owner");
                return ResponseEntity.badRequest().body("No user associated with quiz");
            }*/
            log.info("Attempting to add quiz to database");
            QuizEntity savedQuizEntity = quizService.createQuiz(quizEntity);
            log.info("Quiz added to database: " + savedQuizEntity);
            QuizDto savedQuizDto = quizMapper.mapTo(savedQuizEntity);
            log.info("Sending successful response to frontend");
            return new ResponseEntity(savedQuizDto, HttpStatus.CREATED);
        } catch (Exception e) {
            log.info("An unforeseen error occurred");
            return ResponseEntity.badRequest().body("An unforeseen error occurred.");
        }
    }
}

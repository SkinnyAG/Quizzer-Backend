package edu.ntnu.fullstack.prosjekt.quizzer.controllers;

import edu.ntnu.fullstack.prosjekt.quizzer.domain.dto.QuestionDto;
import edu.ntnu.fullstack.prosjekt.quizzer.services.QuestionService;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest Controller used for managing requests relating to question database operations.
 * Base endpoint is /api/questions/.
 */
@RequestMapping("/api/questions")
@RestController
@Log
public class QuestionController {
  /**
   * Used for Dependency Injection.
   */
  private QuestionService questionService;

  /**
   * Used for Dependency Injection.
   *
   * @param questionService The Injected QuestionService object.
   */
  public QuestionController(QuestionService questionService) {
    this.questionService = questionService;
  }

  /**
   * Endpoint for creating a question.
   *
   * @param questionDto The question to create.
   * @return A response with a status code and message. Fails if question misses necessary fields.
   */
  @PostMapping()
  public ResponseEntity<?> addQuestion(@RequestBody QuestionDto questionDto) {
    log.info("Received request addQuestion for question: " + questionDto);
    try {

      QuestionDto savedQuestionDto = questionService.createQuestion(questionDto);
      return new ResponseEntity<>(savedQuestionDto, HttpStatus.CREATED);
    } catch (Exception e) {
      System.out.println(e);
      return ResponseEntity.badRequest().body("An unforeseen error occurred");
    }
  }
}

package edu.ntnu.fullstack.prosjekt.quizzer.controllers;

import edu.ntnu.fullstack.prosjekt.quizzer.domain.dto.QuestionDto;
import edu.ntnu.fullstack.prosjekt.quizzer.services.QuestionService;
import edu.ntnu.fullstack.prosjekt.quizzer.services.QuizService;
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
}

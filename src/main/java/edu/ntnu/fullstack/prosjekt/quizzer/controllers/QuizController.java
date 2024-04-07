package edu.ntnu.fullstack.prosjekt.quizzer.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.dto.MessageDto;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.dto.CategoryDto;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.dto.QuestionDto;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.dto.QuizDetailsDto;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.dto.QuizGeneralDto;
import edu.ntnu.fullstack.prosjekt.quizzer.services.QuizService;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


/**
 * Rest Controller for managing requests relating to quiz database operations.
 * Base endpoint is /api/quizzes/.
 */
@RestController
@RequestMapping("/api/quizzes")
@CrossOrigin(origins = "*")
@Log
public class QuizController {
  /**
   * Used for Dependency Injection.
   */
  private QuizService quizService;

  /**
   * Used for Dependency Injection.
   *
   * @param quizService The injected QuizService object.
   */
  public QuizController(QuizService quizService) {
    this.quizService = quizService;
  }

  /**
   * Endpoint for creating a new quiz.
   *
   * @param quizDetailsDto The quiz to be created.
   * @return A response with a status code and message. Fails necessary fields are missing.
   */
  @PostMapping()
  public ResponseEntity<QuizDetailsDto> createQuiz(@RequestBody QuizDetailsDto quizDetailsDto) {
    log.info("Request to createQuiz received with quiz: " + quizDetailsDto);
    try {
      QuizDetailsDto responseDto = quizService.createQuiz(quizDetailsDto);
      return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    } catch (Exception e) {
      log.info("An unforeseen error occurred");
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
    }
  }

  @PatchMapping(path = "/{quizId}")
  public ResponseEntity<?> addQuestionToQuiz(@PathVariable String quizId, @RequestBody QuestionDto questionDto) throws JsonProcessingException {
    log.info("Testiiing");
    log.info("Received: " + questionDto);
    QuestionDto responseDto = quizService.addQuestionToQuiz(quizId, questionDto);
    return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
  }

  @PutMapping
  public ResponseEntity<MessageDto> updateQuiz(@RequestBody QuizDetailsDto updatedQuizDto) {
    log.info("Questions: " + updatedQuizDto.getQuestions());
    quizService.updateQuizEntity(updatedQuizDto);
    return new ResponseEntity<>(new MessageDto("Quiz updated"), HttpStatus.OK);
  }

  /**
   * Endpoint that gets a page of quizzes.
   *
   * @param pageable Pagination parameters such as page size, number and sorting.
   * @return Response with a status code and message.
   */
  @GetMapping()
  public ResponseEntity<Page<QuizGeneralDto>> getPageOfQuizzes(Pageable pageable) {
    log.info("Client requesting quiz page");
    Page<QuizGeneralDto> quizDtoPage = quizService.findPageOfQuizzes(pageable);
    return new ResponseEntity<>(quizDtoPage, HttpStatus.OK);
  }

  @GetMapping(path = "/filter")
  public ResponseEntity<Page<QuizGeneralDto>> getFilteredPageOfQuizzes(@RequestParam String searchQuery, Pageable pageable) {
    Page<QuizGeneralDto> quizzesByCategories = quizService.filterQuizzes(searchQuery, pageable);
    return new ResponseEntity<>(quizzesByCategories, HttpStatus.OK);
  }

  /**
   * Endpoint that gets a specified quiz.
   *
   * @param quizId ID of the quiz
   * @return A quizDto object representing the quiz.
   */
  @GetMapping(path = "/{quizId}")
  public ResponseEntity<QuizDetailsDto> getQuizDetails(@PathVariable String quizId) {
    QuizDetailsDto quizDetailsDto = quizService.findQuizDetails(quizId);
    log.info("Received questions: " + quizDetailsDto);
    return new ResponseEntity<>(quizDetailsDto, HttpStatus.OK);
  }

  @GetMapping(path = "/categories")
  public ResponseEntity<List<CategoryDto>> getCategories() {
    List<CategoryDto> categories = quizService.findAllCategories();
    return new ResponseEntity<>(categories, HttpStatus.OK);
  }
}

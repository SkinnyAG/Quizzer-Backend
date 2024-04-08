package edu.ntnu.fullstack.prosjekt.quizzer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 * Main Application Runner.
 */
@SpringBootApplication
public class QuizzerApplication {

  /**
   * The main method.
   * @param args
   */
  public static void main(String[] args) {
    SpringApplication.run(QuizzerApplication.class, args);
  }

}

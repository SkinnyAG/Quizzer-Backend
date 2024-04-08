package edu.ntnu.fullstack.prosjekt.quizzer.controllerTests;

import edu.ntnu.fullstack.prosjekt.quizzer.domain.dto.*;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.CategoryEntity;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.QuizEntity;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.UserEntity;

import java.util.ArrayList;
import java.util.List;

public class QuizControllerTestDataUtil {
  private QuizControllerTestDataUtil() {
  }

  public static UserEntity createTestUserA() {
    return UserEntity.builder()
            .username("andgjers")
            .fullName("Andreas Gjersøe")
            .email("andgjers@ntnu.no")
            .password("password")
            .build();
  }

  public static UserDto createTestUserDtoA() {
    return UserDto.builder()
            .username("andgjers")
            .fullName("Andreas Gjersøe")
            .email("andgjers@ntnu.no")
            .password("password")
            .build();
  }

  public static UserEntity createTestUserB() {
    return UserEntity.builder()
            .username("Supermario")
            .fullName("Mario Piereluigi Calcone")
            .email("mario@plumber.com")
            .password("peachflavor")
            .build();
  }

  public static QuizEntity createTestQuizA() {
    return QuizEntity.builder()
            .owner(createTestUserA())
            .title("Shark Quiz")
            .description("A quiz about sharks")
            .build();
  }

  public static QuizDetailsDto createTestDtoQuizA() {
    List<UserDto> collaborators = new ArrayList<>();
    return QuizDetailsDto.builder()
            .owner(createTestUserDtoA())
            .quizId(1L)
            .collaborators(collaborators)
            .title("Shark Quiz")
            .description("A quiz about sharks")
            .build();
  }

  public static CategoryEntity createGeographyCategory() {
    return CategoryEntity.builder()
            .categoryName("Geography")
            .build();
  }

  public static CategoryEntity createNatureCategory() {
    return CategoryEntity.builder()
            .categoryName("Nature")
            .build();
  }

  public static CategoryDto createGeographyCategoryDto() {
    return CategoryDto.builder()
            .categoryName("Geography")
            .build();
  }

  public static CategoryDto createNatureCategoryDto() {
    return CategoryDto.builder()
            .categoryName("Nature")
            .build();
  }

  public static List<CategoryDto> createCategoryDtos() {
    List<CategoryDto> categories = new ArrayList<>();
    categories.add(createNatureCategoryDto());
    categories.add(createGeographyCategoryDto());
    return categories;
  }

  public static QuestionAnswersDto createQuestionAnswerDtoA1() {
    return QuestionAnswersDto.builder()
            .answer("120")
            .isCorrect(false)
            .build();
  }

  public static QuestionAnswersDto createQuestionAnswerDtoA2() {
    return QuestionAnswersDto.builder()
            .answer("25")
            .isCorrect(false)
            .build();
  }

  public static QuestionAnswersDto createQuestionAnswerDtoA3() {
    return QuestionAnswersDto.builder()
            .answer("0")
            .isCorrect(true)
            .build();
  }

  public static QuestionAnswersDto createQuestionAnswerDtoB1() {
    return QuestionAnswersDto.builder()
            .answer("Hammerhead Shark")
            .isCorrect(false)
            .build();
  }

  public static QuestionAnswersDto createQuestionAnswerDtoB2() {
    return QuestionAnswersDto.builder()
            .answer("Whale Shark")
            .isCorrect(true)
            .build();
  }

  public static QuestionAnswersDto createQuestionAnswerDtoB3() {
    return QuestionAnswersDto.builder()
            .answer("Great White Shark")
            .isCorrect(true)
            .build();
  }

  public static QuestionAnswersDto createQuestionAnswerDtoC1() {
    return QuestionAnswersDto.builder()
            .answer("300")
            .isCorrect(false)
            .build();
  }

  public static QuestionAnswersDto createQuestionAnswerDtoC2() {
    return QuestionAnswersDto.builder()
            .answer("50")
            .isCorrect(true)
            .build();
  }

  public static QuestionAnswersDto createQuestionAnswerDtoC3() {
    return QuestionAnswersDto.builder()
            .answer("1000")
            .isCorrect(false)
            .build();
  }

  public static List<QuestionAnswersDto> createAlternativesA() {
    List<QuestionAnswersDto> questionAnswersDtoList = new ArrayList<>();
    questionAnswersDtoList.add(createQuestionAnswerDtoA1());
    questionAnswersDtoList.add(createQuestionAnswerDtoA2());
    questionAnswersDtoList.add(createQuestionAnswerDtoA3());
    return questionAnswersDtoList;
  }

  public static List<QuestionAnswersDto> createAlternativesB() {
    List<QuestionAnswersDto> questionAnswersDtoList = new ArrayList<>();
    questionAnswersDtoList.add(createQuestionAnswerDtoB1());
    questionAnswersDtoList.add(createQuestionAnswerDtoB2());
    questionAnswersDtoList.add(createQuestionAnswerDtoB3());
    return questionAnswersDtoList;
  }

  public static List<QuestionAnswersDto> createAlternativesC() {
    List<QuestionAnswersDto> questionAnswersDtoList = new ArrayList<>();
    questionAnswersDtoList.add(createQuestionAnswerDtoC1());
    questionAnswersDtoList.add(createQuestionAnswerDtoC2());
    questionAnswersDtoList.add(createQuestionAnswerDtoC3());
    return questionAnswersDtoList;
  }

  public static QuestionDto createQuestionA() {
    return QuestionDto.builder()
            .questionId(1L)
            .label("How many bones does a shark have?")
            .alternatives(createAlternativesA())
            .build();
  }

  public static QuestionDto createQuestionB() {
    return QuestionDto.builder()
            .questionId(1L)
            .label("Which shark is the biggest")
            .alternatives(createAlternativesB())
            .build();
  }

  public static QuestionDto createQuestionC() {
    return QuestionDto.builder()
            .questionId(1L)
            .label("How many teeth does the Great White Shark have?")
            .alternatives(createAlternativesC())
            .build();
  }

  public static List<QuestionDto> createQuestions() {
    List<QuestionDto> questionDtoList = new ArrayList<>();
    questionDtoList.add(createQuestionA());
    questionDtoList.add(createQuestionB());
    questionDtoList.add(createQuestionC());
    return questionDtoList;
  }
}

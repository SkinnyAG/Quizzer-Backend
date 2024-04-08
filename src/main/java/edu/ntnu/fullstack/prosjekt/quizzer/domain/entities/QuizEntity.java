package edu.ntnu.fullstack.prosjekt.quizzer.domain.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

import lombok.*;

/**
 * QuizEntity is one of the main entities in the application, acting as quizzes owned by users in
 * the database, and containing questions.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "quizzes")
public class QuizEntity {

  /**
   * The quizId field is a unique identifier and primary key for a quiz entry in
   * the database.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quiz_id_seq")
  private Long quizId;

  /**
   * The title field represents the name of the quiz.
   */
  private String title;

  /**
   * The description field should be used for describing the quiz.
   */
  private String description;

  /**
   * The categories field should store a list of categories that the quiz belongs to.
   */
  @ManyToMany
  private Set<CategoryEntity> categories;

  /**
   * Links to an image for the quiz.
   */
  private String imageLink = "";

  /**
   * The questions field should store a list of questions that are part of the quiz.
   */
  @ManyToMany
  private List<UserEntity> collaborators;

  /**
   * The owner field should represent the user who owns the quiz, with many quizzes belonging to
   * one user. This field is a foreign key linking owner and quizzes.
   */
  @ManyToOne
  @JsonBackReference
  @JoinColumn(name = "username")
  private UserEntity owner;


  @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonManagedReference
  private List<QuestionEntity> questions;
}

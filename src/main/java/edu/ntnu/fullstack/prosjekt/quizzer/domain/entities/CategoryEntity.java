package edu.ntnu.fullstack.prosjekt.quizzer.domain.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * CategoryEntity is an entity that represents a category in the database.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "categories")
public class CategoryEntity {
  /**
   * The categoryName field is a unique identifier and primary key for a category entry in the database.
   */
  @Id
  private String categoryName;

  /**
   * The quizzes field should store a list of quizzes that are part of the category.
   */
  @ManyToMany
  private Set<QuizEntity> quizzes;
}

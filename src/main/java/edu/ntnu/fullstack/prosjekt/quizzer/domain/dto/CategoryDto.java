package edu.ntnu.fullstack.prosjekt.quizzer.domain.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.QuizEntity;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * CategoryDto is a data transfer object that represents a category.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDto {
  /**
   * The categoryName field represents the name of the category.
   */
  private String categoryName;

    /**
     * The quizzes field represents the quizzes that has this category.
     */
  @JsonBackReference
  private Set<QuizEntity> quizzes;
}

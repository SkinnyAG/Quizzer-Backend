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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDto {
  private String categoryName;

  @JsonBackReference
  private Set<QuizEntity> quizzes;
}

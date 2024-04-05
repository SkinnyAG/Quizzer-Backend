package edu.ntnu.fullstack.prosjekt.quizzer.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categories")
public class CategoryEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "question_id_seq")
  private Long categoryId;

  @ManyToMany
  private Set<QuizEntity> quizzes;
}

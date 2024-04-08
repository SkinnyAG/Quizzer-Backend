package edu.ntnu.fullstack.prosjekt.quizzer.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizAttemptEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "attempt_id_seq")
  private Long attemptId;

  private Integer score;

  private Integer duration;

  @ManyToOne
  private UserEntity user;

  @ManyToOne
  private QuizEntity quiz;
}

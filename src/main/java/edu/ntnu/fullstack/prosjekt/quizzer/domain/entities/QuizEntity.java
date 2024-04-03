package edu.ntnu.fullstack.prosjekt.quizzer.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "quizzes")
public class QuizEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quiz_id_seq")
    private Long quizId;

    private String title;

    private String description;

    @ManyToOne
    @JoinColumn(name = "username")
    private UserEntity owner;

    @OneToMany (mappedBy = "quizId")
    private List<QuestionEntity> questions;

}

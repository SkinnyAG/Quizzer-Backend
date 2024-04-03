package edu.ntnu.fullstack.prosjekt.quizzer.domain.entities;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.models.QuestionModel;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "questions")
public class QuestionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "question_id_seq")
    private Long questionId;

    private String label;

    private Short position;

    private String alternatives;

    @ManyToOne
    @JoinColumn(name = "quizId")
    private QuizEntity quiz;
}

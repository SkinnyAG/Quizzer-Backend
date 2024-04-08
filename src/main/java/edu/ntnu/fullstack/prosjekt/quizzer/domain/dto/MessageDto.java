package edu.ntnu.fullstack.prosjekt.quizzer.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * MessageDto is a data transfer object that represents a message.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {
    /**
     * The message field represents the message.
     */
    private String message;
}

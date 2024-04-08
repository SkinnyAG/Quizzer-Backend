package edu.ntnu.fullstack.prosjekt.quizzer.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TokenDto is a data transfer object that represents a token.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenDto {
    /**
     * The token field represents the token.
     */
    private String token;
}

package edu.ntnu.fullstack.prosjekt.quizzer.controllerTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ntnu.fullstack.prosjekt.quizzer.controllers.TokenController;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.dto.LoginDto;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.dto.TokenDto;
import edu.ntnu.fullstack.prosjekt.quizzer.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class TokenControllerIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserService userService;

  @Autowired
  private ObjectMapper objectMapper;

  TokenController controller = new TokenController(null);

  @Test
  void authenticateUserValidCredentialsReturnsTokens() throws Exception {
    LoginDto loginDto = new LoginDto("user", "password");
    given(userService.checkCredentials(any(LoginDto.class))).willReturn(true);

    mockMvc.perform(post("/api/token")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(loginDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.accessToken").isNotEmpty())
        .andExpect(jsonPath("$.refreshToken").isNotEmpty());
  }

  @Test
  void authenticateUserInvalidCredentialsReturnsUnauthorized() throws Exception {
    LoginDto loginDto = new LoginDto("user", "wrongpassword");
    given(userService.checkCredentials(any(LoginDto.class))).willReturn(false);

    mockMvc.perform(post("/api/token")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(loginDto)))
        .andExpect(status().isUnauthorized());
  }

  @Test
  void refreshAccessTokenWithValidRefreshTokenReturnsNewAccessToken() throws Exception {
    String userId = "testUser";
    String validRefreshToken = controller.generateRefreshToken(userId);
    TokenDto refreshTokenDto = new TokenDto(validRefreshToken);

    mockMvc.perform(post("/api/token/refresh")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(refreshTokenDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.token").isNotEmpty());
  }

  @Test
  void refreshAccessTokenWithInvalidRefreshTokenReturnsUnauthorized() throws Exception {
    // Arrange
    String invalidRefreshToken = "invalidToken";
    TokenDto refreshTokenDto = new TokenDto(invalidRefreshToken);

    // Act & Assert
    mockMvc.perform(post("/api/token/refresh")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(refreshTokenDto)))
        .andExpect(status().isUnauthorized());
  }
}

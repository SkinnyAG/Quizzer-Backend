package edu.ntnu.fullstack.prosjekt.quizzer.controllerTests;

import edu.ntnu.fullstack.prosjekt.quizzer.controllers.TokenController;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class TokenControllerTest {

  TokenController controller = new TokenController(null);

  @Test
  void generateAccessToken_ValidUserId_GeneratesValidToken() {

    String userId = "user123";

    String accessToken = controller.generateAccessToken(userId);

    assertThat(accessToken).isNotNull();
  }

  @Test
  void generateRefreshToken_ValidUserId_GeneratesValidToken() {
    String userId = "user123";

    String refreshToken = controller.generateRefreshToken(userId);

    assertThat(refreshToken).isNotNull();
  }
}

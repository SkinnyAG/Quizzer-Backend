package edu.ntnu.fullstack.prosjekt.quizzer.controllerTests;

import edu.ntnu.fullstack.prosjekt.quizzer.controllers.TokenController;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class TokenControllerTest {

  TokenController controller = new TokenController(null);

  @Test
  void generateAccessTokenValidUserIdGeneratesValidToken() {

    String userId = "user123";

    String accessToken = controller.generateAccessToken(userId);

    assertThat(accessToken).isNotNull();
  }

  @Test
  void generateRefreshTokenValidUserIdGeneratesValidToken() {
    String userId = "user123";

    String refreshToken = controller.generateRefreshToken(userId);

    assertThat(refreshToken).isNotNull();
  }
}

package edu.ntnu.fullstack.prosjekt.quizzer.controllerTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ntnu.fullstack.prosjekt.quizzer.controllers.TokenController;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.dto.LoginDto;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.dto.UserDto;
import edu.ntnu.fullstack.prosjekt.quizzer.services.UserService;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserControllerIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserService userService;

  @Autowired
  private ObjectMapper objectMapper;

  TokenController controller = new TokenController(null);

  void mockSecurityContext(String username) {
    SecurityContextHolder.getContext().setAuthentication(
        new UsernamePasswordAuthenticationToken(username, "password", Collections.emptyList())
    );
  }

  @Test
  void createUserWithValidDataReturnsCreatedUser() throws Exception {
    UserDto newUser = new UserDto("newUser", "New User", "newuser@example.com", "password");
    given(userService.createUser(any(UserDto.class))).willReturn(newUser);

    mockMvc.perform(post("/api/users/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(newUser)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.username").value("newUser"));
  }

  @Test
  void loginUserWithValidCredentialsReturnsAuthenticatedMessage() throws Exception {
    LoginDto loginDto = new LoginDto("user", "password");
    given(userService.checkCredentials(any(LoginDto.class))).willReturn(true);

    mockMvc.perform(post("/api/users/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(loginDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message").value("User authenticated successfully"));
  }

  @Test
  void loginUserWithInvalidCredentialsReturnsUnauthorized() throws Exception {
    LoginDto invalidLogin = new LoginDto("user", "wrongPassword");
    given(userService.checkCredentials(any(LoginDto.class))).willReturn(false);

    mockMvc.perform(post("/api/users/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(invalidLogin)))
        .andExpect(status().isUnauthorized())
        .andExpect(jsonPath("$.message").value("Invalid credentials"));
  }


  @Test
  void getUserWithValidUsernameReturnsUserInfo() throws Exception {
    String username = "existingUser";
    mockSecurityContext(username);

    UserDto userDto = new UserDto(username, "Existing User", "user@example.com", null);
    given(userService.findDtoByUsername(username)).willReturn(userDto);

    mockMvc.perform(get("/api/users/{username}", username))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.username").value(username));
  }

  @Test
  void getUserWhenUnauthenticatedReturnsUnauthorized() throws Exception {
    mockMvc.perform(get("/api/users/{username}", "existingUser"))
        .andExpect(status().isUnauthorized());
  }

  @Test
  void updateUserWithValidDataReturnsSuccessMessage() throws Exception {
    String username = "existingUser";
    mockSecurityContext(username);

    UserDto userUpdateInfo = new UserDto(username, "Updated Name", "updated@example.com", "newPassword");

    mockMvc.perform(patch("/api/users/{username}", username)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userUpdateInfo)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message").value("User information updated successfully"));
  }

  @Test
  void updateUserWhenUnauthenticatedReturnsUnauthorized() throws Exception {
    UserDto userUpdateInfo = new UserDto("existingUser", "New Name", "newemail@example.com", "newPassword");

    mockMvc.perform(patch("/api/users/{username}", "existingUser")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userUpdateInfo)))
        .andExpect(status().isUnauthorized());
  }


}

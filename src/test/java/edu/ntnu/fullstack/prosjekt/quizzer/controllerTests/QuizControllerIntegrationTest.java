package edu.ntnu.fullstack.prosjekt.quizzer.controllerTests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ntnu.fullstack.prosjekt.quizzer.controllers.QuizController;
import edu.ntnu.fullstack.prosjekt.quizzer.controllers.TokenController;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.dto.*;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.CategoryEntity;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.QuizEntity;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.UserEntity;
import edu.ntnu.fullstack.prosjekt.quizzer.services.QuizService;
import edu.ntnu.fullstack.prosjekt.quizzer.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.awaitility.Awaitility.given;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class QuizControllerIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private QuizService quizService;

  @MockBean
  private UserService userService;

  @Autowired
  private ObjectMapper objectMapper;

  QuizController controller = new QuizController(null, null);

  void mockSecurityContext(String username) {
    SecurityContextHolder.getContext().setAuthentication(
            new UsernamePasswordAuthenticationToken(username, "password", Collections.emptyList())
    );
  }
  @Test
  void canCreateQuiz() throws Exception {
    SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
    securityContext.setAuthentication(new UsernamePasswordAuthenticationToken("testUser", null));
    SecurityContextHolder.setContext(securityContext);

    when(userService.findEntityByUsername("testUser")).thenReturn((QuizControllerTestDataUtil.createTestUserA()));

    doNothing().when(quizService).createQuiz(any(QuizDetailsDto.class), any(UserEntity.class));

    QuizDetailsDto quizDto = QuizDetailsDto.builder()
            .quizId(1L)
            .title("Shark quiz")
            .description("This is a quiz about sharks")
            .questions(QuizControllerTestDataUtil.createQuestions())
            .build();

    mockMvc.perform(post("/api/quizzes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(quizDto)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.message").value("Created quiz"));
  }

  @Test
  void canGetPageOfQuizzes() throws Exception {
    Page<QuizGeneralDto> page = new PageImpl<>(Collections.emptyList());
    when(quizService.findPageOfQuizzes(any(Pageable.class))).thenReturn(page);

    mockMvc.perform(get("/api/quizzes"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content").isEmpty());
  }

  @Test
  void canGetFilteredPageOfQuizzes() throws Exception {
    String searchQuery = "Nature";
    Page<QuizGeneralDto> page = new PageImpl<>(Collections.emptyList());
    when(quizService.filterQuizzes(eq(searchQuery), any(Pageable.class))).thenReturn(page);

    mockMvc.perform(get("/api/quizzes/filter")
            .param("searchQuery", searchQuery))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content").isEmpty());

    verify(quizService).filterQuizzes(eq(searchQuery), any(Pageable.class));
  }

  @Test
  void canFindQuizDetails() throws Exception {
    String quizId = "1337";
    QuizDetailsDto mockQuizDetailsDto = new QuizDetailsDto();
    mockQuizDetailsDto.setTitle("Shark Quiz");

    when(quizService.findQuizDetails(eq(quizId))).thenReturn(mockQuizDetailsDto);

    mockMvc.perform(get("/api/quizzes/{quizId}", quizId))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$").exists())
            .andExpect(jsonPath("$.title").value("Shark Quiz"));
  }

  @Test
  void notFoundWhenCannotGetQuizDetails() throws Exception {
    String quizId = "1337";
    when(quizService.findQuizDetails(eq(quizId))).thenReturn(null);

    mockMvc.perform(get("/api/quizzes/{quizId}", quizId))
            .andExpect(status().isNotFound());
  }

  @Test
  void deleteQuizReturnsUnauthorized() throws Exception {
    SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
    securityContext.setAuthentication(new UsernamePasswordAuthenticationToken("testUser", null));
    SecurityContextHolder.setContext(securityContext);

    QuizDetailsDto quizDetailsDtoMock = QuizControllerTestDataUtil.createTestDtoQuizA();
    quizDetailsDtoMock.setOwner(QuizControllerTestDataUtil.createTestUserDtoA());
    when(quizService.findQuizDtoById(anyString())).thenReturn(quizDetailsDtoMock);

    mockMvc.perform(delete("/api/quizzes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(quizDetailsDtoMock)))
            .andExpect(status().isUnauthorized())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message").value("You are not authorized to delete this quiz"));
  }

  @Test
  void canGetCategories() throws Exception {
    List<CategoryDto> mockedCategories = QuizControllerTestDataUtil.createCategoryDtos();
    when(quizService.findAllCategories()).thenReturn(mockedCategories);

    mockMvc.perform(get("/api/quizzes/categories"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].categoryName").value("Nature"))
            .andExpect(jsonPath("$[1].categoryName").value("Geography"));
  }
}

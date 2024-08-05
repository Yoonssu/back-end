//package com.aeon.hadog.controller;
//
//import com.aeon.hadog.base.dto.adopt_review.AdoptReviewDTO;
//import com.aeon.hadog.base.dto.adopt_review.ReviewCommentDTO;
//import com.aeon.hadog.base.dto.adopt_review.ReviewImageDTO;
//import com.aeon.hadog.base.dto.response.ResponseDTO;
//import com.aeon.hadog.base.dto.user.LoginRequestDTO;
//import com.aeon.hadog.service.AdoptReviewService;
//import com.aeon.hadog.service.UserService;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import jakarta.transaction.Transactional;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import java.time.LocalDateTime;
//import java.util.Collections;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@Transactional
//class AdoptReviewControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private AdoptReviewService adoptReviewService;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    private Long testReviewId;
//
//    @Mock
//    UserService userService;
//
//
//    @Test
//    @DisplayName("입양후기 등록 테스트")
//    void createReview() throws Exception {
//        // given
//        String id = "user2";
//        String password = "User3@@@";
//
//        LoginRequestDTO loginDto = new LoginRequestDTO(id, password);
//        String token = loginUserAndGetToken(loginDto);
//
//
//        AdoptReviewDTO adoptReviewDTO = AdoptReviewDTO.builder()
//                .reviewId(1L)
//                .reviewDate(LocalDateTime.now())
//                .content("입양 후기")
//                .build();
//
//        String adoptReviewDTOJson = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(adoptReviewDTO);
//
//
//        MockMultipartFile adoptReviewDTOFile = new MockMultipartFile(
//                "reviewDTO",
//                "reviewDTO",
//                "application/json",
//                adoptReviewDTOJson.getBytes()
//        );
//
//        MockMultipartFile image1 = new MockMultipartFile(
//                "images",
//                "image1.jpg",
//                MediaType.IMAGE_JPEG_VALUE,
//                "image1".getBytes()
//        );
//
//        MockMultipartFile image2 = new MockMultipartFile(
//                "images",
//                "image2.jpg",
//                MediaType.IMAGE_JPEG_VALUE,
//                "image2".getBytes()
//        );
//
//        // when
//        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.multipart("/reviews")
//                .file(adoptReviewDTOFile)
//                .file(image1)
//                .file(image2)
//                .header("Authorization",  "Bearer " + token)
//                .contentType(MediaType.MULTIPART_FORM_DATA));
//
//        // then
//        resultActions.andExpect(status().isOk())
//                .andExpect(jsonPath("$.status").value(200))
//                .andExpect(jsonPath("$.success").value(true))
//                .andExpect(jsonPath("$.message").value("입양후기 등록 성공"))
//                .andExpect(jsonPath("$.data").exists());
//    }
//
//
//    @Test
//    @DisplayName("입양후기 조회 테스트")
//    void getReview_Success() throws Exception {
//        // given
//        Long ReviewId = 1L; // 테스트용 Review ID
//
//        // when
//        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/reviews/{id}", ReviewId)
//                .accept(MediaType.APPLICATION_JSON));
//
//        // then
//        resultActions.andExpect(status().isOk())
//                .andExpect(jsonPath("$.status").value(200))
//                .andExpect(jsonPath("$.success").value(true))
//                .andExpect(jsonPath("$.message").value("입양후기 조회 성공"))
//                .andExpect(jsonPath("$.data").exists());
//    }
//
//    @Test
//    @DisplayName("입양후기 목록 조회 테스트")
//    void getAllReviews_Success() throws Exception {
//        // when
//        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/reviews")
//                .accept(MediaType.APPLICATION_JSON));
//
//        // then
//        resultActions.andExpect(status().isOk())
//                .andExpect(jsonPath("$.status").value(200))
//                .andExpect(jsonPath("$.success").value(true))
//                .andExpect(jsonPath("$.message").value("입양후기 목록 조회 성공"))
//                .andExpect(jsonPath("$.data").isArray());
//    }
//
//
//    private String loginUserAndGetToken(LoginRequestDTO loginDto) throws Exception {
//        String json = new ObjectMapper().writeValueAsString(loginDto);
//
//        String response = mockMvc.perform(post("/user/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andReturn()
//                .getResponse()
//                .getContentAsString();
//
//        // 응답에서 토큰 추출
//        ObjectMapper objectMapper = new ObjectMapper();
//        JsonNode jsonNode = objectMapper.readTree(response);
//        String token = jsonNode.get("data").asText();
//
//        return token;
//    }
//}

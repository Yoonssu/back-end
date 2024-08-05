//package com.aeon.hadog.controller;
//
//import com.aeon.hadog.base.dto.mypage.MyPageDTO;
//import com.aeon.hadog.base.dto.adoptPost.AdoptPostDTO;
//import com.aeon.hadog.base.dto.adopt_review.AdoptReviewDTO;
//import com.aeon.hadog.base.dto.pet.PetDTO;
//import com.aeon.hadog.base.dto.user.LoginRequestDTO;
//import com.aeon.hadog.service.MyPageService;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.h2.command.Token;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//
//import java.time.LocalDateTime;
//import java.util.Collections;
//import java.util.List;
//
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@WebMvcTest(MyPageController.class)
//@AutoConfigureMockMvc
//class MyPageControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private MyPageService myPageService;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Test
//    @DisplayName("사용자 정보 조회 성공 테스트")
//    void getUserInfo_Success() throws Exception {
//
//
//        // given
//        String id = "user2";
//        String password = "User3@@@";
//
//        LoginRequestDTO loginDto = new LoginRequestDTO(id, password);
//        String token = loginUserAndGetToken(loginDto);
//
//        MyPageDTO userInfo = MyPageDTO.builder()
//                .name("홍길동")
//                .id("user2")
//                .password("user3@@@")
//                .nickname("길동이01")
//                .email("user01@gmail.com")
//                .build();
//
//        // when
//        ResultActions resultActions = mockMvc.perform(get("/mypage/userinfo")
//                .header("Authorization",  "Bearer " + token)
//                .contentType(MediaType.APPLICATION_JSON));
//
//
//                // then
//        resultActions.andExpect(status().isOk())
//                .andExpect(jsonPath("$.status").value(200))
//                .andExpect(jsonPath("$.success").value(true))
//                .andExpect(jsonPath("$.message").value("사용자 정보 조회 성공"))
//                .andExpect(jsonPath("$.data").exists());
//    }
//
//    @Test
//    @DisplayName("반려견 정보 조회 성공 테스트")
//    void getPetInfo_Success() throws Exception {
//        // given
//        String userId = "user1";
//        List<PetDTO> petInfo = Collections.singletonList(new PetDTO("Max", "Golden Retriever", "male", true, "image.jpg", 3));
//
//        // when
//        when(myPageService.getPetInfo(userId)).thenReturn(petInfo);
//
//        // when
//        ResultActions resultActions = mockMvc.perform(get("/mypage/petinfo")
//                .contentType(MediaType.APPLICATION_JSON));
//
//        // then
//        resultActions.andExpect(status().isOk())
//                .andExpect(jsonPath("$.status").value(200))
//                .andExpect(jsonPath("$.success").value(true))
//                .andExpect(jsonPath("$.message").value("반려견 정보 조회 성공"))
//                .andExpect(jsonPath("$.data").exists());
//    }
//
//
//    @Test
//    @DisplayName("닉네임 수정 성공 테스트")
//    void updateNickname_Success() throws Exception {
//        // given
//        String userId = "user1";
//        String newNickname = "NewNickname";
//
//        // when
//        ResultActions resultActions = mockMvc.perform(put("/mypage/nickname")
//                .param("newNickname", newNickname)
//                .contentType(MediaType.APPLICATION_JSON));
//
//        // then
//        resultActions.andExpect(status().isOk())
//                .andExpect(jsonPath("$.status").value(200))
//                .andExpect(jsonPath("$.success").value(true))
//                .andExpect(jsonPath("$.message").value("닉네임 수정 성공"))
//                .andExpect(jsonPath("$.data").exists());
//    }
//
//    @Test
//    @DisplayName("사용자 입양 후기 목록 조회 성공 테스트")
//    void getAdoptReviewsByUser_Success() throws Exception {
//        // given
//        String userId = "user1";
//
//
//        // when
//        ResultActions resultActions = mockMvc.perform(get("/mypage/adoptreviews")
//                .contentType(MediaType.APPLICATION_JSON));
//
//        // then
//        resultActions.andExpect(status().isOk())
//                .andExpect(jsonPath("$.status").value(200))
//                .andExpect(jsonPath("$.success").value(true))
//                .andExpect(jsonPath("$.message").value("사용자 입양 후기 목록 조회 성공"))
//                .andExpect(jsonPath("$.data").exists());
//
//    }
//
//    @Test
//    @DisplayName("내가 작성한 임시 보호글 목록 조회 성공 테스트")
//    void getMyAdoptPosts_Success() throws Exception {
//        // given
//        String userId = "user1";
//
//        // when
//        ResultActions resultActions = mockMvc.perform(get("/mypage/adoptposts")
//                .contentType(MediaType.APPLICATION_JSON));
//
//        // then
//        resultActions.andExpect(status().isOk())
//                .andExpect(jsonPath("$.status").value(200))
//                .andExpect(jsonPath("$.success").value(true))
//                .andExpect(jsonPath("$.message").value("내가 작성한 임시 보호글 목록 조회 완료"))
//                .andExpect(jsonPath("$.data[0].createdAt").exists());
//    }
//
//    private String loginUserAndGetToken(LoginRequestDTO loginDto) throws Exception {
//        String json = new ObjectMapper().writeValueAsString(loginDto);
//
//        String response = mockMvc.perform(post("/user/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isOk())
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
//

//package com.aeon.hadog.service;
//
//import com.aeon.hadog.base.dto.adopt_review.AdoptReviewDTO;
//import com.aeon.hadog.base.dto.adopt_review.ReviewCommentDTO;
//import com.aeon.hadog.controller.AdoptReviewController;
//import com.aeon.hadog.domain.AdoptReview;
//import com.aeon.hadog.domain.ReviewComment;
//import com.aeon.hadog.repository.ReviewCommentRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyList;
//import static org.mockito.Mockito.when;
//
//@WebMvcTest(AdoptReviewController.class)
//@ActiveProfiles("test")
//class AdoptReviewServiceTest {
//
//
//        @Autowired
//        private MockMvc mockMvc;
//
//        @MockBean
//        private AdoptReviewService adoptReviewService;
//
//        @MockBean
//        private ReviewCommentRepository reviewCommentRepository;
//
//
//        @Test
//        void createReview() throws Exception {
//            // given
//            String userId = "user1";
//            AdoptReviewDTO reviewDTO = new AdoptReviewDTO();
//            reviewDTO.setContent("테스트 입양 후기");
//
//            List<MultipartFile> images = new ArrayList<>(); // 이 부분은 실제 MultipartFile 객체를 생성해야 하지만, 테스트에서는 Mock으로 대체합니다.
//
//            AdoptReview savedReview = AdoptReview.builder()
//                    .reviewId(1L)
//                    .content(reviewDTO.getContent())
//                    .reviewDate(LocalDateTime.now())
//                    .build();
//
//            when(adoptReviewService.saveReview(any(AdoptReview.class), anyList())).thenReturn(savedReview);
//
//            // when, then
//            mockMvc.perform(MockMvcRequestBuilders.post("/reviews")
//                            .contentType(MediaType.MULTIPART_FORM_DATA)
//                            .param("user", userId)
//                            .content("reviewDTO=" + reviewDTO)
//                            .content("images=" + images)
//                            .accept(MediaType.APPLICATION_JSON))
//                    .andExpect(MockMvcResultMatchers.status().isOk())
//                    .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200))
//                    .andExpect(MockMvcResultMatchers.jsonPath("$.data.reviewId").value(1L))
//                    .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("입양후기 등록 성공"));
//        }
//
//        @Test
//        void getReview() throws Exception {
//            // given
//            Long reviewId = 1L;
//            AdoptReview review = AdoptReview.builder()
//                    .reviewId(reviewId)
//                    .content("테스트 입양 후기")
//                    .reviewDate(LocalDateTime.now())
//                    .build();
//
//            when(adoptReviewService.findById(reviewId)).thenReturn(Optional.of(review));
//
//            // when, then
//            mockMvc.perform(MockMvcRequestBuilders.get("/reviews/{id}", reviewId)
//                            .accept(MediaType.APPLICATION_JSON))
//                    .andExpect(MockMvcResultMatchers.status().isOk())
//                    .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200))
//                    .andExpect(MockMvcResultMatchers.jsonPath("$.data.reviewId").value(1L))
//                    .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("입양후기 조회 성공"));
//        }
//
//        @Test
//        void getAllReviews() throws Exception {
//            // given
//            List<AdoptReview> reviews = new ArrayList<>();
//            reviews.add(AdoptReview.builder()
//                    .reviewId(1L)
//                    .content("테스트 입양 후기 1")
//                    .reviewDate(LocalDateTime.now())
//                    .build());
//            reviews.add(AdoptReview.builder()
//                    .reviewId(2L)
//                    .content("테스트 입양 후기 2")
//                    .reviewDate(LocalDateTime.now())
//                    .build());
//
//            when(adoptReviewService.findAll()).thenReturn(reviews);
//
//            // when, then
//            mockMvc.perform(MockMvcRequestBuilders.get("/reviews")
//                            .accept(MediaType.APPLICATION_JSON))
//                    .andExpect(MockMvcResultMatchers.status().isOk())
//                    .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200))
//                    .andExpect(MockMvcResultMatchers.jsonPath("$.data.length()").value(2))
//                    .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("입양후기 목록 조회 성공"));
//        }
//
//
//    }
//
//
//
//

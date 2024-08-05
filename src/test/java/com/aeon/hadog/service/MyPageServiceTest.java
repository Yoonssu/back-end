//package com.aeon.hadog.service;
//
//import com.aeon.hadog.base.dto.adoptPost.AdoptPostDTO;
//import com.aeon.hadog.base.dto.adopt_review.AdoptReviewDTO;
//import com.aeon.hadog.base.dto.mypage.MyPageDTO;
//import com.aeon.hadog.base.dto.pet.PetDTO;
//import com.aeon.hadog.domain.User;
//import com.aeon.hadog.repository.AdoptPostRepository;
//import com.aeon.hadog.repository.AdoptReviewRepository;
//import com.aeon.hadog.repository.UserRepository;
//import jakarta.transaction.Transactional;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.time.LocalDateTime;
//import java.util.Collections;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest
//@Transactional
//class MyPageServiceTest {
//
//    @Autowired
//    AdoptPostRepository adoptPostRepository;
//
//    @Autowired
//    AdoptReviewRepository adoptReviewRepository;
//
//    @Autowired
//    UserRepository userRepository;
//
//
//    @InjectMocks
//    private MyPageService myPageService;
//
//    @InjectMocks
//    private AdoptPostService adoptPostService;
//
//    @Test
//
//    void getUserInfo_Success() {
//        // given
//        String userId = "user1";
//
//
//        // when
//        User user = userRepository.findById(userId).orElseThrow();
//
//        MyPageDTO actualUserInfo = myPageService.getUserInfo(userId);
//
//        // then
//        assertEquals("홍길동", actualUserInfo.getName());
//        assertEquals("user3", actualUserInfo.getId());
//        assertEquals("user3@@@", actualUserInfo.getPassword());
//        assertEquals("길동이01", actualUserInfo.getNickname());
//        assertEquals("user01@gmail.com", actualUserInfo.getEmail());
//    }
//
//    @Test
//    void getPetInfo_Success() {
//        // given
//        String userId = "user1";
//        List<PetDTO> expectedPetInfo = Collections.singletonList(new PetDTO("Max", "Golden Retriever", "male", true, "image.jpg", 3));
//
//        // when
//        List<PetDTO> actualPetInfo = myPageService.getPetInfo(userId);
//
//        // then
//        assertEquals(expectedPetInfo.size(), actualPetInfo.size());
//        assertEquals(expectedPetInfo.get(0).getName(), actualPetInfo.get(0).getName());
//        assertEquals(expectedPetInfo.get(0).getBreed(), actualPetInfo.get(0).getBreed());
//    }
//
//    @Test
//    void updateNickname_Success() {
//        // given
//        String userId = "user1";
//        String newNickname = "NewNickname";
//
//        // when
//        myPageService.updateNickname(userId, newNickname);
//
//        // then: No return value to test
//    }
//
//    @Test
//    @DisplayName("사용자 입양 후기 목록 조회 성공 테스트")
//    void getAdoptReviewsByUserId_Success() {
//        // given
//        String userId = "user1";
//        List<AdoptReviewDTO> expectedReviews = Collections.singletonList(new AdoptReviewDTO(1L, LocalDateTime.now() , List.of(),"후기"));
//
//        // when
//        List<AdoptReviewDTO> actualReviews = myPageService.getAdoptReviewsByUserId(userId);
//
//        // then
//        assertEquals(expectedReviews.size(), actualReviews.size());
//        assertEquals(expectedReviews.get(0).getReviewId(), actualReviews.get(0).getReviewId());
//        assertEquals(expectedReviews.get(0).getContent(), actualReviews.get(0).getContent());
//    }
//
//
//
//}

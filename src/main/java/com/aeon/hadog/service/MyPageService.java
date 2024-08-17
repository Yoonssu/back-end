package com.aeon.hadog.service;

import com.aeon.hadog.base.code.ErrorCode;
import com.aeon.hadog.base.dto.emotionTrack.EmotionDTO;
import com.aeon.hadog.base.dto.emotionTrack.EmotionTrackDTO;
import com.aeon.hadog.base.dto.mypage.MyPageDTO;
import com.aeon.hadog.base.dto.adoptPost.AdoptPostDTO;
import com.aeon.hadog.base.dto.adopt_review.AdoptReviewDTO;
import com.aeon.hadog.base.dto.adopt_review.ReviewImageDTO;
import com.aeon.hadog.base.dto.pet.PetDTO;
import com.aeon.hadog.base.exception.PetNotFoundException;
import com.aeon.hadog.base.exception.UserNotFoundException;
import com.aeon.hadog.domain.*;
import com.aeon.hadog.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final UserRepository userRepository;
    private final PetRepository petRepository;
    private final AdoptReviewService adoptReviewService;
    private final AdoptPostRepository adoptPostRepository;
    private final AdoptPostImagesRepository adoptPostImagesRepository;
    private final EmotionTrackRepository emotionTrackRepository;
    private final EmotionRepository emotionRepository;

    public MyPageDTO getUserInfo(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));

        return MyPageDTO.builder()
                .name(user.getName())
                .id(user.getId())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .build();
    }

    public List<PetDTO> getPetInfo(String userId) {
        List<Pet> pets = petRepository.findByUserId(userId);
        if (pets.isEmpty()) {
            throw new PetNotFoundException(ErrorCode.PET_NOT_FOUND);
        }
        return pets.stream().map(this::mapToPetDTO).collect(Collectors.toList());
    }

    private PetDTO mapToPetDTO(Pet pet) {
        return PetDTO.builder()
                .petId(pet.getPetId())
                .name(pet.getName())
                .breed(pet.getBreed())
                .sex(pet.getSex())
                .neuter(pet.getNeuter())
                .image(pet.getImage())
                .age(pet.getAge())
                .feature(pet.getFeature())
                .build();
    }

    public void updateNickname(String userId, String newNickname) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));

        user.setNickname(newNickname);
        userRepository.save(user);
    }



    // 사용자 ID를 기반으로 해당 사용자가 작성한 입양 후기 목록을 DTO로 변환하여 반환하는 메서드
    public List<AdoptReviewDTO> getAdoptReviewsByUserId(String userId) {
        return adoptReviewService.findReviewsByUserId(userId).stream()
                .map(this::mapToAdoptReviewDTO)
                .collect(Collectors.toList());
    }

    private AdoptReviewDTO mapToAdoptReviewDTO(AdoptReview review) {
        return AdoptReviewDTO.builder()
                .reviewId(review.getReviewId())
                .reviewDate(review.getReviewDate())
                .content(review.getContent())
                .images(review.getImages().stream()
                        .map(image -> ReviewImageDTO.builder()
                                .imageId(image.getImageId())
                                .fileName(image.getFileName())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    // 사용자 ID를 기반으로 해당 사용자가 작성한 입양 공고 목록을 조회하는 메서드
    public List<AdoptPostDTO> getMyAdoptPosts(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));
        List<AdoptPost> adoptPosts = adoptPostRepository.findByUser(user);
        return adoptPosts.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // AdoptPost를 AdoptPostDTO로 변환하는 메서드
    private AdoptPostDTO convertToDTO(AdoptPost adoptPost) {
        // 입양 공고의 이미지 정보를 가져옴
        List<AdoptPostImages> images = adoptPost.getImages();

        // 이미지 URL 목록을 생성
        List<String> imageUrls = images.stream()
                .map(AdoptPostImages::getImageUrl)
                .collect(Collectors.toList());

        // 필요한 필드들을 DTO에 매핑하여 반환
        return AdoptPostDTO.builder()
                .content(adoptPost.getContent())
                .imageUrls(imageUrls) // 이미지 URL 목록 추가
                .name(adoptPost.getName())
                .breed(adoptPost.getBreed())
                .sex(adoptPost.getSex())
                .age(adoptPost.getAge())
                .phone(adoptPost.getPhone())
                .duration(adoptPost.getDuration())
                .neutering(adoptPost.isNeutering())
                .adoptStatus(adoptPost.isAdoptStatus())
                .user(adoptPost.getUser())
                .build();
    }


    public List<EmotionTrackDTO> getEmotionTracksByPetId(Long petId) {
        List<EmotionTrack> emotionTracks = emotionTrackRepository.findByPetIdWithEmotion(petId);

        return emotionTracks.stream().map(emotionTrack -> {
            Emotion emotion = emotionTrack.getEmotion();
            EmotionDTO emotionDTO = EmotionDTO.builder()
                    .emotionId(emotion.getEmotionId())
                    .emotion(emotion.getEmotion())
                    .description(emotion.getDescription())
                    .image(emotion.getImage())
                    .build();

            return EmotionTrackDTO.builder()
                    .emotionTrackId(emotionTrack.getEmotionTrackId())
                    .petId(emotionTrack.getPetId())
                    .emotionDate(emotionTrack.getEmotionDate())
                    .emotionId(emotion.getEmotionId())
                    .emotion(emotionDTO)
                    .build();
        }).collect(Collectors.toList());
    }


}

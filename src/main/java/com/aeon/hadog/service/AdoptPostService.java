package com.aeon.hadog.service;

import com.aeon.hadog.base.code.ErrorCode;
import com.aeon.hadog.base.dto.adoptPost.AdoptPostDTO;
import com.aeon.hadog.base.dto.adoptPost.ListAdoptPostDTO;
import com.aeon.hadog.base.exception.AdoptPostNotBelongToUserException;
import com.aeon.hadog.base.exception.AdoptPostNotFoundException;
import com.aeon.hadog.base.exception.UserNotFoundException;
import com.aeon.hadog.domain.*;
import com.aeon.hadog.repository.AdoptPostImagesRepository;
import com.aeon.hadog.repository.AdoptPostRepository;
import com.aeon.hadog.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class AdoptPostService {
    private final UserRepository userRepository;
    private final AdoptPostRepository adoptPostRepository;

    private final AdoptPostImagesRepository adoptPostImagesRepository;

    public Long uploadPost(String userId, AdoptPostDTO adoptPostDTO){
        User user = userRepository.findById(userId).orElseThrow(()->new UserNotFoundException(ErrorCode.USER_NOT_FOUND));

        AdoptPost post = AdoptPost.builder()
                .content(adoptPostDTO.getContent())
                .name(adoptPostDTO.getName())
                .breed(adoptPostDTO.getBreed())
                .sex(adoptPostDTO.getSex())
                .age(adoptPostDTO.getAge())
                .phone(adoptPostDTO.getPhone())
                .duration(adoptPostDTO.getDuration())
                .neutering(adoptPostDTO.isNeutering())
                .adoptStatus(false)
                .user(user)
                .build();

        adoptPostRepository.save(post);

        for (String imageUrl : adoptPostDTO.getImageUrls()) {
            AdoptPostImages image = new AdoptPostImages();
            image.setImageUrl(imageUrl);
            image.setAdoptPost(post);

            adoptPostImagesRepository.save(image);
        }


        return post.getAdoptPostId();
    }

    public AdoptPostDTO getPostDetail(Long postId){
        AdoptPost post = adoptPostRepository.findByAdoptPostId(postId).orElseThrow(()->new AdoptPostNotFoundException(ErrorCode.ADOPT_POST_NOT_FOUND));
        List<AdoptPostImages> adoptPostImages = adoptPostImagesRepository.findByAdoptPost(post);

        List<String> images = new ArrayList<>();
        for (int i=0; i<adoptPostImages.size(); i++) {
            images.add(adoptPostImages.get(i).getImageUrl());
        }

        AdoptPostDTO dto = AdoptPostDTO.builder()
                .content(post.getContent())
                .imageUrls(images)
                .name(post.getName())
                .breed(post.getBreed())
                .sex(post.getSex())
                .age(post.getAge())
                .phone(post.getPhone())
                .duration(post.getDuration())
                .neutering(post.isNeutering())
                .adoptStatus(post.isAdoptStatus())
                .user(post.getUser())
                .build();

        return dto;
    }

    public Page<ListAdoptPostDTO> getList(int page){
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("adoptPostId"));
        Pageable pageable = PageRequest.of(page, 15, Sort.by(sorts));
        Page<AdoptPost> posts = adoptPostRepository.findAll(pageable);
        return posts.map(this::convertToListDTO);
    }

    private ListAdoptPostDTO convertToListDTO(AdoptPost adoptPost) {
        ListAdoptPostDTO dto = new ListAdoptPostDTO();
        dto.setAdoptPostId(adoptPost.getAdoptPostId());
        dto.setName(adoptPost.getName());
        dto.setBreed(adoptPost.getBreed());
        dto.setAge(adoptPost.getAge());
        dto.setDuration(adoptPost.getDuration());
        dto.setThumbnail(adoptPostImagesRepository.findFirstByAdoptPostOrderByAdoptPostImagesIdAsc(adoptPost).getImageUrl());
        return dto;
    }

    public boolean modifyAdoptStatus(String userId, Long adoptPostId, boolean isAdopt){
        User user = userRepository.findById(userId).orElseThrow(()->new UserNotFoundException(ErrorCode.USER_NOT_FOUND));
        AdoptPost post = adoptPostRepository.findByAdoptPostId(adoptPostId).orElseThrow(()->new AdoptPostNotFoundException(ErrorCode.ADOPT_POST_NOT_FOUND));

        if (!Objects.equals(user.getUserId(), post.getUser().getUserId())) {
            throw new AdoptPostNotBelongToUserException(ErrorCode.ADOPT_POST_NOT_BELONG_TO_USER_ERROR);
        }

        post.setAdoptStatus(isAdopt);
        adoptPostRepository.save(post);

        return isAdopt;
    }

    public Map<String, String> validateHandling(Errors errors) {
        Map<String, String> validatorResult = new HashMap<>();

        /* 유효성 검사에 실패한 필드 목록을 받음 */
        for (FieldError error : errors.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }

        return validatorResult;
    }



}

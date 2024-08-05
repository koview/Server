package com.koview.koview_server.image.service;

import com.koview.koview_server.global.apiPayload.code.status.ErrorStatus;
import com.koview.koview_server.global.apiPayload.exception.GeneralException;
import com.koview.koview_server.global.apiPayload.exception.MemberException;
import com.koview.koview_server.global.s3.AmazonS3Manager;
import com.koview.koview_server.global.common.image.ImageResponseDTO;
import com.koview.koview_server.global.security.util.SecurityUtil;
import com.koview.koview_server.image.domain.ReviewImage;
import com.koview.koview_server.image.repository.ReviewImageRepository;
import com.koview.koview_server.member.domain.Member;
import com.koview.koview_server.member.repository.MemberRepository;
import com.koview.koview_server.mypage.domain.ProfileImage;
import com.koview.koview_server.mypage.repository.ProfileImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ImageServiceImpl {
    private final AmazonS3Manager s3Manager;
    private final ReviewImageRepository reviewImageRepository;
    private final ProfileImageRepository profileImageRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public ImageResponseDTO createReview(MultipartFile request) {
        String keyName = s3Manager.genReviewsKeyName(s3Manager.genRandomUuid());

        ReviewImage savedReviewImage = reviewImageRepository.save(
                ReviewImage.builder()
                        .url(s3Manager.uploadFile(keyName, request))
                        .build()
        );

        return new ImageResponseDTO(savedReviewImage);
    }


    @Transactional
    public List<ImageResponseDTO> createReviews(List<MultipartFile> requests) {
        List<ImageResponseDTO> urls = new ArrayList<>();

        for (MultipartFile request : requests)
            urls.add(createReview(request));

        return urls;
    }

    @Transactional
    public String deleteReview(Long imageId) {
        ReviewImage reviewImage = reviewImageRepository.findById(imageId).orElseThrow(() -> new GeneralException(ErrorStatus.IMAGE_NOT_FOUND));
        s3Manager.deleteFile(reviewImage.getUrl());
        reviewImageRepository.delete(reviewImage);

        return "삭제하였습니다.";
    }

    @Transactional
    public String deleteReviews(List<Long> imageIds) {
        for (Long imageId : imageIds)
            deleteReview(imageId);
        return "이미지 리스트 삭제하였습니다.";
    }

    public List<ImageResponseDTO> findAll() {
        List<ReviewImage> reviewImages = reviewImageRepository.findAll();
        List<ImageResponseDTO> imageResponseDTOs = new ArrayList<>();
        for (ReviewImage reviewImage : reviewImages) {
            imageResponseDTOs.add(new ImageResponseDTO(reviewImage));
        }
        return imageResponseDTOs;
    }

    @Transactional
    public ImageResponseDTO createProfile(MultipartFile request) {
        String keyName = s3Manager.genUserProfilesKeyName(s3Manager.genRandomUuid());
        Member member = validateMember();

        ProfileImage savedProfileImage = profileImageRepository.save(
                ProfileImage.builder()
                        .url(s3Manager.uploadFile(keyName, request))
                        .member(member)
                        .build()
        );
        member.addProfileImage(savedProfileImage);
        memberRepository.save(member);

        return new ImageResponseDTO(savedProfileImage);
    }

    private Member validateMember() {
        return memberRepository.findByEmail(SecurityUtil.getEmail()).orElseThrow(
                () -> new MemberException(ErrorStatus.MEMBER_NOT_FOUND));
    }
}
package com.koview.koview_server.api.image.service;

import com.koview.koview_server.api.image.repository.ReviewImageRepository;
import com.koview.koview_server.api.common.apiPayload.code.status.ErrorStatus;
import com.koview.koview_server.api.common.apiPayload.exception.GeneralException;
import com.koview.koview_server.api.common.apiPayload.exception.MemberException;
import com.koview.koview_server.global.s3.AmazonS3Manager;
import com.koview.koview_server.api.image.domain.dto.ImageResponseDTO;
import com.koview.koview_server.global.security.util.SecurityUtil;
import com.koview.koview_server.api.image.domain.ReviewImage;
import com.koview.koview_server.api.auth.member.domain.Member;
import com.koview.koview_server.api.auth.member.repository.MemberRepository;
import com.koview.koview_server.api.image.domain.ProfileImage;
import com.koview.koview_server.api.image.repository.ProfileImageRepository;
import com.koview.koview_server.api.product.domain.Product;
import com.koview.koview_server.api.image.domain.ProductImage;
import com.koview.koview_server.api.image.repository.ProductImageRepository;
import com.koview.koview_server.api.product.repository.ProductRepository;
import com.koview.koview_server.api.image.domain.QueryImage;
import com.koview.koview_server.api.image.repository.QueryImageRepository;

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
    private final ProductImageRepository productImageRepository;
    private final QueryImageRepository queryImageRepository;

    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

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

    @Transactional
    public List<ImageResponseDTO> createProducts(Long productId, List<MultipartFile> requests) {
        List<ImageResponseDTO> urls = new ArrayList<>();
        Product product = productRepository.findById(productId).
                orElseThrow(()->new GeneralException(ErrorStatus.PRODUCT_NOT_FOUND));

        for (MultipartFile request : requests)
            urls.add(createProduct(product, request));

        return urls;
    }

    @Transactional
    protected ImageResponseDTO createProduct(Product product, MultipartFile request) {
        String keyName = s3Manager.genProductsKeyName(s3Manager.genRandomUuid());

        ProductImage savedProductImage = productImageRepository.save(
                ProductImage.builder()
                        .url(s3Manager.uploadFile(keyName, request))
                        .product(product)
                        .build()
        );

        return new ImageResponseDTO(savedProductImage);
    }

    @Transactional
    public ImageResponseDTO createQueryImage(MultipartFile request) {
        String keyName = s3Manager.genQueriesKeyName(s3Manager.genRandomUuid());

        QueryImage savedQueryImage = queryImageRepository.save(
            QueryImage.builder()
                .url(s3Manager.uploadFile(keyName, request))
                .build()
        );

        return new ImageResponseDTO(savedQueryImage);
    }

    @Transactional
    public List<ImageResponseDTO> createQueryImages(List<MultipartFile> requests) {
        List<ImageResponseDTO> urls = new ArrayList<>();

        for (MultipartFile request : requests)
            urls.add(createQueryImage(request));

        return urls;
    }

    @Transactional
    public String deleteQueryImage(Long imageId) {
        QueryImage queryImage = queryImageRepository.findById(imageId).orElseThrow(() -> new GeneralException(ErrorStatus.IMAGE_NOT_FOUND));
        s3Manager.deleteFile(queryImage.getUrl());
        queryImageRepository.delete(queryImage);

        return "삭제하였습니다.";
    }

    @Transactional
    public String deleteQueryImages(List<Long> imageIds) {
        for (Long imageId : imageIds)
            deleteQueryImage(imageId);
        return "이미지 리스트 삭제하였습니다.";
    }

    private Member validateMember() {
        return memberRepository.findByEmail(SecurityUtil.getEmail()).orElseThrow(
                () -> new MemberException(ErrorStatus.MEMBER_NOT_FOUND));
    }
}
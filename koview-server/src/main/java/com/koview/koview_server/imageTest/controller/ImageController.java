package com.koview.koview_server.imageTest.controller;

import com.koview.koview_server.imageTest.service.ImageServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/image")
public class ImageController {

    private final ImageServiceImpl reviewImageService;
    @PostMapping(value = "/review",consumes = "multipart/form-data")
    public String postReviewImage(@RequestPart MultipartFile image){

        return reviewImageService.createReview(image);
    }
}

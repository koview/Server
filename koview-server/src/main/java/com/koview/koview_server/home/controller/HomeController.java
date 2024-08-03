package com.koview.koview_server.home.controller;

import com.koview.koview_server.global.apiPayload.ApiResult;
import com.koview.koview_server.home.dto.HomeResponseDTO;
import com.koview.koview_server.home.facade.HomeFacade;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/home")
@Tag(name = "Home", description = "Home API")
public class HomeController {

    private final HomeFacade homeFacade;

    @GetMapping(value="")
    public ApiResult<HomeResponseDTO> getHome(){
        return ApiResult.onSuccess(homeFacade.getHomeV1());
    }
}

package com.reditus.daangn.image

import com.reditus.daangn.core.controller.interceptor.annotation.Login
import com.reditus.daangn.core.jwt.MemberAuth
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@Tag(name = "Image", description = "이미지 업로드")
@RestController
@RequestMapping("/api/v1/images")
class ImageController(
    private val imageService: ImageService,
) {

    @Operation(
        summary = "이미지 업로드", description = "이미지를 업로드합니다.",
        responses = [
            ApiResponse(responseCode = "201", description = "이미지 업로드 성공"),
        ]
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun uploadImage(
        @Login memberAuth: MemberAuth,
        @RequestPart("imageFiles") imageFiles: List<MultipartFile>,
    ) :List<String> {
        return imageService.uploadTempImage(memberAuth.id, imageFiles)
    }
}
package com.reditus.daangn.image

import com.reditus.daangn.core.controller.interceptor.annotation.Login
import com.reditus.daangn.core.jwt.MemberAuth
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/v1/images")
class ImageController(
    private val imageService: ImageService,
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun uploadImage(
        @Login memberAuth: MemberAuth,
        @RequestPart("imageFiles") imageFiles: List<MultipartFile>,
    ) :List<String> {
        return imageService.uploadTempImage(memberAuth.id, imageFiles)
    }
}
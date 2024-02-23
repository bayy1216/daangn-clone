package com.reditus.daangn.saleposts.controller

import com.reditus.daangn.core.controller.interceptor.annotation.Login
import com.reditus.daangn.core.jwt.MemberAuth
import com.reditus.daangn.saleposts.controller.dto.request.CreateSalePostRequest
import com.reditus.daangn.saleposts.service.SalePostService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/saleposts")
class SalePostController(
    private val salePostService: SalePostService
) {
    @PostMapping
    fun createSalePost(@Login memberAuth: MemberAuth, @Valid @RequestBody request: CreateSalePostRequest) {
        salePostService.createSalePost(memberAuth.id, request)
    }
}

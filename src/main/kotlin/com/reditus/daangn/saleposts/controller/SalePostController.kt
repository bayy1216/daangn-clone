package com.reditus.daangn.saleposts.controller

import com.reditus.daangn.core.controller.dto.PagingResponse
import com.reditus.daangn.core.controller.interceptor.annotation.Login
import com.reditus.daangn.core.jwt.MemberAuth
import com.reditus.daangn.saleposts.controller.dto.request.CreateSalePostRequest
import com.reditus.daangn.saleposts.controller.dto.request.PagingSalePostsParams
import com.reditus.daangn.saleposts.controller.dto.request.UpdateSalePostRequest
import com.reditus.daangn.saleposts.controller.dto.response.SalePostDto
import com.reditus.daangn.saleposts.domain.SalePostCategory
import com.reditus.daangn.saleposts.service.SalePostService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/saleposts")
class SalePostController(
    private val salePostService: SalePostService
) {
    @GetMapping
    fun pagingSalePost(
        @Login memberAuth: MemberAuth,
        requestParam : PagingSalePostsParams
    ) : PagingResponse<SalePostDto> {
        return salePostService.pagingSalePost(memberAuth.id, requestParam)
    }

    @PostMapping
    fun createSalePost(@Login memberAuth: MemberAuth, @Valid @RequestBody request: CreateSalePostRequest) {
        salePostService.createSalePost(memberAuth.id, request)
    }

    @PutMapping("/{id}")
    fun updateSalePost(@Login memberAuth: MemberAuth, @PathVariable id: Long, @Valid @RequestBody request: UpdateSalePostRequest) {
        salePostService.updateSalePost(memberAuth.id, id, request)
    }

    @DeleteMapping("/{id}")
    fun deleteSalePost(@Login memberAuth: MemberAuth, @PathVariable id: Long) {
        salePostService.deleteSalePost(memberAuth.id, id)
    }
}

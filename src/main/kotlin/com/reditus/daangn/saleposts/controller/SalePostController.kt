package com.reditus.daangn.saleposts.controller

import com.reditus.daangn.core.controller.dto.PagingResponse
import com.reditus.daangn.core.controller.interceptor.annotation.Login
import com.reditus.daangn.core.jwt.MemberAuth
import com.reditus.daangn.saleposts.controller.dto.request.CreateSalePostRequest
import com.reditus.daangn.saleposts.controller.dto.request.PagingSalePostsParams
import com.reditus.daangn.saleposts.controller.dto.request.UpdateSalePostRequest
import com.reditus.daangn.saleposts.controller.dto.response.SalePostDetailDto
import com.reditus.daangn.saleposts.controller.dto.response.SalePostDetailResponse
import com.reditus.daangn.saleposts.controller.dto.response.SalePostDto
import com.reditus.daangn.saleposts.domain.SalePostCategory
import com.reditus.daangn.saleposts.service.SalePostService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/saleposts")
class SalePostController(
    private val salePostService: SalePostService
) {
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun pagingSalePost(
        @Login memberAuth: MemberAuth,
        requestParam : PagingSalePostsParams
    ) : PagingResponse<SalePostDto> {
        return salePostService.pagingSalePost(memberAuth.id, requestParam)
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getSalePostDetail(@PathVariable id: Long): SalePostDetailResponse {
        val dto = salePostService.getSalePostDetail(id)
        return SalePostDetailResponse(data = dto)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createSalePost(@Login memberAuth: MemberAuth, @Valid @RequestBody request: CreateSalePostRequest) : Long{
        return salePostService.createSalePost(memberAuth.id, request)
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun updateSalePost(@Login memberAuth: MemberAuth, @PathVariable id: Long, @Valid @RequestBody request: UpdateSalePostRequest) {
        salePostService.updateSalePost(memberAuth.id, id, request)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteSalePost(@Login memberAuth: MemberAuth, @PathVariable id: Long) {
        salePostService.deleteSalePost(memberAuth.id, id)
    }
}

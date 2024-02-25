package com.reditus.daangn.saleposts.controller

import com.reditus.daangn.core.controller.dto.PagingResponse
import com.reditus.daangn.core.controller.interceptor.annotation.Login
import com.reditus.daangn.core.jwt.MemberAuth
import com.reditus.daangn.saleposts.controller.dto.request.CreateSalePostRequest
import com.reditus.daangn.saleposts.controller.dto.request.PagingSalePostsParams
import com.reditus.daangn.saleposts.controller.dto.request.UpdateSalePostRequest
import com.reditus.daangn.saleposts.controller.dto.response.SalePostDetailResponse
import com.reditus.daangn.saleposts.controller.dto.response.SalePostDto
import com.reditus.daangn.saleposts.controller.dto.response.SearchKeywordResponse
import com.reditus.daangn.saleposts.service.SalePostQueryService
import com.reditus.daangn.saleposts.service.SalePostService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@Tag(name = "SalePost", description = "게시글")
@RestController
@RequestMapping("/api/v1/saleposts")
class SalePostController(
    private val salePostService: SalePostService,
    private val salePostQueryService: SalePostQueryService,
) {

    @Operation(
        summary = "게시글 목록 조회", description = "게시글 목록을 조회합니다.",
        responses = [
            ApiResponse(responseCode = "200", description = "게시글 목록 조회 성공"),
        ]
    )
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun pagingSalePost(
        @Login memberAuth: MemberAuth,
        requestParam : PagingSalePostsParams
    ) : PagingResponse<SalePostDto> {
        return salePostQueryService.pagingSalePost(memberAuth.id, requestParam)
    }

    @Operation(
        summary = "게시글 상세 조회", description = "게시글 상세 정보를 조회합니다.",
        responses = [
            ApiResponse(responseCode = "200", description = "게시글 상세 조회 성공"),
        ]
    )
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getSalePostDetail(@Login memberAuth: MemberAuth ,@PathVariable id: Long): SalePostDetailResponse {
        val dto = salePostQueryService.getSalePostDetail(id, memberAuth.id)
        return SalePostDetailResponse(data = dto)
    }

    @Operation(
        summary = "게시글 생성", description = "게시글을 생성합니다.",
        responses = [
            ApiResponse(responseCode = "201", description = "게시글 생성 성공"),
        ]
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createSalePost(@Login memberAuth: MemberAuth, @Valid @RequestBody request: CreateSalePostRequest) : Long{
        return salePostService.createSalePost(memberAuth.id, request)
    }

    @Operation(
        summary = "게시글  수정", description = "게시글 정보를 수정합니다.",
        responses = [
            ApiResponse(responseCode = "204", description = "게시글 수정 성공"),
        ]
    )
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun updateSalePost(@Login memberAuth: MemberAuth, @PathVariable id: Long, @Valid @RequestBody request: UpdateSalePostRequest) {
        salePostService.updateSalePost(memberAuth.id, id, request)
    }

    @Operation(
        summary = "게시글 삭제", description = "게시글을 삭제처리합니다.",
        responses = [
            ApiResponse(responseCode = "204", description = "게시글 삭제 성공"),
        ]
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteSalePost(@Login memberAuth: MemberAuth, @PathVariable id: Long) {
        salePostService.deleteSalePost(memberAuth.id, id)
    }

    @Operation(
        summary = "게시글 검색 기록조회", description = "최근 20개의 검색 기록을 조회합니다.",
        responses = [
            ApiResponse(responseCode = "200", description = "검색 기록 조회 성공"),
        ]
    )
    @GetMapping("/search-history")
    @ResponseStatus(HttpStatus.OK)
    fun getSearchHistory(@Login memberAuth: MemberAuth): SearchKeywordResponse {
        val keywords =  salePostQueryService.getSearchHistory(memberAuth.id)
        return SearchKeywordResponse(keywords = keywords)
    }
}

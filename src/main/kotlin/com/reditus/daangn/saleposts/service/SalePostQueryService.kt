package com.reditus.daangn.saleposts.service

import com.reditus.daangn.core.aop.TemporaryApi
import com.reditus.daangn.core.aop.ViewCount
import com.reditus.daangn.core.controller.dto.PagingResponse
import com.reditus.daangn.core.exception.ResourceNotFoundException
import com.reditus.daangn.saleposts.controller.dto.request.PagingSalePostsParams
import com.reditus.daangn.saleposts.controller.dto.response.SalePostDetailDto
import com.reditus.daangn.saleposts.controller.dto.response.SalePostDto
import com.reditus.daangn.saleposts.repository.SalePostImageRepository
import com.reditus.daangn.saleposts.repository.SalePostQueryRepository
import com.reditus.daangn.saleposts.repository.SalePostRedisRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class SalePostQueryService(
    private val salePostQueryRepository: SalePostQueryRepository,
    private val salePostImageRepository: SalePostImageRepository,
    private val salePostRedisRepository: SalePostRedisRepository
) {

    @TemporaryApi("섬네일 이미지 불러오기 N+1 문제 존재")
    fun pagingSalePost(id: Long, requestParam: PagingSalePostsParams) : PagingResponse<SalePostDto> {
        /**
         * 검색 키워드 존재시, Redis에 키워드 저장
         */
        if(requestParam.keyword !=null){
            salePostRedisRepository.saveSearchKeyword(id, requestParam.keyword)
        }

        val data = salePostQueryRepository.getPagingSalePost(
            pageable = PageRequest.of(requestParam.page, requestParam.size),
            category =  requestParam.category,
            detailAddress = requestParam.detailAddress,
            keyword = requestParam.keyword
        )
        val dto = data.map {
            val imageUrl = salePostImageRepository.findFirstBySalePostId(it.id!!)?.imageUrl
            SalePostDto.from(it, imageUrl)
        }.toList()
        return PagingResponse(hasNext = data.hasNext(), data = dto)
    }


    @ViewCount(key = "salepost:view:#postId", value = "#memberId")
    fun getSalePostDetail(postId: Long, memberId: Long): SalePostDetailDto {
        val post = salePostQueryRepository.findByIdWithMemberAndLocation(postId) ?: throw ResourceNotFoundException("SalePost", postId)
        val imageUrls = salePostImageRepository.findAllBySalePostId(postId).map { it.imageUrl }
        return SalePostDetailDto.from(post, imageUrls)
    }

    fun getSearchHistory(memberId: Long): Set<String> {
        return salePostRedisRepository.getSearchKeywords(memberId)
    }
}
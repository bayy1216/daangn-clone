package com.reditus.daangn.saleposts.service

import com.reditus.daangn.core.controller.dto.PagingResponse
import com.reditus.daangn.core.exception.ResourceNotFoundException
import com.reditus.daangn.core.aop.TemporaryApi
import com.reditus.daangn.core.utils.findByIdOrThrow
import com.reditus.daangn.image.ImageService
import com.reditus.daangn.location.service.LocationService
import com.reditus.daangn.member.repository.MemberRepository
import com.reditus.daangn.saleposts.controller.dto.request.CreateSalePostRequest
import com.reditus.daangn.saleposts.controller.dto.request.PagingSalePostsParams
import com.reditus.daangn.saleposts.controller.dto.request.UpdateSalePostRequest
import com.reditus.daangn.saleposts.controller.dto.response.SalePostDetailDto
import com.reditus.daangn.saleposts.controller.dto.response.SalePostDetailResponse
import com.reditus.daangn.saleposts.controller.dto.response.SalePostDto
import com.reditus.daangn.saleposts.entity.SalePost
import com.reditus.daangn.saleposts.entity.SalePostImage
import com.reditus.daangn.saleposts.repository.SalePostImageRepository
import com.reditus.daangn.saleposts.repository.SalePostQueryRepository
import com.reditus.daangn.saleposts.repository.SalePostRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class SalePostService(
    private val memberRepository: MemberRepository,
    private val salePostRepository: SalePostRepository,
    private val salePostQueryRepository: SalePostQueryRepository,
    private val salePostImageRepository: SalePostImageRepository,
    private val locationService: LocationService,
    private val imageService: ImageService,
) {
    @Transactional
    fun createSalePost(memberId: Long, request: CreateSalePostRequest) : Long{
        val member = memberRepository.findById(memberId).orElseThrow{
            throw ResourceNotFoundException("Member", memberId)
        }
        val location = locationService.getLocation(request.longitude, request.latitude)
        val command = request.toCommand(member, location)
        val post = SalePost.create(command)

        val imageUrls = imageService.uploadImage(memberId, request.imageIds)
        val salePostImages = imageUrls.map { url ->
            SalePostImage(
                salePost = post,
                imageUrl = url
            )
        }

        salePostImageRepository.saveAll(salePostImages)
        salePostRepository.save(post)

        return post.id!!
    }

    @TemporaryApi("섬네일 이미지 불러오기 N+1 문제 존재")
    fun pagingSalePost(id: Long, requestParam: PagingSalePostsParams) :PagingResponse<SalePostDto>{
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

    @Transactional
    fun updateSalePost(memberId: Long, postId: Long, request: UpdateSalePostRequest) {
        val post = salePostRepository.findByIdOrThrow(postId)
        if(post.member.id != memberId){
            throw IllegalArgumentException("게시글 작성자만 수정할 수 있습니다.")
        }
        val location = locationService.getLocation(request.longitude, request.latitude)
        val command = request.toCommand(location)

        post.update(command)
    }

    @Transactional
    fun deleteSalePost(memberId: Long, postId: Long) {
        val post = salePostRepository.findByIdOrThrow(postId)
        if(post.member.id != memberId){
            throw IllegalArgumentException("게시글 작성자만 삭제할 수 있습니다.")
        }

        post.delete()
    }

    @Transactional
    fun getSalePostDetail(postId: Long): SalePostDetailDto {
        val post = salePostQueryRepository.findByIdWithMemberAndLocation(postId) ?: throw ResourceNotFoundException("SalePost", postId)
        val imageUrls = salePostImageRepository.findAllBySalePostId(postId).map { it.imageUrl }

        post.increaseViewCount()
        return SalePostDetailDto.from(post, imageUrls)
    }
}
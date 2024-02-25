package com.reditus.daangn.saleposts.service

import com.reditus.daangn.core.controller.dto.PagingResponse
import com.reditus.daangn.core.exception.ResourceNotFoundException
import com.reditus.daangn.core.aop.TemporaryApi
import com.reditus.daangn.core.aop.ViewCount
import com.reditus.daangn.core.utils.findByIdOrThrow
import com.reditus.daangn.image.ImageService
import com.reditus.daangn.location.service.LocationService
import com.reditus.daangn.member.repository.MemberRepository
import com.reditus.daangn.saleposts.controller.dto.request.CreateSalePostRequest
import com.reditus.daangn.saleposts.controller.dto.request.PagingSalePostsParams
import com.reditus.daangn.saleposts.controller.dto.request.UpdateSalePostRequest
import com.reditus.daangn.saleposts.controller.dto.response.SalePostDetailDto
import com.reditus.daangn.saleposts.controller.dto.response.SalePostDto
import com.reditus.daangn.saleposts.entity.SalePost
import com.reditus.daangn.saleposts.entity.SalePostImage
import com.reditus.daangn.saleposts.repository.SalePostImageRepository
import com.reditus.daangn.saleposts.repository.SalePostQueryRepository
import com.reditus.daangn.saleposts.repository.SalePostRepository
import com.reditus.daangn.saleposts.repository.SalePostRedisRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class SalePostService(
    private val memberRepository: MemberRepository,
    private val salePostRepository: SalePostRepository,
    private val salePostImageRepository: SalePostImageRepository,
    private val locationService: LocationService,
    private val imageService: ImageService,
) {
    @Transactional
    fun createSalePost(memberId: Long, request: CreateSalePostRequest) : Long{
        val member = memberRepository.findById(memberId).orElseThrow{
            throw ResourceNotFoundException("Member", memberId)
        }
        // 위도, 경도로 가져온 위치를 location에서 검색
        val location = locationService.getLocation(request.longitude, request.latitude)

        
        val command = request.toCommand(member, location)
        val post = SalePost.create(command)

        /**
         * 임시 저장된 이미지를 영구저장소에 업로드
         * 이미지 업로드 후 SalePostImage 엔티티 생성
         */
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


}
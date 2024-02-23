package com.reditus.daangn.saleposts.service

import com.reditus.daangn.core.controller.dto.PagingResponse
import com.reditus.daangn.core.exception.ResourceNotFoundException
import com.reditus.daangn.core.utils.TemporaryApi
import com.reditus.daangn.location.service.LocationService
import com.reditus.daangn.member.repository.MemberRepository
import com.reditus.daangn.saleposts.controller.dto.request.CreateSalePostRequest
import com.reditus.daangn.saleposts.controller.dto.request.PagingSalePostsParams
import com.reditus.daangn.saleposts.controller.dto.response.SalePostDto
import com.reditus.daangn.saleposts.entity.SalePost
import com.reditus.daangn.saleposts.repository.SalePostRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class SalePostService(
    private val memberRepository: MemberRepository,
    private val salePostRepository: SalePostRepository,
    private val locationService: LocationService,
) {
    @Transactional
    fun createSalePost(id: Long, request: CreateSalePostRequest) : Long{
        val member = memberRepository.findById(id).orElseThrow{
            throw ResourceNotFoundException("Member", id)
        }
        val location = locationService.getLocation(request.longitude, request.latitude)
        val command = request.toCommand(member, location)
        val post = SalePost.create(command)

        // TODO imageService.uploadImages(request.images)
        salePostRepository.save(post)

        return post.id!!
    }

    @TemporaryApi
    fun pagingSalePost(id: Long, requestParam: PagingSalePostsParams) :PagingResponse<SalePostDto>{
        val data = salePostRepository.findAllByOrderById(PageRequest.of(requestParam.page, requestParam.size))
        val dto = data.map { SalePostDto.from(it) }.toList()
        return PagingResponse(hasNext = data.hasNext(), data = dto)
    }
}
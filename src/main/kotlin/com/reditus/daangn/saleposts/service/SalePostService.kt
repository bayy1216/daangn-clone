package com.reditus.daangn.saleposts.service

import com.reditus.daangn.core.exception.ResourceNotFoundException
import com.reditus.daangn.location.service.LocationService
import com.reditus.daangn.member.repository.MemberRepository
import com.reditus.daangn.saleposts.controller.dto.request.CreateSalePostRequest
import com.reditus.daangn.saleposts.entity.SalePost
import com.reditus.daangn.saleposts.repository.SalePostRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
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
}
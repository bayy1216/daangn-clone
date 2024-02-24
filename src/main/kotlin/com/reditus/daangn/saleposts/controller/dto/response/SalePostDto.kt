package com.reditus.daangn.saleposts.controller.dto.response

import com.reditus.daangn.saleposts.domain.SalePostCategory
import com.reditus.daangn.saleposts.domain.SalePostStatus
import com.reditus.daangn.saleposts.entity.SalePost
import java.time.LocalDateTime


data class SalePostDto(
    val id: Long,
    val thumbnailUrl : String?,
    val title: String,
    val price: Int,
    val detailAddress: String,
    val category: SalePostCategory,
    val count: CountInfoDto,
    val status: SalePostStatus,
    val createdDate: LocalDateTime,
){
    companion object {
        fun from(salePost: SalePost, thumbnailUrl: String?): SalePostDto {
            return SalePostDto(
                id = salePost.id!!,
                thumbnailUrl = thumbnailUrl,
                title = salePost.title,
                price = salePost.price,
                detailAddress = salePost.location.detailAddress,
                category = salePost.category,
                count = CountInfoDto(
                    viewCount = salePost.countInfo.viewCount,
                    interestCount = salePost.countInfo.interestCount,
                    chatCount = salePost.countInfo.chatCount
                ),
                status = salePost.status,
                createdDate = salePost.createdAt!!
            )
        }
    }
}


data class CountInfoDto(
    val viewCount: Int,
    val interestCount: Int,
    val chatCount: Int
)

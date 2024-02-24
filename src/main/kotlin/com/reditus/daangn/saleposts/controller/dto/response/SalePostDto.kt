package com.reditus.daangn.saleposts.controller.dto.response

import com.reditus.daangn.member.entity.Member
import com.reditus.daangn.saleposts.domain.SalePostCategory
import com.reditus.daangn.saleposts.domain.SalePostStatus
import com.reditus.daangn.saleposts.entity.SalePost
import java.time.LocalDateTime


data class SalePostDto(
    val id: Long,
    val writer: WriterDto,
    val imageUrl : String?,
    val title: String,
    val price: Int,
    val description: String,
    val locationInfo: LocationInfoDto,
    val category: SalePostCategory,
    val count: CountInfoDto,
    val status: SalePostStatus,
    val createdDate: LocalDateTime,
){
    companion object {
        fun from(salePost: SalePost, imageUrls: String?): SalePostDto {
            return SalePostDto(
                id = salePost.id!!,
                writer = WriterDto.from(salePost.member),
                imageUrl = imageUrls,
                title = salePost.title,
                price = salePost.price,
                description = salePost.description,
                locationInfo = LocationInfoDto(
                    latitude = salePost.locationInfo.latitude,
                    longitude = salePost.locationInfo.longitude,
                    locationName = salePost.locationInfo.locationName
                ),
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


data class WriterDto(
    val id: Long,
    val nickname: String?,
    val profileImageUrl: String?,
){
    companion object {
        fun from(member: Member): WriterDto {
            return WriterDto(
                id = member.id!!,
                nickname = member.nickname,
                profileImageUrl = member.profileImageUrl
            )
        }
    }
}

data class LocationInfoDto(
    val latitude: Double,
    val longitude: Double,
    val locationName: String
)

data class CountInfoDto(
    val viewCount: Int,
    val interestCount: Int,
    val chatCount: Int
)

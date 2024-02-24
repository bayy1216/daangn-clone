package com.reditus.daangn.saleposts.controller.dto.response

import com.reditus.daangn.member.entity.Member
import com.reditus.daangn.saleposts.domain.SalePostCategory
import com.reditus.daangn.saleposts.domain.SalePostStatus
import com.reditus.daangn.saleposts.entity.SalePost
import java.time.LocalDateTime

data class SalePostDetailResponse(
    val data: SalePostDetailDto,
)

data class SalePostDetailDto(
    val id: Long,
    val thumbnailUrl : String?,
    val title: String,
    val price: Int,
    val detailAddress: String,
    val category: SalePostCategory,
    val count: CountInfoDto,
    val status: SalePostStatus,
    val createdDate: LocalDateTime,

    val description: String,
    val locationInfo: LocationInfoDto,
    val writer: WriterDto,
    val imageUrls: List<String>,
){
    companion object {
        fun from(salePost: SalePost, imageUrls: List<String>): SalePostDetailDto {
            return SalePostDetailDto(
                id = salePost.id!!,
                thumbnailUrl = imageUrls.firstOrNull(),
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
                createdDate = salePost.createdAt!!,

                description = salePost.description,
                locationInfo = LocationInfoDto(
                    latitude = salePost.locationInfo.latitude,
                    longitude = salePost.locationInfo.longitude,
                    locationName = salePost.locationInfo.locationName
                ),
                writer = WriterDto.from(salePost.member),
                imageUrls = imageUrls
            )
        }
    }
}

data class LocationInfoDto(
    val latitude: Double,
    val longitude: Double,
    val locationName: String
)

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
package com.reditus.daangn.saleposts.controller.dto.request

import com.reditus.daangn.location.entity.Location
import com.reditus.daangn.member.entity.Member
import com.reditus.daangn.saleposts.domain.SalePostCategory
import com.reditus.daangn.saleposts.entity.LocationInfo
import com.reditus.daangn.saleposts.entity.SalePostCreateCommand
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class CreateSalePostRequest(
    @field: NotBlank(message = "제목을 입력해주세요.")
    val title: String,
    @field: NotBlank(message = "설명을 입력해주세요.")
    val description: String,
    @field: Min(0, message = "가격은 0원 이상이어야 합니다.")
    val price: Int,
    val category: SalePostCategory,
    @field: Min(-90, message = "위도는 -90 이상이어야 합니다.")
    val latitude: Double,
    @field: Min(-180, message = "경도는 -180 이상이어야 합니다.")
    val longitude: Double,
    @field: NotBlank(message = "위치를 입력해주세요.")
    val locationName: String,
    @field: NotNull(message = "이미지를 입력해주세요.")
    val imageIds: List<String>
){
    fun toCommand(): SalePostCreateCommand {
        return SalePostCreateCommand(
            title = title,
            description = description,
            price = price,
            category = category,
            locationInfo = LocationInfo(
                latitude = latitude,
                longitude = longitude,
                locationName = locationName
            )
        )
    }
}
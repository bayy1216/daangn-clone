package com.reditus.daangn.location.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Location(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    var baseAddress: String,
    var detailAddress: String,
    var subAddress: String,
) {
    companion object {
        fun fixture(
            id: Long? = null,
            baseAddress: String = "대구광역시",
            detailAddress: String = "복현동",
            subAddress: String = "복현1동",
        ) = Location(
            id = id,
            baseAddress = baseAddress,
            detailAddress = detailAddress,
            subAddress = subAddress,
        )
    }
}
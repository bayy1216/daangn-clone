package com.reditus.daangn.location.entity

import jakarta.persistence.*

@Entity
class LocationPath(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @ManyToOne
    @JoinColumn(name = "location_id")
    val location: Location,
    var longitude: String,
    var latitude: String,
) {
    companion object {
        fun fixture(
            id: Long? = null,
            location: Location = Location.fixture(),
            longitude: String = "34.1234",
            latitude: String = "126.1234",
        ) = LocationPath(
            id = id,
            location = location,
            longitude = longitude,
            latitude = latitude,
        )
    }
}
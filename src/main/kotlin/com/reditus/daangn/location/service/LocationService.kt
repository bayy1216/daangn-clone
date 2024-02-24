package com.reditus.daangn.location.service

import com.reditus.daangn.core.aop.TemporaryApi
import com.reditus.daangn.core.utils.findByIdOrThrow
import com.reditus.daangn.location.entity.Location
import com.reditus.daangn.location.repository.LocationRepository
import org.springframework.stereotype.Service

@Service
class LocationService(
    private val locationRepository: LocationRepository
) {
    @TemporaryApi
    fun getLocation(
        longitude: Double,
        latitude: Double
    ) : Location {
        return locationRepository.findByIdOrThrow(1L)
    }
}
package com.reditus.daangn.location.repository

import com.reditus.daangn.location.entity.LocationPath
import org.springframework.data.jpa.repository.JpaRepository

interface LocationPathRepository : JpaRepository<LocationPath, Long> {
}
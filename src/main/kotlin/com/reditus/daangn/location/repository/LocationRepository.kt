package com.reditus.daangn.location.repository

import com.reditus.daangn.location.entity.Location
import org.springframework.data.jpa.repository.JpaRepository

interface LocationRepository : JpaRepository<Location, Long> {
}

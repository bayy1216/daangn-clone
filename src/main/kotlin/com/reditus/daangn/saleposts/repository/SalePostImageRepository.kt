package com.reditus.daangn.saleposts.repository

import com.reditus.daangn.saleposts.entity.SalePostImage
import org.springframework.data.jpa.repository.JpaRepository

interface SalePostImageRepository : JpaRepository<SalePostImage, Long> {
}
package com.reditus.daangn.saleposts.repository

import com.reditus.daangn.saleposts.entity.SalePost
import org.springframework.data.jpa.repository.JpaRepository

interface SalePostRepository: JpaRepository<SalePost, Long> {
}
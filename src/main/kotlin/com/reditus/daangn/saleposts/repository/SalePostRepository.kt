package com.reditus.daangn.saleposts.repository

import com.reditus.daangn.saleposts.entity.SalePost
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface SalePostRepository: JpaRepository<SalePost, Long> {

    fun findAllByOrderById(pageable: Pageable): Page<SalePost>
}
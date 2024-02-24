package com.reditus.daangn.saleposts.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import com.reditus.daangn.saleposts.domain.SalePostCategory
import com.reditus.daangn.saleposts.domain.SalePostStatus
import com.reditus.daangn.saleposts.entity.QSalePost
import com.reditus.daangn.saleposts.entity.SalePost
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository
class SalePostQueryRepository(
    private val jpaQueryFactory: JPAQueryFactory,
) {


    /**
     * 판매글 페이징 조회 : [Member]와 Fetch Join으로 Member와의 N+1 문제 해결
     */
    fun getPagingSalePost(pageable: Pageable, category: SalePostCategory?, detailAddress: String?, keyword: String?):Page<SalePost> {
        val whereCondition = categoryEq(category)
            ?.and(locationEq(detailAddress))
            ?.and(keywordContains(keyword))
            ?.and(
                QSalePost.salePost.status.eq(SalePostStatus.ACTIVE)
                    .or(QSalePost.salePost.status.eq(SalePostStatus.IN_PROGRESS)
                    .or(QSalePost.salePost.status.eq(SalePostStatus.SOLD)))
            )

        val totalCount = jpaQueryFactory
            .select(QSalePost.salePost.count())
            .from(QSalePost.salePost)
            .where(whereCondition)
            .fetchOne()

        val data = jpaQueryFactory
            .select(QSalePost.salePost)
            .from(QSalePost.salePost)
            .join(QSalePost.salePost.member).fetchJoin()
            .join(QSalePost.salePost.location).fetchJoin()
            .where(whereCondition)
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()
        return PageImpl(data, pageable, totalCount?:0L)
    }


    fun categoryEq(category: SalePostCategory?) = category?.let {
        QSalePost.salePost.category.eq(category)
    }

    fun locationEq(detailAddress: String?) = detailAddress?.let {
        QSalePost.salePost.location.detailAddress.eq(detailAddress)
    }

    fun keywordContains(keyword: String?) = keyword?.let {
        QSalePost.salePost.title.contains(keyword)
    }
}
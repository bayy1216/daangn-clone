package com.reditus.daangn.saleposts.controller.dto.request

import com.reditus.daangn.saleposts.domain.SalePostCategory

data class PagingSalePostsParams(
    val page: Int,
    val size: Int = 20,
    val category: SalePostCategory?,
    val detailAddress: String?,
    val keyword: String?,
    val state: String?
)

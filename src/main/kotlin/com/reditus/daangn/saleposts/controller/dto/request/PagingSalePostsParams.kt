package com.reditus.daangn.saleposts.controller.dto.request

data class PagingSalePostsParams(
    val page: Int,
    val size: Int = 20,
    val category: String?,
    val location: String?,
    val keyword: String?
)

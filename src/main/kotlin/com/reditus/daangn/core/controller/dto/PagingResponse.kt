package com.reditus.daangn.core.controller.dto

data class PagingResponse<T>(
    val hasNext: Boolean,
    val data : List<T>
)

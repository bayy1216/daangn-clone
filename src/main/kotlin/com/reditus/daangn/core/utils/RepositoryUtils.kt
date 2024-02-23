package com.reditus.daangn.core.utils

import com.reditus.daangn.core.exception.ResourceNotFoundException
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.findByIdOrNull

inline fun <reified T,ID> CrudRepository<T,ID>.findByIdOrThrow(id: ID) : T {
    return this.findByIdOrNull(id) ?: throw ResourceNotFoundException(T::class.simpleName ?: "Resource", id.toString())
}
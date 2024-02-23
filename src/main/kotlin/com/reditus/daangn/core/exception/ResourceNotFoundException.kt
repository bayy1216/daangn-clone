package com.reditus.daangn.core.exception

class ResourceNotFoundException(
    resourceName: String,
    resourceId: Any,
) : RuntimeException(
    "${resourceName}에서 ${resourceId}를 찾을 수 없습니다."
)
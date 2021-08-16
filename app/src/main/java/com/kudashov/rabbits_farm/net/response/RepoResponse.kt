package com.kudashov.rabbits_farm.net.response

data class RepoResponse<T>(
    val content: T?,
    val detail: String?
)
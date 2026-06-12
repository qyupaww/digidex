package com.qyupaww.jetpackcomposedigidex.data.remote.responses

data class Pageable(
    val currentPage: Int,
    val elementsOnPage: Int,
    val nextPage: String,
    val previousPage: String,
    val totalElements: Int,
    val totalPages: Int
)
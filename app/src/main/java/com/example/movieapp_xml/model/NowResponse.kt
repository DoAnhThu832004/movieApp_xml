package com.example.movieapp_xml.model

data class NowResponse<T>(
    val dates: Dates,
    val page: Int,
    val results: List<T>,
    val total_pages: Int,
    val total_results: Int
)
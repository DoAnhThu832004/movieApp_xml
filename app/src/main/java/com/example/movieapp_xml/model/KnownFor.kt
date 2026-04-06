package com.example.movieapp_xml.model

data class KnownFor(
    val adult: Boolean,
    val backdrop_path: String,
    val id: Int,
    val name: String,
    val original_name: String,
    val overview: String,
    val poster_path: String,
    val media_type: String,
    val original_language: String,
    val genre_ids: List<Int>,
    val popularity: Double,
    val first_air_date: String,
    val vote_average: Double,
    val vote_count: Int,
    val origin_country: List<String>
)

package com.example.movieapp_xml.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("trending/movie/day")
    suspend fun getTrendingMovies(
        @Query("api_key") apiKey: String
    ): ApiResponse<Trending>
    @GET("movie/now_playing")
    suspend fun getNowPlaying(
        @Query("api_key") apiKey: String
    ): NowResponse<NowPlaying>
    @GET("movie/popular")
    suspend fun getPopular(
        @Query("api_key") apiKey: String
    ): ApiResponse<Popular>
    @GET("movie/top_rated")
    suspend fun getTopRated(
        @Query("api_key") apiKey: String
    ): ApiResponse<TopRated>
    @GET("movie/upcoming")
    suspend fun getUpcoming(
        @Query("api_key") apiKey: String
    ): NowResponse<UpComing>
}
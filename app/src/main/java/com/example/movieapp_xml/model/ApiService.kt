package com.example.movieapp_xml.model

import com.example.movieapp_xml.model.movie.Movie
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import kotlin.collections.Collection

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
    @GET("collection/{collectionId}")
    suspend fun getCollection(
        @Path("collectionId") collectionId: Int,
        @Query("api_key") apiKey: String
    ): com.example.movieapp_xml.model.Collection
    @GET("person/popular")
    suspend fun getPerson(
        @Query("api_key") apiKey: String
    ): ApiResponse<Person>
    @GET("person/{person_id}")
    suspend fun getPersonDetail(
        @Path("person_id") personId: Int,
        @Query("api_key") apiKey: String
    ): PersonDetail
    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Movie
}
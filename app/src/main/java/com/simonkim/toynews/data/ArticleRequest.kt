package com.simonkim.toynews.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ArticleRequest {
    @GET("v2/everything/")
    fun getNewsResponse(
        @Query("q") query: String,
        @Query("apiKey") apiKey: String
    ): Call<ArticleResponse>

    @GET("v2/top-headlines/")
    fun getHeadlines(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String
    ): Call<ArticleResponse>
}
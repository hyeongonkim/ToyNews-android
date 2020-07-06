package com.simonkim.toynews.data

data class ArticleResponse(
    val status: String?,
    val totalResults: Int?,
    val articles: List<Article>?
)
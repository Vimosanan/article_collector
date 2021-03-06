package com.vimosanan.articlecollectorapplication.service

import com.vimosanan.articlecollectorapplication.service.model.Article
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {
    @GET("/article")
    suspend fun getAllArticles(
    ): Response<List<Article>>

    @GET("/article/{id}")
    suspend fun getArticleDetail(
        @Path("id") id: Int
    ): Response<Article>
}
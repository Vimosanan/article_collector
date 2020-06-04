package com.vimosanan.articlecollectorapplication.service.repository.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vimosanan.articlecollectorapplication.service.model.Article

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(articles: List<Article>)

    @Query("SELECT * from articles")
    suspend fun getAllArticles(): List<Article>

    @Query("UPDATE articles SET title = :title, description = :description WHERE id = :id")
    suspend fun updateArticle(id: Int, title: String, description: String)

    @Query("SELECT * from articles WHERE id = :id")
    suspend fun getArticle(id: Int): List<Article>
}
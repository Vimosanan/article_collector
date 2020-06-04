package com.vimosanan.articlecollectorapplication.service.repository.local

import com.vimosanan.articlecollectorapplication.service.model.Article
import com.vimosanan.articlecollectorapplication.util.Result
import javax.inject.Inject

class LocalRepository @Inject constructor(private val db: AppDatabase) {

    suspend fun loadArticlesFormLocalDatabase(): Result<List<Article>> {
        val article = db.articleDao().getAllArticles()

        return if(article.isEmpty()) {
            Result.Error(Exception("No such item in local Database..."))
        } else {
            Result.Success(article)
        }
    }

    suspend fun uploadAllArticles(articles: List<Article>) {
        return db.articleDao().insertAll(articles)
    }

    suspend fun updateArticle(article: Article) {
        return db.articleDao().updateArticle(article.id, article.title!!, article.description!!)
    }

    suspend fun getArticle(id: Int): Result<Article> {
        val article = db.articleDao().getArticle(id)

        return if(article.isEmpty()) {
            Result.Error(Exception("No such item in local Database..."))
        } else {
            Result.Success(article[0])
        }
    }
}
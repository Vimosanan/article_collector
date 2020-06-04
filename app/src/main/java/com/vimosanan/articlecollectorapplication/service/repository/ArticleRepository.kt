package com.vimosanan.articlecollectorapplication.service.repository

import com.vimosanan.articlecollectorapplication.app.IS_CONNECTED
import com.vimosanan.articlecollectorapplication.service.model.Article
import com.vimosanan.articlecollectorapplication.service.repository.local.LocalRepository
import com.vimosanan.articlecollectorapplication.service.repository.remote.RemoteRepository
import com.vimosanan.articlecollectorapplication.util.Result
import kotlinx.coroutines.delay
import javax.inject.Inject

class ArticleRepository @Inject constructor(private val remoteRepo: RemoteRepository, private val localRepo: LocalRepository) {

    suspend fun loadAllArticlesFromRemote(): Result<List<Article>>{
        return if(IS_CONNECTED) {
            val result = remoteRepo.loadArticlesFromRemote()

            when (result)  {
                is Result.Success -> {
                    localRepo.uploadAllArticles(result.data)
                }
            }
            result
        } else {
            localRepo.loadArticlesFormLocalDatabase()
        }
    }

    suspend fun loadDescriptionFromRemote(id: Int): Result<Article>{
        return if(IS_CONNECTED) {
            remoteRepo.loadDescriptionFromRemote(id)
        } else {
            localRepo.getArticle(id)
        }
    }

    suspend fun updateArticle(article: Article): Result<Article> {
        delay(3_000)
        localRepo.updateArticle(article)
        return Result.Success(article)
    }
}

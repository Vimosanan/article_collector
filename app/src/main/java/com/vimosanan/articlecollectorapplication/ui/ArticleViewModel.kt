package com.vimosanan.articlecollectorapplication.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vimosanan.articlecollectorapplication.service.model.Article
import com.vimosanan.articlecollectorapplication.service.repository.ArticleRepository
import com.vimosanan.articlecollectorapplication.util.Result
import kotlinx.coroutines.*
import javax.inject.Inject

class ArticleViewModel @Inject constructor(private val repo: ArticleRepository) : ViewModel() {

    private val _articles = MutableLiveData<Result<List<Article>>>()
    val articles: LiveData<Result<List<Article>>>
        get() = _articles


    private val _article = MutableLiveData<Result<Article>>()
    val article: LiveData<Result<Article>>
        get() = _article

    fun loadAllArticles() {
        viewModelScope.launch {
            _articles.postValue(Result.Loading)

            delay(3_000) // purposefully delayed to show loader
            withContext(Dispatchers.IO) {
                _articles.postValue(repo.loadAllArticlesFromRemote())
            }
        }
    }

    fun loadDescriptionData(id: Int) {
        viewModelScope.launch {
            viewModelScope.launch {
                _article.postValue(Result.Loading)

                delay(3_000) // purposefully delayed to show loader
                withContext(Dispatchers.IO) {
                    _article.postValue(repo.loadDescriptionFromRemote(id))
                }
            }
        }
    }

    fun saveArticleToDatabase(article: Article) {
        CoroutineScope(Dispatchers.IO).launch {
            _article.postValue(repo.updateArticle(article))
        }
    }


}
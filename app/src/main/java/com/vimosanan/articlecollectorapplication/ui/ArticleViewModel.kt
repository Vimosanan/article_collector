package com.vimosanan.articlecollectorapplication.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vimosanan.articlecollectorapplication.service.model.Article
import com.vimosanan.articlecollectorapplication.service.repository.ArticleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.vimosanan.articlecollectorapplication.util.Result
import javax.inject.Inject

class ArticleViewModel @Inject constructor(private val repo: ArticleRepository): ViewModel() {

    private val _articles = MutableLiveData<Result<List<Article>>>()
    val articles: LiveData<Result<List<Article>>>
        get() = _articles


    private val _article = MutableLiveData<Result<Article>>()
    val article: LiveData<Result<Article>>
        get() = _article

    fun loadAllArticles() {
        viewModelScope.launch {
            _articles.postValue(Result.Loading)
            withContext(Dispatchers.IO) {
                _articles.postValue(repo.loadAllArticlesFromRemote())
            }
        }
    }

    fun loadDescriptionData(id: Int) {
        viewModelScope.launch {
            viewModelScope.launch {
                _article.postValue(Result.Loading)
                withContext(Dispatchers.IO) {
                    _article.postValue(repo.loadDescriptionFromRemote(id))
                }
            }
        }
    }
}
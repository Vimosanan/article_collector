package com.vimosanan.articlecollectorapplication.ui

import androidx.lifecycle.ViewModel
import com.vimosanan.articlecollectorapplication.service.repository.ArticleRepository
import javax.inject.Inject

class ArticleViewModel @Inject constructor(private val repo: ArticleRepository): ViewModel() {

}
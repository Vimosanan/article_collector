package com.vimosanan.articlecollectorapplication.ui.edit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.vimosanan.articlecollectorapplication.app.ArticleCollectionApplication
import com.vimosanan.articlecollectorapplication.databinding.ActivityDetailBinding
import com.vimosanan.articlecollectorapplication.databinding.ActivityEditArticleBinding
import com.vimosanan.articlecollectorapplication.ui.ArticleViewModel
import javax.inject.Inject

class EditArticleActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: ActivityEditArticleBinding
    private lateinit var articleViewModel: ArticleViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        val appComponent = (applicationContext as ArticleCollectionApplication).appComponent
        appComponent.inject(this)

        super.onCreate(savedInstanceState)
        binding = ActivityEditArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // register for view-model
        articleViewModel = ViewModelProvider(
            this,
            viewModelFactory
        ).get(ArticleViewModel::class.java)

        initObservers()
    }


    private fun initObservers() {

    }
}
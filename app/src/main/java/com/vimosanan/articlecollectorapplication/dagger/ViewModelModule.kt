package com.vimosanan.articlecollectorapplication.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vimosanan.articlecollectorapplication.ui.ArticleViewModel
import com.vimosanan.articlecollectorapplication.viewmodel.AppViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @IntoMap
    @Binds
    @ViewModelKey(ArticleViewModel::class)
    abstract fun bindMovieViewModel(movieViewModel: ArticleViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: AppViewModelFactory): ViewModelProvider.Factory
}
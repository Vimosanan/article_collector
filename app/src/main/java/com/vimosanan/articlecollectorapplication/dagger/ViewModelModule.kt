package com.vimosanan.articlecollectorapplication.dagger

import androidx.lifecycle.ViewModelProvider
import com.vimosanan.articlecollectorapplication.viewmodel.AppViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: AppViewModelFactory): ViewModelProvider.Factory
}
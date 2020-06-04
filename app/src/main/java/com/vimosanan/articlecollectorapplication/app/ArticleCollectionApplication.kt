package com.vimosanan.articlecollectorapplication.app

import android.app.Application
import com.vimosanan.articlecollectorapplication.dagger.AppComponent
import com.vimosanan.articlecollectorapplication.dagger.DaggerAppComponent

class ArticleCollectionApplication: Application() {
    val appComponent: AppComponent =
        DaggerAppComponent.builder()
            .build()
}
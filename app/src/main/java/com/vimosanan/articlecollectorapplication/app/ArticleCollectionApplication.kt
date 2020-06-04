package com.vimosanan.articlecollectorapplication.app

import android.app.Application
import com.vimosanan.articlecollectorapplication.dagger.AppComponent
import com.vimosanan.articlecollectorapplication.dagger.AppModule
import com.vimosanan.articlecollectorapplication.dagger.DaggerAppComponent
import com.vimosanan.articlecollectorapplication.util.NetworkManager

class ArticleCollectionApplication: Application() {
    val appComponent: AppComponent =
        DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()

    override fun onCreate() {
        super.onCreate()

        NetworkManager.registerNetworkCallback(this)
    }
}
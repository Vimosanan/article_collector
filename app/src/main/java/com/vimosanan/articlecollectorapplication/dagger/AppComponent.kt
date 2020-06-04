package com.vimosanan.articlecollectorapplication.dagger

import com.vimosanan.articlecollectorapplication.ui.dashboard.DashboardActivity
import com.vimosanan.articlecollectorapplication.ui.detail.DetailActivity
import com.vimosanan.articlecollectorapplication.ui.edit.EditArticleActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class, ViewModelModule::class])
interface AppComponent {
    fun inject(dashboardActivity: DashboardActivity)
    fun inject(detailActivity: DetailActivity)
    fun inject(editArticleActivity: EditArticleActivity)
}
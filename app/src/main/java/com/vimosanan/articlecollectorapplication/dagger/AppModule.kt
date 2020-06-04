package com.vimosanan.articlecollectorapplication.dagger

import android.content.Context
import com.vimosanan.articlecollectorapplication.service.repository.local.AppDatabase
import dagger.Module
import dagger.Provides

@Module
class AppModule(private val context: Context) {

    @Provides
    fun provideContext(): Context {
        return context
    }

    @Provides
    fun providesDatabase(context: Context): AppDatabase {
        return AppDatabase
            .getInstance(context)
    }

}
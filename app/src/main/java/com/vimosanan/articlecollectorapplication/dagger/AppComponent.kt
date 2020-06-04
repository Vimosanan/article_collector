package com.vimosanan.articlecollectorapplication.dagger

import dagger.Component

@Component(modules = [AppModule::class, NetworkModule::class, ViewModelModule::class])
interface AppComponent {

}
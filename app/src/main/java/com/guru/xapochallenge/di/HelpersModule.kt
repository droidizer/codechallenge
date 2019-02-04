package com.guru.xapochallenge.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.guru.xapochallenge.GithubApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class HelpersModule {

    @Provides
    fun provideContext(app: GithubApp): Context = app.applicationContext

    @Provides
    fun provideResources(context: Context) = context.resources

    @Singleton
    @Provides
    fun provideGson(): Gson = GsonBuilder().create()
}
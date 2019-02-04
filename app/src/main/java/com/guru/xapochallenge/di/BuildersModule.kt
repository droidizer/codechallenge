package com.guru.xapochallenge.di

import com.guru.xapochallenge.ui.details.RepositoryDetailsActivity
import com.guru.xapochallenge.ui.details.di.RepositoryDetailsModule
import com.guru.xapochallenge.ui.repository.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuildersModule {

    @ContributesAndroidInjector
    abstract fun bindRepositoryActivity(): MainActivity

    @ContributesAndroidInjector(modules = arrayOf(RepositoryDetailsModule::class))
    abstract fun bindRepositoryDetailsActivity(): RepositoryDetailsActivity
}
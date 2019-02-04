package com.guru.xapochallenge.ui.details.di

import com.guru.xapochallenge.api.model.Repository
import com.guru.xapochallenge.ui.details.RepositoryDetailsActivity
import dagger.Module
import dagger.Provides

const val REPOSITORY_KEY = "REPOSITORY_KEY"

@Module
class RepositoryDetailsModule {

    @Provides
    fun provideRepository(repositoryDetailsActivity: RepositoryDetailsActivity) =
        repositoryDetailsActivity.intent.getParcelableExtra<Repository>(REPOSITORY_KEY)
}
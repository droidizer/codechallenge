package com.guru.xapochallenge.di

import com.guru.xapochallenge.api.GitHubDataRepository
import com.guru.xapochallenge.api.IGitHubDataRepository
import com.guru.xapochallenge.manager.IRepositoryManager
import com.guru.xapochallenge.manager.RepositoryManager
import dagger.Binds
import dagger.Module

@Module
abstract class BindsModule {

    @Binds
    abstract fun bindGitHubDataRepository(gitHubDataRepository: GitHubDataRepository): IGitHubDataRepository

    @Binds
    abstract fun bindRepositoryManager(repositoryManager: RepositoryManager): IRepositoryManager
}
package com.guru.xapochallenge.di

import com.guru.xapochallenge.GithubApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = arrayOf(
        AndroidSupportInjectionModule::class,
        BindsModule::class,
        BuildersModule::class,
        NetworkModule::class, HelpersModule::class
    )
)
interface GithubComponent {
    fun inject(app: GithubApp)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: GithubApp): Builder

        fun build(): GithubComponent
    }
}
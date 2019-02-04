package com.guru.xapochallenge

import android.app.Activity
import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import com.guru.xapochallenge.di.DaggerGithubComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

open class GithubApp : Application(), HasActivityInjector {

    @Inject
    lateinit var mAndroidDispatchingInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        DaggerGithubComponent
            .builder()
            .application(this)
            .build()
            .inject(this)
        Fresco.initialize(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> = mAndroidDispatchingInjector
}
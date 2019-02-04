package com.guru.xapochallenge.common

import android.os.Bundle
import com.blackbelt.bindings.activity.BaseBindingActivity
import dagger.android.AndroidInjection

abstract class BaseBindingInjectableActivity : BaseBindingActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }
}
package com.guru.xapochallenge.ui.details.viewmodel

import com.guru.xapochallenge.api.model.Owner

open class OwnerViewModel constructor(val owner: Owner) {

    fun getName() = owner.name

    fun getAvatarUrl() = owner.url
}
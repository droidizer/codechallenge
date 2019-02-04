package com.guru.xapochallenge.ui.details

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import com.blackbelt.bindings.recyclerviewbindings.AndroidBindableRecyclerView
import com.guru.xapochallenge.R
import com.guru.xapochallenge.common.BaseBindingInjectableActivity
import com.guru.xapochallenge.common.viewmodel.ProgressLoader
import com.guru.xapochallenge.ui.details.viewmodel.RepositoryDetailsViewModel
import javax.inject.Inject
import com.guru.xapochallenge.BR

class RepositoryDetailsActivity : BaseBindingInjectableActivity() {

    @Inject
    lateinit var mFactory: RepositoryDetailsViewModel.Factory

    private val mRepositoryViewModel by lazy {
        ViewModelProviders.of(this, mFactory).get(RepositoryDetailsViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repository_details, BR.repositoryDetailsViewModel, mRepositoryViewModel)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        val recyclerView: AndroidBindableRecyclerView = findViewById(R.id.subscribers_rv)
        val layoutManager = GridLayoutManager(this, 3)
        recyclerView.layoutManager = layoutManager
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val dataSet = recyclerView.dataSet ?: return 1
                if (dataSet[position] is ProgressLoader) {
                    return 3
                }
                return 1
            }
        }
    }

    fun getViewModel(): RepositoryDetailsViewModel = mRepositoryViewModel
}
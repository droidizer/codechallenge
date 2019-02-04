package com.guru.xapochallenge.ui.repository

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.inputmethod.InputMethodManager
import com.blackbelt.bindings.notifications.ClickItemWrapper
import com.guru.xapochallenge.BR
import com.guru.xapochallenge.R
import com.guru.xapochallenge.api.model.Repository
import com.guru.xapochallenge.common.BaseBindingInjectableActivity
import com.guru.xapochallenge.ui.details.RepositoryDetailsActivity
import com.guru.xapochallenge.ui.details.di.REPOSITORY_KEY
import com.guru.xapochallenge.ui.repository.viewmodel.RepositoryViewModel
import javax.inject.Inject

class MainActivity : BaseBindingInjectableActivity() {
    @Inject
    lateinit var mFactory: RepositoryViewModel.Factory

    private val mOnScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                val view = currentFocus
                if (view != null) {
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(view.windowToken, 0, null)
                }
            }
        }
    }

    private val mRepositoryViewModel by lazy {
        ViewModelProviders.of(this, mFactory).get(RepositoryViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main, BR.repositoryViewModel, mRepositoryViewModel)
        findViewById<RecyclerView>(R.id.repository_rv).addOnScrollListener(mOnScrollListener)
    }

    override fun onItemClicked(itemWrapper: ClickItemWrapper) {
        val repository = itemWrapper.additionalData as? Repository ?: return
        val intent = Intent(this, RepositoryDetailsActivity::class.java)
        intent.putExtra(REPOSITORY_KEY, repository)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        findViewById<RecyclerView>(R.id.repository_rv).removeOnScrollListener(mOnScrollListener)
    }
}

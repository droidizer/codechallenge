package com.guru.xapochallenge.ui.repository.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.content.res.Resources
import android.databinding.Bindable
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View
import com.blackbelt.bindings.notifications.ClickItemWrapper
import com.blackbelt.bindings.notifications.MessageWrapper
import com.blackbelt.bindings.recyclerviewbindings.AndroidItemBinder
import com.blackbelt.bindings.recyclerviewbindings.ItemClickListener
import com.blackbelt.bindings.recyclerviewbindings.PageDescriptor
import com.guru.xapochallenge.BR
import com.guru.xapochallenge.R
import com.guru.xapochallenge.common.viewmodel.LoadingIndicatorViewModel
import com.guru.xapochallenge.common.viewmodel.ProgressLoader
import com.guru.xapochallenge.manager.IRepositoryManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposables
import javax.inject.Inject


open class RepositoryViewModel constructor(repositoryManager: IRepositoryManager, resources: Resources) :
    LoadingIndicatorViewModel() {

    private val mResources = resources

    private val mTemplates: Map<Class<*>, AndroidItemBinder> =
        hashMapOf(
            ProgressLoader::class.java to AndroidItemBinder(R.layout.loading_progress, BR.progressLoader),
            RepositoryItemViewModel::class.java to AndroidItemBinder(
                R.layout.repository_item,
                BR.repositoryItemViewModel
            )
        )

    private var mFirstLoading: Boolean = false

    private val mRepositoryManager = repositoryManager

    private var mRepositoriesDisposable = Disposables.disposed()

    private val mItemDecoration by lazy {
        val margin: Int = mResources.getDimension(R.dimen.margin_4).toInt()
        val lateralMargin: Int = mResources.getDimension(R.dimen.margin_16).toInt()
        object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                outRect.bottom = margin
                outRect.top = margin
                outRect.left = lateralMargin
                outRect.right = lateralMargin
            }
        }
    }

    @Bindable
    fun getTemplatesForObjects(): Map<Class<*>, AndroidItemBinder> = mTemplates

    @Bindable
    fun getRepositories(): List<Any> = mItems

    @Bindable
    fun getItemDecoration(): RecyclerView.ItemDecoration = mItemDecoration

    @Bindable
    fun getNextPage() = mPageDescriptor

    fun setNextPage(pageDescriptor: PageDescriptor) {
        mRepositoriesDisposable.dispose()
        handleLoading(true)
        mRepositoriesDisposable =
            mRepositoryManager.searchRepositories(
                "kotlin",
                pageDescriptor.getCurrentPage(),
                pageDescriptor.getPageSize()
            )
                .map {
                    it.map { mItems.add(RepositoryItemViewModel(it)) }
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    handleLoading(false)
                    notifyPropertyChanged(BR.repositories)
                }, this::handlerError)
    }

    override fun onCreate() {
        super.onCreate()
        loadRepositories()
    }

    open fun loadRepositories() {
        mItems.clear()
        mPageDescriptor.setCurrentPage(1)
        setNextPage(mPageDescriptor)
    }

    fun getItemClickListener(): ItemClickListener {
        return object : ItemClickListener {
            override fun onItemClicked(view: View, item: Any) {
                val repositoryViewModel = item as? RepositoryItemViewModel
                    ?: return
                mItemClickNotifier.value = ClickItemWrapper.withAdditionalData(0, repositoryViewModel.getRepository())
            }
        }
    }

    fun getItemClickNotifier() = mItemClickNotifier

    override fun onCleared() {
        super.onCleared()
        mRepositoriesDisposable.dispose()
    }

    private fun setFistLoading(loading: Boolean) {
        mFirstLoading = loading
        notifyPropertyChanged(BR.firstLoading)
    }

    private fun setLoading(loading: Boolean) {
        if (loading && !mItems.contains(mProgressLoader)) {
            mItems.add(mProgressLoader)
        } else if (!loading) {
            mItems.remove(mProgressLoader)
        }
        notifyPropertyChanged(BR.repositories)
    }

    override fun handlerError(throwable: Throwable) {
        super.handlerError(throwable)
        handleLoading(false)
        mMessageNotifier.value = MessageWrapper.withSnackBar(throwable.message ?: return)
    }

    class Factory @Inject constructor(resources: Resources, repositoryManager: IRepositoryManager) :
        ViewModelProvider.NewInstanceFactory() {

        private val mResource = resources

        private val mDataRepository = repositoryManager

        override fun <T : ViewModel?> create(modelClass: Class<T>) = RepositoryViewModel(
            mDataRepository,
            mResource
        ) as T
    }
}
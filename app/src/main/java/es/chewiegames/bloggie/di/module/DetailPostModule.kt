package es.chewiegames.bloggie.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import es.chewiegames.bloggie.di.scope.DetailPostScope
import es.chewiegames.bloggie.interactor.detailPost.DetailPostInteractor
import es.chewiegames.bloggie.interactor.detailPost.IDetailPostInteractor
import es.chewiegames.bloggie.presenter.detailPost.DetailPostPresenter
import es.chewiegames.bloggie.presenter.detailPost.IDetailPostPresenter
import es.chewiegames.bloggie.ui.detailPost.DetailPostView
import android.support.v7.widget.LinearLayoutManager
import es.chewiegames.bloggie.ui.detailPost.DetailPostActivity
import es.chewiegames.bloggie.ui.detailPost.DetailPostAdapter

@Module
class DetailPostModule constructor(private val view: DetailPostView, var activity: DetailPostActivity) {

    @Provides
    fun provideView(): DetailPostView {
        return view
    }

    @Provides
    @DetailPostScope
    fun providePresenter(presenter: DetailPostPresenter): IDetailPostPresenter {
        presenter.setView(view)
        return presenter
    }

    @Provides
    @DetailPostScope
    fun provideInteractor(interactor: DetailPostInteractor): IDetailPostInteractor {
        return interactor
    }

    @Provides
    @DetailPostScope
    fun provideLayoutManager(): LinearLayoutManager {
        return LinearLayoutManager(activity)
    }

    @Provides
    @DetailPostScope
    fun provideDetailPostAdapterListener(): DetailPostAdapter.DetailPostAdapterListener {
        return activity
    }
}
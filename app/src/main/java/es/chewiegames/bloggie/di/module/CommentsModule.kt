package es.chewiegames.bloggie.di.module

import androidx.recyclerview.widget.LinearLayoutManager
import dagger.Module
import dagger.Provides
import es.chewiegames.bloggie.di.scope.CommentsScope
import es.chewiegames.bloggie.interactor.comments.CommentsInteractor
import es.chewiegames.bloggie.interactor.comments.ICommentsInteractor
import es.chewiegames.bloggie.presenter.comments.CommentsPresenter
import es.chewiegames.bloggie.presenter.comments.ICommentsPresenter
import es.chewiegames.bloggie.ui.comments.CommentsActivity
import es.chewiegames.bloggie.ui.comments.CommentsAdapter
import es.chewiegames.bloggie.ui.comments.CommentsView

@Module
class CommentsModule constructor(private val activity: CommentsActivity){

    @Provides
    fun provideView(): CommentsView {
        return activity
    }

    @Provides
    @CommentsScope
    fun providePresenter(presenter: CommentsPresenter): ICommentsPresenter {
        presenter.setView(activity)
        return presenter
    }

    @Provides
    @CommentsScope
    fun provideInteractor(interactor: CommentsInteractor): ICommentsInteractor {
        return interactor
    }

    @Provides
    @CommentsScope
    fun provideLinearLayoutManager() : LinearLayoutManager {
        return LinearLayoutManager(activity)
    }

    @Provides
    @CommentsScope
    fun provideCommentsAdapterListener(): CommentsAdapter.CommentsAdapterListener {
        return activity
    }
}
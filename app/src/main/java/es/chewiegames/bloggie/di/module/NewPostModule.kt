package es.chewiegames.bloggie.di.module

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.Module
import es.chewiegames.bloggie.ui.newPost.NewPostView
import es.chewiegames.bloggie.interactor.newPost.NewPostInteractor
import es.chewiegames.bloggie.interactor.newPost.INewPostInteractor
import dagger.Provides
import es.chewiegames.bloggie.di.scope.NewPostScope
import es.chewiegames.bloggie.presenter.newPost.NewPostPresenter
import es.chewiegames.bloggie.presenter.newPost.INewPostPresenter
import es.chewiegames.data.model.PostContentData
import es.chewiegames.bloggie.ui.newPost.PostAdapter

@Module
class NewPostModule constructor(var view: NewPostView?, var context : Context, var listener : PostAdapter.PostAdapterListener) {

    @Provides
    fun provideView(): NewPostView? {
        return view
    }

    @Provides
    @NewPostScope
    fun providePresenter(presenter: NewPostPresenter): INewPostPresenter {
        presenter.setView(this.provideView()!!)
        return presenter
    }

    @Provides
    @NewPostScope
    fun provideInteractor(interactor: NewPostInteractor): INewPostInteractor {
        return interactor
    }

    @Provides
    @NewPostScope
    fun provideLayoutManager() : LinearLayoutManager {
        return LinearLayoutManager(context)
    }

    @Provides
    @NewPostScope
    fun providePostContent(): ArrayList<PostContentData> {
        return ArrayList()
    }

    @Provides
    @NewPostScope
    fun providePostAdapterListener(): PostAdapter.PostAdapterListener {
        return listener
    }
}
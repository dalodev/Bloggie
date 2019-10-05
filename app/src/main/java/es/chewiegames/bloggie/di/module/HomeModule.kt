package es.chewiegames.bloggie.di.module

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.Module
import dagger.Provides
import es.chewiegames.bloggie.di.scope.HomeScope
import es.chewiegames.bloggie.interactor.home.HomeInteractor
import es.chewiegames.bloggie.interactor.home.IHomeInteractor
import es.chewiegames.bloggie.presenter.home.HomePresenter
import es.chewiegames.bloggie.presenter.home.IHomePresenter
import es.chewiegames.bloggie.ui.ui.home.HomeAdapter
import es.chewiegames.bloggie.ui.ui.home.HomeView

@Module
class HomeModule constructor(var view: HomeView?, var context : Context, var listener : HomeAdapter.HomeAdapterListener) {

    @Provides
    fun provideView(): HomeView? {
        return view
    }

    @Provides
    @HomeScope
    fun providePresenter(presenter: HomePresenter): IHomePresenter {
        presenter.setView(this.provideView()!!)
        return presenter
    }

    @Provides
    @HomeScope
    fun provideInteractor(interactor: HomeInteractor): IHomeInteractor {
        return interactor
    }

    @Provides
    @HomeScope
    fun provideLayoutManager() : LinearLayoutManager {
        return LinearLayoutManager(context)
    }

    @Provides
    @HomeScope
    fun provideHomeAdapterListener() : HomeAdapter.HomeAdapterListener {
        return listener
    }
}
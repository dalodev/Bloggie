package es.chewiegames.bloggie.di.module

import android.app.Activity
import dagger.Module
import dagger.Provides
import es.chewiegames.bloggie.di.scope.MainScope
import es.chewiegames.bloggie.interactor.main.IMainInteractor
import es.chewiegames.bloggie.interactor.main.MainInteractor
import es.chewiegames.bloggie.presenter.main.IMainPresenter
import es.chewiegames.bloggie.presenter.main.MainPresenter
import es.chewiegames.bloggie.ui.main.MainView

@Module
class MainActivityModule constructor(var view: MainView?, var activity: Activity?){

    @Provides
    fun provideView(): MainView? {
        return view
    }

    @Provides
    @MainScope
    fun providePresenter(presenter: MainPresenter): IMainPresenter {
        presenter.setView(this.provideView()!!)
        return presenter
    }

    @Provides
    @MainScope
    fun provideInteractor(interactor: MainInteractor): IMainInteractor {
        return interactor
    }

    @Provides
    fun provideActivity(): Activity? {
        return activity
    }
}
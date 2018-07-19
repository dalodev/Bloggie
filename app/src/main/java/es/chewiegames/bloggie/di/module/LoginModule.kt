package es.chewiegames.bloggie.di.module

import dagger.Module
import android.app.Activity
import es.chewiegames.bloggie.ui.login.LoginView
import dagger.Provides
import es.chewiegames.bloggie.di.scope.LoginScope
import es.chewiegames.bloggie.presenter.login.LoginPresenter
import es.chewiegames.bloggie.presenter.login.ILoginPresenter
import es.chewiegames.bloggie.interactor.login.LoginInteractor
import es.chewiegames.bloggie.interactor.login.ILoginInteractor

@Module
class LoginModule constructor(var view: LoginView?, var activity: Activity?) {

    @Provides
    fun provideView(): LoginView? {
        return view
    }

    @Provides
    @LoginScope
    fun providePresenter(presenter: LoginPresenter): ILoginPresenter {
        presenter.setView(this.provideView()!!)
        return presenter
    }

    @Provides
    @LoginScope
    fun provideInteractor(interactor: LoginInteractor): ILoginInteractor {
        return interactor
    }

    @Provides
    fun provideActivity(): Activity? {
        return activity
    }
}
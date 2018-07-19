package es.chewiegames.bloggie.di.component

import dagger.Subcomponent
import es.chewiegames.bloggie.di.module.LoginModule
import es.chewiegames.bloggie.di.scope.LoginScope
import es.chewiegames.bloggie.ui.login.LoginActivity

@LoginScope
@Subcomponent(modules = (arrayOf(LoginModule::class)))
interface LoginSubComponent {
    fun inject(loginActivity: LoginActivity)
}
package es.chewiegames.bloggie.di.component

import dagger.Subcomponent
import es.chewiegames.bloggie.di.module.HomeModule
import es.chewiegames.bloggie.di.scope.HomeScope
import es.chewiegames.bloggie.ui.ui.home.HomeFragment

@HomeScope
@Subcomponent(modules = (arrayOf(HomeModule::class)))
interface HomeSubComponent {
    fun inject(homeFragment: HomeFragment)
}
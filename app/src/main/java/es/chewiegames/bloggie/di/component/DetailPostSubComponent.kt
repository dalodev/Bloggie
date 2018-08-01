package es.chewiegames.bloggie.di.component

import dagger.Subcomponent
import es.chewiegames.bloggie.di.module.DetailPostModule
import es.chewiegames.bloggie.di.scope.DetailPostScope
import es.chewiegames.bloggie.ui.detailPost.DetailPostActivity

@DetailPostScope
@Subcomponent(modules = (arrayOf(DetailPostModule::class)))
interface DetailPostSubComponent {
    fun inject(detailPostActivity: DetailPostActivity)
}
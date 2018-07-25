package es.chewiegames.bloggie.di.component

import dagger.Subcomponent
import es.chewiegames.bloggie.di.module.NewPostModule
import es.chewiegames.bloggie.di.scope.NewPostScope
import es.chewiegames.bloggie.ui.newPost.NewPostActivity

@NewPostScope
@Subcomponent(modules = (arrayOf(NewPostModule::class)))
interface NewPostSubComponent {
    fun inject(newPostActivity: NewPostActivity)
}
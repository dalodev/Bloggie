package es.chewiegames.bloggie.di.component

import dagger.Subcomponent
import es.chewiegames.bloggie.di.module.CommentsModule
import es.chewiegames.bloggie.di.scope.CommentsScope
import es.chewiegames.bloggie.ui.comments.CommentsActivity

@CommentsScope
@Subcomponent(modules = (arrayOf(CommentsModule::class)))
interface CommentsSubComponent {
    fun inject(commentsActivity: CommentsActivity)
}
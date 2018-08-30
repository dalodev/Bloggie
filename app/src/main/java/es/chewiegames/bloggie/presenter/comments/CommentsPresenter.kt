package es.chewiegames.bloggie.presenter.comments

import android.os.Bundle
import es.chewiegames.bloggie.model.Post
import es.chewiegames.bloggie.ui.comments.CommentsView
import es.chewiegames.bloggie.util.EXTRA_POST
import javax.inject.Inject

class CommentsPresenter @Inject constructor(): ICommentsPresenter {

    private lateinit var view: CommentsView

    override fun setView(view: CommentsView) {
        this.view = view
    }

    override fun loadData(extras: Bundle) {
        val post : Post? = extras.getSerializable(EXTRA_POST) as Post
        if(post != null){
            view.fillValues(post)
            if(post.comments.isNotEmpty()) view.setAdapter(post.comments)
        }
    }
}
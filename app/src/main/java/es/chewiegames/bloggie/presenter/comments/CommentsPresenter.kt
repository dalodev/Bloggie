package es.chewiegames.bloggie.presenter.comments

import android.os.Bundle
import es.chewiegames.bloggie.interactor.comments.CommentsInteractor
import es.chewiegames.bloggie.interactor.comments.ICommentsInteractor
import es.chewiegames.bloggie.model.Comment
import es.chewiegames.bloggie.model.Post
import es.chewiegames.bloggie.ui.comments.CommentsView
import es.chewiegames.bloggie.util.EXTRA_POST
import javax.inject.Inject

class CommentsPresenter @Inject constructor() : ICommentsPresenter, ICommentsInteractor.CommentsListener {

    private lateinit var view: CommentsView

    private var post: Post? = null

    @Inject
    lateinit var mCommentsInteractor: CommentsInteractor

    override fun setView(view: CommentsView) {
        this.view = view
    }

    override fun loadData(extras: Bundle) {
        post = extras.getSerializable(EXTRA_POST) as Post
        view.fillValues(post!!)
        if (post!!.comments.isNotEmpty()) view.setAdapter(post!!.comments)
    }

    override fun sendComment(text: String?) {
        mCommentsInteractor.storeCommentInDatabase(text, post!!, this)
    }

    override fun onAdded(comment: Comment) {
        view.commentAdded(comment)
    }

    override fun loadComments() {
        view.setAdapter(post!!.comments)
    }
}
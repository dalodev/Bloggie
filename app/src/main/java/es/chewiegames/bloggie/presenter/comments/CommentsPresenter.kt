package es.chewiegames.bloggie.presenter.comments

import android.content.res.Resources
import android.os.Bundle
import es.chewiegames.bloggie.R
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

    private var isReplyTo = false

    private var parentComment : Comment? = null

    @Inject
    lateinit var mCommentsInteractor: CommentsInteractor

    @Inject
    lateinit var resources: Resources

    override fun setView(view: CommentsView) {
        this.view = view
    }

    override fun loadData(extras: Bundle) {
        post = extras.getSerializable(EXTRA_POST) as Post
        view.fillValues(post!!)
        if (post!!.comments.isNotEmpty()) view.setAdapter(post!!.comments)
    }

    override fun sendComment(text: String?) {
        if(!isReplyTo){
            mCommentsInteractor.storeCommentInDatabase(text, post!!, this)
        }else{
            view.showReplyTo(false, "")
            parentComment = null
            isReplyTo = false
            mCommentsInteractor.storeReplyCommentInDatabase(text, post!!, parentComment!!, this)
        }
    }

    override fun onCommentAdded(comment: Comment) {
        view.commentAdded(comment)
    }

    override fun onReplyCommentAdded(replyComment: Comment) {
        view.replyCommentAdded(replyComment)
    }

    override fun loadComments() {
        view.setAdapter(post!!.comments)
    }

    override fun loadReplyComments(parentComment: Comment) {
        view.setReplyCommentsAdapter(parentComment)
    }

    override fun replyTo(parentComment: Comment) {
        this.parentComment = parentComment
        isReplyTo = true
        val replyToText = ""+resources.getText(R.string.replyTo) + " @" + parentComment.user!!.userName
        view.showReplyTo(true, replyToText)
    }

    override fun handleBack() {
        if(isReplyTo){
            parentComment = null
            isReplyTo = false
            view.showReplyTo(false, "")
        }else{
            view.goBack()
        }
    }
}
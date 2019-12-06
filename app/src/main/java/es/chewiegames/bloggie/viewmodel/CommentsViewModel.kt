package es.chewiegames.bloggie.viewmodel

import android.os.Bundle
import android.widget.EditText
import androidx.lifecycle.viewModelScope
import es.chewiegames.bloggie.livedata.BaseSingleLiveEvent
import es.chewiegames.bloggie.util.EXTRA_POST
import es.chewiegames.domain.model.Comment
import es.chewiegames.domain.model.CommentParams
import es.chewiegames.domain.model.Post
import es.chewiegames.domain.usecases.comments.StoreCommentUseCase
import es.chewiegames.domain.usecases.comments.StoreReplyUseCase

class CommentsViewModel(
    private val storeCommentUseCase: StoreCommentUseCase,
    private val storeReplyUseCase: StoreReplyUseCase
) : BaseViewModel() {

    val post: BaseSingleLiveEvent<Post> by lazy { BaseSingleLiveEvent<Post>() }
    val comments: BaseSingleLiveEvent<ArrayList<Comment>> by lazy { BaseSingleLiveEvent<ArrayList<Comment>>() }
    val goBack: BaseSingleLiveEvent<Any> by lazy { BaseSingleLiveEvent<Any>() }
    val addItemAdapter: BaseSingleLiveEvent<Int> by lazy { BaseSingleLiveEvent<Int>() }

    private var isReplyTo = false
    private var parentComment: Comment? = null

    fun loadData(extras: Bundle) {
        val extraPost = extras.getSerializable(EXTRA_POST) as Post
        post.value = extraPost
        post.value?.comments?.isNotEmpty().let { comments.value = post.value?.comments }
    }

    private fun sendComment(comment: String) {
        if (!isReplyTo) {
            storeCommentUseCase.executeAsync(viewModelScope, CommentParams(comment, post.value!!, null), ::onCommentStored, {}, {}, {})
        } else {
//            view.showReplyTo(false, "")
            parentComment = null
            isReplyTo = false
            storeReplyUseCase.executeAsync(viewModelScope, CommentParams(comment, post.value!!, parentComment), ::onCommentStored, {}, {}, {})
        }
    }

    private fun onCommentStored(comment: Comment) {
        comments.value?.add(comment)
        addItemAdapter.value = comments.value?.indexOf(comment)
    }

    fun replyTo(parentComment: Comment) {
        this.parentComment = parentComment
        isReplyTo = true
//        val replyToText = "" + resources.getText(R.string.replyTo) + " @" + parentComment.user!!.userName
//        view.showReplyTo(true, replyToText)
    }

    fun handleBack() {
        if (isReplyTo) {
            parentComment = null
            isReplyTo = false
//            view.showReplyTo(false, "")
        } else {
            goBack.call()
        }
    }

    fun onSendCommentClicked(view: EditText) {
        if (view.text.toString().isNotEmpty()) {
            sendComment(view.text.toString())
            view.text.clear()
        }
    }
}

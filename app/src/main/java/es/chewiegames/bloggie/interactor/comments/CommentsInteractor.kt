package es.chewiegames.bloggie.interactor.comments

import com.google.firebase.database.DatabaseReference
import es.chewiegames.bloggie.util.COMMENT_VIEW
import es.chewiegames.data.model.Comment
import es.chewiegames.data.model.UserData
import es.chewiegames.domain.model.Post
import javax.inject.Inject
import javax.inject.Named

class CommentsInteractor @Inject constructor() : ICommentsInteractor {

    @field:[Inject Named("all posts")]
    lateinit var mDatabaseAllPosts: DatabaseReference

    @field:[Inject Named("post by user")]
    lateinit var mDatabasePostByUser: DatabaseReference

    @Inject
    lateinit var mUserData: UserData

    override fun storeCommentInDatabase(text: String?, post: Post, listener: ICommentsInteractor.CommentsListener) {

        var comment = Comment()
        comment.comment = text
        comment.likes = 0
        comment.replies = ArrayList()
        comment.userData = mUserData
        comment.viewType = COMMENT_VIEW
        post.comments.add(comment)
        mDatabaseAllPosts.child(post.id!!).setValue(post)
        mDatabasePostByUser.child(post.id!!).setValue(post)
        listener.onCommentAdded(comment)
    }

    override fun storeReplyCommentInDatabase(text: String?, post: Post, parentComment: Comment, listener: ICommentsInteractor.CommentsListener) {
        var comment = Comment()
        comment.comment = text
        comment.likes = 0
        comment.userData = mUserData
        comment.viewType = COMMENT_VIEW
        parentComment.replies.add(comment)
        post.comments.add(parentComment)
        mDatabaseAllPosts.child(post.id!!).setValue(post)
        mDatabasePostByUser.child(post.id!!).setValue(post)
        listener.onReplyCommentAdded(parentComment)
    }
}

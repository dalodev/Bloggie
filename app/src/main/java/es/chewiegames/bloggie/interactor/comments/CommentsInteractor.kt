package es.chewiegames.bloggie.interactor.comments

import android.arch.lifecycle.LiveData
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import es.chewiegames.bloggie.model.Comment
import es.chewiegames.bloggie.model.Post
import es.chewiegames.bloggie.model.User
import es.chewiegames.bloggie.util.COMMENT_VIEW
import javax.inject.Inject
import javax.inject.Named

class CommentsInteractor @Inject constructor(): ICommentsInteractor{

    @field:[Inject Named("all posts")]
    lateinit var mDatabaseAllPosts: DatabaseReference

    @field:[Inject Named("post by user")]
    lateinit var mDatabasePostByUser: DatabaseReference

    @Inject
    lateinit var mUser: User

    override fun storeCommentInDatabase(text: String?, post: Post, listener: ICommentsInteractor.CommentsListener) {
        var comment = Comment()
        comment.comment = text
        comment.likes = 0
        comment.responses = ArrayList()
        comment.user = mUser
        comment.viewType = COMMENT_VIEW
        post.comments.add(comment)
        mDatabaseAllPosts.child(post.id!!).setValue(post)
        mDatabasePostByUser.child(post.id!!).setValue(post)
        listener.onAdded(comment)
    }
}
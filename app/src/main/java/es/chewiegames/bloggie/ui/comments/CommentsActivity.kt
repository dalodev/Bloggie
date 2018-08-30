package es.chewiegames.bloggie.ui.comments

import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View
import es.chewiegames.bloggie.R
import es.chewiegames.bloggie.di.component.ApplicationComponent
import es.chewiegames.bloggie.di.module.CommentsModule
import es.chewiegames.bloggie.model.Comment
import es.chewiegames.bloggie.model.Post
import es.chewiegames.bloggie.presenter.comments.ICommentsPresenter
import es.chewiegames.bloggie.ui.BaseActivity
import javax.inject.Inject
import es.chewiegames.bloggie.util.RoundedTransformation
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_comments.*

class CommentsActivity : BaseActivity(), CommentsView {

    @Inject
    lateinit var mCommentsPresenter: ICommentsPresenter

    @Inject
    lateinit var adapter : CommentsAdapter

    @Inject
    lateinit var layoutManager : LinearLayoutManager

    override fun getLayoutId(): Int {
        return R.layout.activity_comments
    }

    override fun injectDependencies(component: ApplicationComponent) {
        component.plus(CommentsModule(this)).inject(this)
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(true)
    }

    override fun onStart() {
        super.onStart()
        mCommentsPresenter.loadData(intent.extras)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId){
            android.R.id.home ->{
                super.onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun fillValues(post: Post) {
        if(!post.user!!.avatar.equals("null")) Picasso.with(this).load(post.user!!.avatar).transform(RoundedTransformation(50, 0)).into(userImage)
    }

    override fun setAdapter(comments: ArrayList<Comment>) {
        adapter.comments = comments
        commentsRecyclerView.layoutManager = layoutManager
        commentsRecyclerView.itemAnimator = DefaultItemAnimator()
        commentsRecyclerView.adapter = adapter
    }

    fun onSendCommentClicked(view: View){
        if(userCommentTextInput.text.toString().isNotEmpty()){
            mCommentsPresenter.sendComment(userCommentTextInput.text.toString())
            userCommentTextInput.setText("")
        }
    }

    override fun commentAdded(comment: Comment) {
        mCommentsPresenter.loadComments()
    }

    override fun showMessage(message: String) {
    }

    override fun showLoading(show: Boolean) {
    }
}

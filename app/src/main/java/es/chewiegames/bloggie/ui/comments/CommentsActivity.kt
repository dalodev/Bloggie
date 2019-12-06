package es.chewiegames.bloggie.ui.comments

import android.animation.LayoutTransition
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import es.chewiegames.bloggie.R
import es.chewiegames.bloggie.databinding.ActivityCommentsBinding
import es.chewiegames.bloggie.ui.base.BaseActivity
import es.chewiegames.bloggie.util.RoundedTransformation
import es.chewiegames.bloggie.viewmodel.CommentsViewModel
import es.chewiegames.domain.model.Comment
import es.chewiegames.domain.model.Post
import org.koin.androidx.viewmodel.ext.android.viewModel

class CommentsActivity : BaseActivity<ActivityCommentsBinding>() {

    private val viewModel: CommentsViewModel by viewModel()
    private val commentsAdapter: CommentsAdapter by lazy { CommentsAdapter(viewModel) }

    override fun getLayoutId() = R.layout.activity_comments

    override fun initView(savedInstanceState: Bundle?) {
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(true)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    override fun onStart() {
        super.onStart()
        viewModel.loadData(intent.extras!!)
    }

    override fun initObservers() {
        viewModel.post.observe(this, Observer { fillValues(it) })
        viewModel.comments.observe(this, Observer { setAdapter(it) })
        viewModel.goBack.observe(this, Observer { goBack() })
        viewModel.addItemAdapter.observe(this, Observer { commentsAdapter.notifyItemInserted(it) })
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> {
                viewModel.handleBack()
            }
        }
        return true
    }

    override fun onBackPressed() {
        viewModel.handleBack()
    }

    private fun goBack() = super.onBackPressed()

    private fun fillValues(post: Post) {
        if (!post.user!!.avatar.equals("null"))
            Picasso.with(this).load(post.user!!.avatar).transform(RoundedTransformation(50, 0)).into(binding.userImage)
    }

    private fun setAdapter(comments: ArrayList<Comment>) {
        binding.commentsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.commentsRecyclerView.itemAnimator = DefaultItemAnimator()
        binding.commentsRecyclerView.adapter = commentsAdapter
    }

    fun showReplyTo(show: Boolean, replyToText: String) {
        if (show) {
            binding.commentsRoot.layoutTransition.enableTransitionType(LayoutTransition.APPEARING)
            binding.replyInfo.text = replyToText
            binding.replyInfo.visibility = View.VISIBLE
        } else {
            binding.commentsRoot.layoutTransition.enableTransitionType(LayoutTransition.DISAPPEARING)
            binding.replyInfo.visibility = View.GONE
        }
    }
}

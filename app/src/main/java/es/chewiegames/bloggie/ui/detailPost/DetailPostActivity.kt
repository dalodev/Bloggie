package es.chewiegames.bloggie.ui.detailPost

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import es.chewiegames.bloggie.R
import es.chewiegames.bloggie.databinding.ActivityDetailPostBinding
import es.chewiegames.bloggie.di.component.ApplicationComponent
import es.chewiegames.bloggie.presenter.detailPost.IDetailPostPresenter
import es.chewiegames.bloggie.ui.base.BaseActivity
import es.chewiegames.bloggie.viewmodel.DetailPostViewModel
import es.chewiegames.data.model.PostContentData
import es.chewiegames.domain.model.Post
import org.koin.androidx.viewmodel.ext.android.viewModel
import javax.inject.Inject

class DetailPostActivity : BaseActivity<ActivityDetailPostBinding>(), DetailPostView, DetailPostAdapter.DetailPostAdapterListener {

    private val viewModel: DetailPostViewModel by viewModel()

    @Inject
    lateinit var mDetailPostPresenter: IDetailPostPresenter

    @Inject
    lateinit var layoutManager: LinearLayoutManager

    override fun getLayoutId(): Int {
        return R.layout.activity_detail_post
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        mDetailPostPresenter.setCurrentAnimatorDuration()
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(true)
    }

    override fun onStart() {
        super.onStart()
        mDetailPostPresenter.loadData(intent.extras!!)
    }

    override fun injectDependencies(component: ApplicationComponent) {}

    override fun setAdapter(content: ArrayList<PostContentData>) {
        /*adapter.setPostContent(content)
        contentPostList.layoutManager = layoutManager
        contentPostList.itemAnimator = DefaultItemAnimator()
        contentPostList.adapter = adapter*/
    }

    override fun fillValues(post: Post) {
        if (post.titleImage != null) {
            binding.imgToolbar.transitionName = post.id
        }
        binding.collapsingToolBar.transitionName = post.title
        Picasso.with(this).load(post.titleImage).into(binding.imgToolbar)
        binding.collapsingToolBar.title = post.title
    }

    override fun onBackPressed() {
        mDetailPostPresenter.handleBack()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> {
                supportFinishAfterTransition()
                super.onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun expandTitleImage(v: View) {
        if (mDetailPostPresenter.getPost()!!.titleImage != null) {
            mDetailPostPresenter.zoomDetailPostImage(binding.imgToolbar, binding.expandedImage, mDetailPostPresenter.getPost()!!)
        }
    }

    override fun displayExpandedImage(content: String) {
        Picasso.with(this).load(content).into(binding.expandedImage, object : Callback {
            override fun onSuccess() { binding.expandedImageProgressbar.visibility = View.GONE }
            override fun onError() {} })
    }

    override fun goBack() {
        super.onBackPressed()
    }

    override fun closeExpandedImage() {
        closeExpandedImage(binding.expandedImage)
    }

    fun closeExpandedImage(v: View) {
        mDetailPostPresenter.closeExpandedImage(binding.expandedImage)
    }

    override fun onClickImage(thumbView: View, postContent: PostContentData) {
        binding.expandedImageProgressbar.visibility = View.VISIBLE
        mDetailPostPresenter.zoomDetailPostImage(thumbView, binding.expandedImage, postContent)
    }

    override fun showMessage(message: String) {
    }

    override fun showLoading(show: Boolean) {
    }
}

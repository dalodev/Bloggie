package es.chewiegames.bloggie.ui.detailPost

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import es.chewiegames.bloggie.R
import es.chewiegames.bloggie.databinding.ActivityDetailPostBinding
import es.chewiegames.bloggie.di.component.ApplicationComponent
import es.chewiegames.bloggie.ui.base.BaseActivity
import es.chewiegames.bloggie.viewmodel.DetailPostViewModel
import es.chewiegames.data.model.PostContentData
import es.chewiegames.domain.model.Post
import es.chewiegames.domain.model.PostContent
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailPostActivity : BaseActivity<ActivityDetailPostBinding>() {

    private val viewModel: DetailPostViewModel by viewModel()
    private val adapter: DetailPostAdapter by lazy { DetailPostAdapter(viewModel) }

    override fun getLayoutId() = R.layout.activity_detail_post

    override fun initView(savedInstanceState: Bundle?) {
        viewModel.setCurrentAnimatorDuration()
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(true)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    override fun onStart() {
        super.onStart()
        viewModel.loadData(intent.extras!!)
    }

    override fun injectDependencies(component: ApplicationComponent) {}

    override fun initObservers() {
        viewModel.postContent.observe(this, Observer { setAdapter(it) })
        viewModel.closeExpandedImage.observe(this, Observer { closeExpandedImage(binding.expandedImage) })
        viewModel.goBack.observe(this, Observer { goBack() })
        viewModel.displayExpandedImage.observe(this, Observer { displayExpandedImage(it) })
    }

    private fun setAdapter(content: ArrayList<PostContent>) {
        adapter.postContent = content
        binding.contentPostList.layoutManager = LinearLayoutManager(this)
        binding.contentPostList.itemAnimator = DefaultItemAnimator()
        binding.contentPostList.adapter = adapter
    }

    override fun onBackPressed() = viewModel.handleBack()

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> {
                supportFinishAfterTransition()
                goBack()
            }
        }
        return true
    }

    fun expandTitleImage(v: View) {
        if (viewModel.post.value?.titleImage != null) {
            viewModel.zoomDetailPostImage(v, binding.expandedImage, viewModel.post.value!!)
        }
    }

    private fun displayExpandedImage(content: String) {
        Picasso.with(this).load(content).into(binding.expandedImage, object : Callback {
            override fun onSuccess() { binding.expandedImageProgressbar.visibility = View.GONE }
            override fun onError() {} })
    }

    private fun goBack() = super.onBackPressed()

    private fun closeExpandedImage(view: View) = viewModel.closeExpandedImage(view)
}

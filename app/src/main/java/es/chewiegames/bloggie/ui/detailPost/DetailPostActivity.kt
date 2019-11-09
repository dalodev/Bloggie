package es.chewiegames.bloggie.ui.detailPost

import android.os.Bundle
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import es.chewiegames.bloggie.R
import es.chewiegames.bloggie.di.component.ApplicationComponent
import es.chewiegames.bloggie.di.module.DetailPostModule
import es.chewiegames.domain.model.Post
import es.chewiegames.data.model.PostContentData
import es.chewiegames.bloggie.presenter.detailPost.IDetailPostPresenter
import es.chewiegames.bloggie.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_detail_post.*
import javax.inject.Inject

class DetailPostActivity : BaseActivity(), DetailPostView, DetailPostAdapter.DetailPostAdapterListener{

    @Inject
    lateinit var mDetailPostPresenter : IDetailPostPresenter


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

    override fun injectDependencies(component: ApplicationComponent) {
        component.plus(DetailPostModule(this, this)).inject(this)
    }

    override fun setAdapter(content: ArrayList<PostContentData>) {
        /*adapter.setPostContent(content)
        contentPostList.layoutManager = layoutManager
        contentPostList.itemAnimator = DefaultItemAnimator()
        contentPostList.adapter = adapter*/
    }

    override fun fillValues(post: Post) {
        if(post.titleImage!=null){
            imgToolbar.transitionName = post.id
        }
        collapsingToolBar.transitionName = post.title
        Picasso.with(this).load(post.titleImage).into(imgToolbar)
        collapsingToolBar.title = post.title
    }

    override fun onBackPressed() {
        mDetailPostPresenter.handleBack()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId) {
            android.R.id.home ->{
                supportFinishAfterTransition()
                super.onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun expandTitleImage(v: View){
        if(mDetailPostPresenter.getPost()!!.titleImage!= null){
            mDetailPostPresenter.zoomDetailPostImage(imgToolbar, expandedImage, mDetailPostPresenter.getPost()!!)
        }
    }

    override fun displayExpandedImage(content: String) {
        Picasso.with(this).load(content).into(expandedImage, object: Callback {
            override fun onSuccess() { expandedImageProgressbar.visibility = View.GONE }
            override fun onError() {} })
    }

    override fun goBack() {
        super.onBackPressed()
    }

    override fun closeExpandedImage() {
        closeExpandedImage(expandedImage)
    }

    fun closeExpandedImage(v: View){
        mDetailPostPresenter.closeExpandedImage(expandedImage)
    }

    override fun onClickImage(thumbView: View, postContent: PostContentData) {
        expandedImageProgressbar.visibility = View.VISIBLE
        mDetailPostPresenter.zoomDetailPostImage(thumbView, expandedImage, postContent)
    }

    override fun showMessage(message: String) {
    }

    override fun showLoading(show: Boolean) {
    }
}

package es.chewiegames.bloggie.ui.detailPost

import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.squareup.picasso.Picasso
import es.chewiegames.bloggie.R
import es.chewiegames.bloggie.di.component.ApplicationComponent
import es.chewiegames.bloggie.di.module.DetailPostModule
import es.chewiegames.bloggie.model.Post
import es.chewiegames.bloggie.model.PostContent
import es.chewiegames.bloggie.presenter.detailPost.IDetailPostPresenter
import es.chewiegames.bloggie.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_detail_post.*
import javax.inject.Inject

class DetailPostActivity : BaseActivity(), DetailPostView, DetailPostAdapter.DetailPostAdapterListener{

    @Inject
    lateinit var mDetailPostPresenter : IDetailPostPresenter

    @Inject
    lateinit var adapter : DetailPostAdapter

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
        mDetailPostPresenter.loadData(intent.extras)
    }

    override fun injectDependencies(component: ApplicationComponent) {
        component.plus(DetailPostModule(this, this)).inject(this)
    }



    override fun setAdapter(content: ArrayList<PostContent>) {
        adapter.setPostContent(content)
        contentPostList.layoutManager = layoutManager
        contentPostList.itemAnimator = DefaultItemAnimator()
        contentPostList.adapter = adapter
    }

    override fun fillValues(post: Post) {
        if(post.titleImage!=null){
            imgToolbar.transitionName = post.id
        }
        collapsingToolBar.transitionName = post.title
        collapsingToolBar.title = post.title
        Picasso.with(this).load(post.titleImage).into(imgToolbar)

    }

    override fun displayExpandedImage(content: String) {
    }

    override fun goBack() {
    }

    override fun closeExpandedImage() {
    }

    override fun onClickImage(thumbView: View, postContent: PostContent) {
    }

    override fun showMessage(message: String) {
    }

    override fun showLoading(show: Boolean) {
    }
}

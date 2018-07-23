package es.chewiegames.bloggie.ui.ui.home

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import es.chewiegames.bloggie.R
import es.chewiegames.bloggie.di.component.ApplicationComponent
import es.chewiegames.bloggie.di.module.HomeModule
import es.chewiegames.bloggie.model.Post
import es.chewiegames.bloggie.presenter.home.IHomePresenter
import es.chewiegames.bloggie.ui.BaseFragment
import es.chewiegames.bloggie.util.HomeItemAnimator
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

class HomeFragment : BaseFragment(), HomeView, HomeAdapter.HomeAdapterListener {

    @Inject
    lateinit var mHomePresenter: IHomePresenter

    @Inject
    lateinit var layoutManager: LinearLayoutManager

    @Inject
    lateinit var adapter : HomeAdapter



    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun injectDependencies(component: ApplicationComponent, context: Context) {
        component.plus(HomeModule(this, context, this)).inject(this)
    }

    override fun initView() {
        super.initView()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        updateAdapter()
    }

    override fun onStart() {
        super.onStart()
        //mHomePresenter.loadFeedPosts()
    }

    fun updateAdapter(){
        feedRecyclerview.layoutManager = layoutManager
        feedRecyclerview.itemAnimator = HomeItemAnimator()
        feedRecyclerview.adapter = adapter
        showEmptyView()
    }

    override fun onPostClicked(post: Post, vararg viewsToShare: View) {
    }

    override fun onLikePost(post: Post, checked: Boolean) {
    }

    override fun onAddCommentClicked(post: Post) {
    }

    override fun updateFeedPosts(posts: ArrayList<Post>) {
    }

    override fun updateItem(position: Int) {
    }

    override fun addItem() {
    }

    override fun removeItem(position: Int) {
    }

    override fun showEmptyView() {
        emptyView.visibility = if (adapter.itemCount == 0) View.VISIBLE else View.GONE
        progressBar.visibility = View.GONE
    }

    override fun showMessage(message: String) {
    }

    override fun showLoading(show: Boolean) {
        progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }
}
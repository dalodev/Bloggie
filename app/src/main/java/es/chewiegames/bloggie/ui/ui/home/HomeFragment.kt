package es.chewiegames.bloggie.ui.ui.home

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
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

    /**
     * Dependences
     */
    @Inject
    lateinit var mHomePresenter: IHomePresenter

    @Inject
    lateinit var layoutManager: LinearLayoutManager

    @Inject
    lateinit var adapter : HomeAdapter

    /**
     * Get the layout view of the activity
     * @return The layout id of the activity
     */
    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    /**
     * Initialize the inject dependences for this activity. This method is triggered in onCreate event
     * @param component
     */
    override fun injectDependencies(component: ApplicationComponent, context: Context) {
        component.plus(HomeModule(this, context, this)).inject(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //when activity created, initialize the adapter
        updateAdapter()
    }

    override fun onStart() {
        super.onStart()
        //call presenter to load data
        mHomePresenter.loadFeedPosts()
    }

    /**
     * set the adapter for recyclerview
     */
    fun updateAdapter(){
        feedRecyclerview.layoutManager = layoutManager
        feedRecyclerview.itemAnimator = HomeItemAnimator()
        feedRecyclerview.adapter = adapter
        showEmptyView()
    }

    /**
     * trigger when user touch on item of the list
     */
    override fun onPostClicked(post: Post, vararg viewsToShare: View) {
    }

    /**
     * trigger when user touch on like button in post
     */
    override fun onLikePost(post: Post, checked: Boolean) {
        mHomePresenter.onPostLiked(post, checked)
    }

    /**
     * trigger when user touch on comment buttom
     */
    override fun onAddCommentClicked(post: Post) {
    }

    /**
     * notify the adapter the data has been changed
     */
    override fun updateFeedPosts(posts: ArrayList<Post>) {
        adapter.notifyDataSetChanged()
    }

    /**
     * notify the adapter the position has been changed
     */
    override fun updateItem(position: Int) {
        adapter.notifyItemChanged(position)
    }

    /**
     * notify the adapter a item has been added on the first position
     */
    override fun addItem() {
        adapter.notifyItemRangeChanged(0, adapter.itemCount)
    }

    /**
     * notify the adapter a item has been removed in a specific position
     */
    override fun removeItem(position: Int) {
        adapter.notifyItemRemoved(position)
    }

    /**
     * display the view if no items in list
     */
    override fun showEmptyView() {
        emptyView.visibility = if (adapter.itemCount == 0) View.VISIBLE else View.GONE
        progressBar.visibility = View.GONE
    }

    /**
     * Display a progressbar when loading data from database
     */
    override fun showLoading(show: Boolean) {
        progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun showMessage(message: String) {
    }
}
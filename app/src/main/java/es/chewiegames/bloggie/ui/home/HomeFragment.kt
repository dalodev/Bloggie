package es.chewiegames.bloggie.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import es.chewiegames.bloggie.R
import es.chewiegames.bloggie.util.HomeItemAnimator
import kotlinx.android.synthetic.main.fragment_home.*
import androidx.lifecycle.Observer
import es.chewiegames.bloggie.databinding.FragmentHomeBinding
import es.chewiegames.bloggie.ui.base.BaseBindingFragment
import es.chewiegames.bloggie.ui.detailPost.DetailPostActivity
import es.chewiegames.bloggie.util.EXTRA_POST
import es.chewiegames.bloggie.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseBindingFragment() {

    private val viewModel: HomeViewModel by viewModel()
    lateinit var binding: FragmentHomeBinding
    private val adapter: HomeAdapter by lazy { HomeAdapter(context!!, viewModel) }

    override fun getLayoutId(): Int = R.layout.fragment_home

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        binding.lifecycleOwner = this
        binding.homeViewModel = viewModel
        return binding.root
    }

    override fun initObservers() {
        viewModel.posts.observe(this, Observer {
            adapter.posts = it
            adapter.notifyDataSetChanged()
        })
        viewModel.updateItemAdapterPosition.observe(this, Observer {
            adapter.notifyItemChanged(it)
        })
        viewModel.addItemAdapter.observe(this, Observer {
            adapter.notifyItemRangeChanged(0, adapter.itemCount)
        })
        viewModel.removeItemAdapterPosition.observe(this, Observer {
            adapter.notifyItemRemoved(it)
        })
        viewModel.goToComments.observe(this, Observer { findNavController().navigate(R.id.action_home_to_comments, it) })
        viewModel.viewsToShare.observe(this, Observer {
            val p2 = Pair.create(it[1], ViewCompat.getTransitionName(it[1]))
            val options = if(it.size > 1){
                val p1 = Pair.create(it[0], ViewCompat.getTransitionName(it[0]))
                ActivityOptionsCompat.makeSceneTransitionAnimation(activity!!, p1, p2)
            }else{
                ActivityOptionsCompat.makeSceneTransitionAnimation(activity!!, p2)
            }
            viewModel.options = options.toBundle()!!
        })
        viewModel.postToDetail.observe(this, Observer {
            val intent = Intent(activity, DetailPostActivity::class.java)
            intent.putExtra(EXTRA_POST, it)
            goToActivity(intent, viewModel.options)
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        updateAdapter()
    }

    private fun updateAdapter() {
        feedRecyclerview.layoutManager = LinearLayoutManager(context)
        feedRecyclerview.itemAnimator = HomeItemAnimator()
        feedRecyclerview.adapter = adapter
        showEmptyView()
    }

    private fun showEmptyView() {
        viewModel.emptyViewVisibility.value = if(adapter.itemCount == 0) View.VISIBLE else View.GONE
        viewModel.loadingVisibility.value = View.GONE
    }

    override fun destroyView() {
    }
}
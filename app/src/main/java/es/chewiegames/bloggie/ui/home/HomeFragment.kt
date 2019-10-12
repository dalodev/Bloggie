package es.chewiegames.bloggie.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import es.chewiegames.bloggie.R
import es.chewiegames.bloggie.util.HomeItemAnimator
import kotlinx.android.synthetic.main.fragment_home.*
import androidx.lifecycle.Observer
import es.chewiegames.bloggie.databinding.FragmentHomeBinding
import es.chewiegames.bloggie.ui.base.BaseBindingFragment
import es.chewiegames.bloggie.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseBindingFragment() {

    private val viewModel: HomeViewModel by viewModel()

    lateinit var binding : FragmentHomeBinding

    private val adapter: HomeAdapter by lazy {  HomeAdapter(context!!, viewModel) }

    /**
     * Get the layout view of the activity
     * @return The layout id of the activity
     */
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
            showEmptyView()
        })
        viewModel.updateItemPosition.observe(this, Observer {
            adapter.notifyItemChanged(it)
            showEmptyView()
        })
        viewModel.addItem.observe(this, Observer {
            adapter.notifyItemRangeChanged(0, adapter.itemCount)
            showEmptyView()
        })
        viewModel.removeItemPosition.observe(this, Observer {
            adapter.notifyItemRemoved(it)
            showEmptyView()
        })
        viewModel.goToComments.observe(this, Observer {
            findNavController().navigate(R.id.action_home_to_comments, it)
        })
        viewModel.goToDetailPostActivity.observe(this, Observer {
            val entry = it.entries.iterator().next()
            val bundle = entry.value
            val intent = entry.key
            goToActivity(intent, bundle)
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //when activity created, initialize the adapter
        updateAdapter()
    }

    override fun onStart() {
        super.onStart()
        //call viewmodel to load data
        viewModel.loadFeedPosts()
    }

    /**
     * set the adapter for recyclerview
     */
    private fun updateAdapter() {
        if (feedRecyclerview.layoutManager == null) {
            feedRecyclerview.layoutManager = LinearLayoutManager(context)
            feedRecyclerview.itemAnimator = HomeItemAnimator()
            feedRecyclerview.adapter = adapter
            showEmptyView()
        }
    }

    /**
     * display the view if no items in list
     */
    fun showEmptyView() {
        viewModel.showEmptyView.value = adapter.itemCount == 0
        viewModel.showLoading.value = false
    }

    override fun destroyView() {
    }
}
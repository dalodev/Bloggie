/*
 * Copyright 2019 littledavity
 */
package es.littledavity.bloggie.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import es.littledavity.bloggie.R
import es.littledavity.bloggie.databinding.FragmentHomeBinding
import es.littledavity.bloggie.ui.base.BaseFragment
import es.littledavity.bloggie.ui.detailPost.DetailPostActivity
import es.littledavity.bloggie.util.EXTRA_POST
import es.littledavity.bloggie.util.HomeItemAnimator
import es.littledavity.bloggie.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val viewModel: HomeViewModel by viewModel()
    private val adapter: HomeAdapter by lazy { HomeAdapter(context!!, viewModel) }

    override fun getLayoutId(): Int = R.layout.fragment_home

    override fun initView() {
        binding.homeViewModel = viewModel
    }

    override fun initObservers() {
        viewModel.posts.observe(this, Observer {
            adapter.posts = it
            adapter.notifyDataSetChanged()
        })
        viewModel.updateItemAdapterPosition.observe(this, Observer { adapter.notifyItemChanged(it) })
        viewModel.addItemAdapter.observe(this, Observer { adapter.notifyItemRangeChanged(0, adapter.itemCount) })
        viewModel.removeItemAdapterPosition.observe(this, Observer { adapter.notifyItemRemoved(it) })
        viewModel.navigateToComments.observe(this, Observer { findNavController().navigate(R.id.action_home_to_comments, it) })
        viewModel.viewsToShare.observe(this, Observer {
            if (it != null) {
                val p2 = Pair.create(it[1], ViewCompat.getTransitionName(it[1]!!))
                val options = if (it[0] != null) {
                    val p1 = Pair.create(it[0], ViewCompat.getTransitionName(it[0]!!))
                    ActivityOptionsCompat.makeSceneTransitionAnimation(activity!!, p1, p2)
                } else {
                    ActivityOptionsCompat.makeSceneTransitionAnimation(activity!!, p2)
                }
                viewModel.options = options.toBundle()!!
            }
        })
        viewModel.navigateToDetail.observe(this, Observer {
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
        binding.feedRecyclerview.layoutManager = LinearLayoutManager(context)
        binding.feedRecyclerview.itemAnimator = HomeItemAnimator()
        binding.feedRecyclerview.adapter = adapter
    }
}

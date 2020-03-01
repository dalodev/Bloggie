/*
 * Copyright 2020 littledavity
 */
package es.littledavity.dynamicfeatures.feed.list

import android.os.Bundle
import android.view.View
import androidx.paging.PagedList
import es.littledavity.bloggie.BloggieApp
import es.littledavity.commons.ui.base.BaseFragment
import es.littledavity.commons.ui.extensions.linearLayoutManager
import es.littledavity.commons.ui.extensions.observe
import es.littledavity.domain.model.Post
import es.littledavity.dynamicfeatures.feed.R
import es.littledavity.dynamicfeatures.feed.databinding.FragmentFeedBinding
import es.littledavity.dynamicfeatures.feed.list.adapter.FeedAdapter
import es.littledavity.dynamicfeatures.feed.list.adapter.FeedAdapterState
import es.littledavity.dynamicfeatures.feed.list.di.DaggerFeedComponent
import es.littledavity.dynamicfeatures.feed.list.di.FeedModule
import javax.inject.Inject
import timber.log.Timber

/**
 * View listing the all feed posts, ours and follow with option to display the detail view.
 *
 * @see BaseFragment
 */
class FeedFragment : BaseFragment<FragmentFeedBinding, FeedViewModel>(
    layoutId = R.layout.fragment_feed
) {

    @Inject
    lateinit var viewAdapter: FeedAdapter

    /**
     * Called to have the fragment instantiate its user interface view.
     *
     * @param view The view returned by onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     * @see BaseFragment.onViewCreated
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(viewModel.state, ::onViewStateChange)
        observe(viewModel.data, ::onViewDataChange)
        observe(viewModel.event, ::onViewEvent)
    }

    /**
     * Initialize dagger injection dependency graph.
     */
    override fun onInitDependencyInjection() {
        DaggerFeedComponent
            .builder()
            .coreComponent(BloggieApp.coreComponent(requireContext()))
            .feedModule(FeedModule(this))
            .build()
            .inject(this)
    }

    /**
     * Initialize view data binding variables.
     */
    override fun onInitDataBinding() {
        viewBinding.viewModel = viewModel
        viewBinding.feedRecyclerview.apply {
            adapter = viewAdapter
            linearLayoutManager
        }
    }

    /**
     * Clear all listeners and observers of [FeedFragment]
     */
    override fun onClear() {
        viewModel.data.removeObservers(this)
        viewModel.state.removeObservers(this)
        viewModel.event.removeObservers(this)
    }

    // ============================================================================================
    //  Private observers methods
    // ============================================================================================

    /**
     * Observer view data change on [FeedViewModel].
     *
     * @param viewData Paged list of characters.
     */
    private fun onViewDataChange(viewData: PagedList<Post>) {
        viewAdapter.submitList(viewData)
    }

    /**
     * Observer view state change on [FeedViewModel].
     *
     * @param viewState State of characters list.
     */
    private fun onViewStateChange(viewState: FeedViewState) {
        when (viewState) {
            is FeedViewState.Loaded ->
                viewAdapter.submitState(FeedAdapterState.Added)
            is FeedViewState.AddLoading ->
                viewAdapter.submitState(FeedAdapterState.AddLoading)
            is FeedViewState.AddError ->
                viewAdapter.submitState(FeedAdapterState.AddError)
            is FeedViewState.NoMoreElements ->
                viewAdapter.submitState(FeedAdapterState.NoMore)
        }
    }

    /**
     * Observer view event change on [FeedViewModel].
     *
     * @param viewEvent Event on characters list.
     */
    private fun onViewEvent(viewEvent: FeedViewEvent) {
        when (viewEvent) {
            is FeedViewEvent.OpenPostDetail ->
                Timber.i("Open post detail: $viewEvent.id")
//                findNavController().navigate(
//                    CharactersListFragmentDirections
//                        .actionCharactersListFragmentToCharacterDetailFragment(viewEvent.id))
        }
    }
}

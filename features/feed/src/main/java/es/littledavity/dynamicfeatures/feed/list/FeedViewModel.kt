package es.littledavity.dynamicfeatures.feed.list

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.littledavity.dynamicfeatures.feed.list.paging.FeedPageDataSourceFactory
import javax.inject.Inject

class FeedViewModel @Inject constructor(
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val dataSourceFactory: FeedPageDataSourceFactory
) : ViewModel() {

    private val _state = MutableLiveData<FeedViewState>()
    val state: LiveData<FeedViewState>
        get() = _state
}

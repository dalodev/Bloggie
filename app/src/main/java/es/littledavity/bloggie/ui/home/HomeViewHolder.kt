package es.littledavity.bloggie.ui.home

import android.view.GestureDetector
import android.view.MotionEvent
import androidx.core.view.GestureDetectorCompat
import androidx.core.view.ViewCompat
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import es.littledavity.bloggie.BR
import es.littledavity.bloggie.viewmodel.HomeViewModel
import es.littledavity.domain.model.Post
import es.littledavity.domain.model.User
import kotlinx.android.synthetic.main.list_item_home.view.*
import timber.log.Timber

class HomeViewHolder constructor(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(feedPost: Post, feedPostUserData: User, homeViewModel: HomeViewModel) {
        homeViewModel.onBind = true
        binding.setVariable(BR.homeViewModel, homeViewModel)
        binding.setVariable(BR.feedPost, feedPost)
        binding.setVariable(BR.user, feedPostUserData)
        binding.setVariable(BR.adapterPosition, adapterPosition)
        ViewCompat.setTransitionName(binding.root.postImage, feedPost.id)
        ViewCompat.setTransitionName(binding.root.postTitle, feedPost.title)
        binding.executePendingBindings()
        val mDetector = GestureDetectorCompat(itemView.confettiAnimation.context, object : GestureDetector.OnGestureListener {
            override fun onShowPress(e: MotionEvent?) {
                Timber.i("onShowPress")
            }

            override fun onSingleTapUp(e: MotionEvent?): Boolean {
                Timber.i("onSingleTapUp")
                return true
            }

            override fun onDown(e: MotionEvent?): Boolean {
                Timber.i("onDown")
                return true
            }

            override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
                Timber.i("onFling")
                return false
            }

            override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
                Timber.i("onScroll")
                return false
            }

            override fun onLongPress(e: MotionEvent?) {
                Timber.i("onLongPress")
            }
        })
        mDetector.setOnDoubleTapListener(
                object : GestureDetector.OnDoubleTapListener {
                    override fun onDoubleTap(e: MotionEvent?): Boolean {
                        Timber.i("onDoubleTap")
                        if (!itemView.confettiAnimation.isAnimating) {
                            itemView.confettiAnimation.playAnimation()
                        }
                        homeViewModel.littlePointChecked(itemView.littlePoint, adapterPosition)
                        return true
                    }

                    override fun onDoubleTapEvent(e: MotionEvent?): Boolean {
                        Timber.i("onDoubleTapEvent")
                        return true
                    }

                    override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
                        Timber.i("onSingleTapConfirmed")
                        homeViewModel.onPostClicked(feedPost, if (feedPost.titleImage != null) itemView.postImage else null, itemView.postTitle)
                        return true
                    }
                })
        homeViewModel.isLittlePointChecked(itemView.littlePoint, adapterPosition)
        itemView.confettiAnimation.setOnTouchListener { v, event -> mDetector.onTouchEvent(event) }
        homeViewModel.onBind = false
    }
}

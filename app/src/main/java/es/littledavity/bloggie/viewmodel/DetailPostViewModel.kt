package es.littledavity.bloggie.viewmodel

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Point
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.lifecycle.viewModelScope
import es.littledavity.bloggie.livedata.BaseSingleLiveEvent
import es.littledavity.bloggie.util.EXTRA_POST
import es.littledavity.data.model.PostContentData
import es.littledavity.domain.model.Post
import es.littledavity.domain.model.PostContent
import es.littledavity.domain.usecases.post.UpdatePostUseCase

class DetailPostViewModel(private val context: Context, private val updatePostUseCase: UpdatePostUseCase) : BaseViewModel() {

    val post: BaseSingleLiveEvent<Post> by lazy { BaseSingleLiveEvent<Post>() }
    val postContent: BaseSingleLiveEvent<ArrayList<PostContent>> by lazy { BaseSingleLiveEvent<ArrayList<PostContent>>() }
    val closeExpandedImage: BaseSingleLiveEvent<Any> by lazy { BaseSingleLiveEvent<Any>() }
    val goBack: BaseSingleLiveEvent<Any> by lazy { BaseSingleLiveEvent<Any>() }
    val displayExpandedImage: BaseSingleLiveEvent<String> by lazy { BaseSingleLiveEvent<String>() }

    private var isExpandedImage = false
    private var mShortAnimationDuration: Long = 0L
    private var mCurrentAnimator: Animator? = null
    private var thumbView: View? = null
    private var startScaleFinal: Float = 0f
    private var startScale: Float = 0f
    private var startBounds: Rect? = null

    fun loadData(extras: Bundle) {
        val extraPost: Post? = extras.getSerializable(EXTRA_POST) as Post
        if (extraPost != null) {
            extraPost.views = extraPost.views + 1
            post.value = extraPost
            updatePostUseCase.executeAsync(viewModelScope, extraPost, {}, {}, {}, {})
            if (extraPost.content.isNotEmpty()) {
                postContent.value = extraPost.content
            }
        }
    }

    fun setCurrentAnimatorDuration() {
        val mShortAnimationDuration = context.resources.getInteger(android.R.integer.config_shortAnimTime)
        this.mShortAnimationDuration = mShortAnimationDuration.toLong()
    }

    fun zoomImageFromThumb(thumbView: View, expandedImage: View, content: String) {
        this.thumbView = thumbView
        // If there's an animation in progress, cancel it
        // immediately and proceed with this one.
        if (mCurrentAnimator != null) {
            mCurrentAnimator!!.cancel()
        }
        // Load the high-resolution "zoomed-in" image.
        displayExpandedImage(content)

            // Calculate the starting and ending bounds for the zoomed-in image.
            // This step involves lots of math. Yay, math.
            startBounds = Rect()
            val finalBounds = Rect()
            val globalOffset = Point()

            // The start bounds are the global visible rectangle of the thumbnail,
            // and the final bounds are the global visible rectangle of the container
            // view. Also set the container view's offset as the origin for the
            // bounds, since that's the origin for the positioning animation
            // properties (X, Y).
            thumbView.getGlobalVisibleRect(startBounds)
            thumbView.rootView.getGlobalVisibleRect(finalBounds, globalOffset)
            startBounds!!.offset(-globalOffset.x, -globalOffset.y)
            finalBounds.offset(-globalOffset.x, -globalOffset.y)

            // Adjust the start bounds to be the same aspect ratio as the final
            // bounds using the "center crop" technique. This prevents undesirable
            // stretching during the animation. Also calculate the start scaling
            // factor (the end scaling factor is always 1.0).
            if (finalBounds.width().toFloat() / finalBounds.height() > startBounds!!.width().toFloat() / startBounds!!.height()) {
                // Extend start bounds horizontally
                startScale = startBounds!!.height().toFloat() / finalBounds.height()
                val startWidth: Int = (startScale * finalBounds.width()).toInt()
                val deltaWidth = (startWidth - startBounds!!.width()) / 2
                startBounds!!.left -= deltaWidth
                startBounds!!.right += deltaWidth
            } else {
                // Extend start bounds vertically
                startScale = startBounds!!.width().toFloat() / finalBounds.width()
                val startHeight: Int = (startScale * finalBounds.height()).toInt()
                val deltaHeight = (startHeight - startBounds!!.height()) / 2
                startBounds!!.top -= deltaHeight
                startBounds!!.bottom += deltaHeight
            }

            // Hide the thumbnail and show the zoomed-in view. When the animation
            // begins, it will position the zoomed-in view in the place of the
            // thumbnail.
            thumbView.alpha = 0f
            expandedImage.visibility = View.VISIBLE
            isExpandedImage = true
            // Set the pivot point for SCALE_X and SCALE_Y transformations
            // to the top-left corner of the zoomed-in view (the default
            // is the center of the view).
            expandedImage.pivotX = 0f
            expandedImage.pivotY = 0f

            // Construct and run the parallel animation of the four translation and
            // scale properties (X, Y, SCALE_X, and SCALE_Y).
            val set = AnimatorSet()
            set.play(ObjectAnimator.ofFloat(expandedImage, View.SCALE_X, startBounds!!.left.toFloat(), finalBounds.left.toFloat()))
                    .with(ObjectAnimator.ofFloat(expandedImage, View.Y, startBounds!!.top.toFloat(), finalBounds.top.toFloat()))
                    .with(ObjectAnimator.ofFloat(expandedImage, View.SCALE_X, startScale, 1f))
                    .with(ObjectAnimator.ofFloat(expandedImage, View.SCALE_Y, startScale, 1f))
            set.duration = mShortAnimationDuration
            set.interpolator = DecelerateInterpolator()
            set.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    mCurrentAnimator = null
                }

                override fun onAnimationCancel(animation: Animator) {
                    mCurrentAnimator = null
                }
            })
            set.start()
            mCurrentAnimator = set

        // Upon clicking the zoomed-in image, it should zoom back down
        // to the original bounds and show the thumbnail instead of
        // the expanded image.
        startScaleFinal = startScale
    }

    fun closeExpandedImage(expandedImage: View) {
        mCurrentAnimator?.cancel()

        // Animate the four positioning/sizing properties in parallel,
        // back to their original values.
        val set = AnimatorSet()
        set.play(ObjectAnimator.ofFloat(expandedImage, View.X, startBounds!!.left.toFloat()))
                .with(ObjectAnimator.ofFloat(expandedImage, View.Y, startBounds!!.top.toFloat()))
                .with(ObjectAnimator.ofFloat(expandedImage, View.SCALE_X, startScaleFinal))
                .with(ObjectAnimator.ofFloat(expandedImage, View.SCALE_Y, startScaleFinal))
        set.duration = mShortAnimationDuration
        set.interpolator = DecelerateInterpolator()
        set.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                thumbView!!.alpha = 1f
                expandedImage.visibility = View.GONE
                mCurrentAnimator = null
                isExpandedImage = false
            }

            override fun onAnimationCancel(animation: Animator) {
                thumbView!!.alpha = 1f
                expandedImage.visibility = View.GONE
                mCurrentAnimator = null
            }
        })
        set.start()
        mCurrentAnimator = set
    }

    fun zoomDetailPostImage(thumbView: View, expandedImage: View, post: Post) {
        zoomImageFromThumb(thumbView, expandedImage, post.titleImage!!)
    }

    fun zoomDetailPostImage(thumbView: View, expandedImage: View, post: PostContentData?) {
        zoomImageFromThumb(thumbView, expandedImage, post!!.content!!)
    }

    fun handleBack() {
        if (isExpandedImage) {
            closeExpandedImage.call()
        } else {
            goBack.call()
        }
    }

    private fun displayExpandedImage(content: String) {
        displayExpandedImage.value = content
    }
}

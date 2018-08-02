package es.chewiegames.bloggie.interactor.detailPost

import android.view.View
import android.widget.ImageView
import es.chewiegames.bloggie.model.Post
import javax.inject.Inject
import android.animation.Animator
import android.graphics.Rect
import com.google.firebase.database.DatabaseReference
import javax.inject.Named
import android.animation.AnimatorListenerAdapter
import android.view.animation.DecelerateInterpolator
import android.animation.ObjectAnimator
import android.animation.AnimatorSet
import android.graphics.Point


class DetailPostInteractor @Inject constructor() : IDetailPostInteractor {

    private var mShortAnimationDuration: Long = 0L
    private var mCurrentAnimator: Animator? = null
    private var thumbView: View? = null
    private var startScaleFinal: Float = 0f
    private var startScale: Float = 0f
    private var startBounds: Rect? = null
    private var isExpandedImage: Boolean = false

    @field:[Inject Named("all posts")]
    lateinit var mDatabaseAllPosts: DatabaseReference

    @field:[Inject Named("post by user")]
    lateinit var mDatabasePostByUser: DatabaseReference

    override fun setCurrentAnimatorDuration(mShortAnimationDuration: Int) {
        this.mShortAnimationDuration = mShortAnimationDuration.toLong()

    }

    override fun zoomImageFromThumb(thumbView: View, expandedImage: ImageView, content: String, listener: IDetailPostInteractor.InteractorListener) {
        this.thumbView = thumbView
        // If there's an animation in progress, cancel it
        // immediately and proceed with this one.
        if (mCurrentAnimator != null) {
            mCurrentAnimator!!.cancel()
        }
        // Load the high-resolution "zoomed-in" image.
        listener.displayExpandedImage(content)

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

    override fun closeExpandedImage(expandedImage: ImageView) {
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

    override fun isExpandedImage(): Boolean {
        return isExpandedImage
    }

    override fun addViewToPost(post: Post) {
        post.views = post.views + 1
        mDatabaseAllPosts.child(post.id!!).setValue(post)
        mDatabasePostByUser.child(post.id!!).setValue(post)
    }
}
package es.chewiegames.bloggie.presenter.detailPost

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import es.chewiegames.bloggie.interactor.detailPost.DetailPostInteractor
import es.chewiegames.bloggie.interactor.detailPost.IDetailPostInteractor
import es.chewiegames.bloggie.model.PostContent
import es.chewiegames.bloggie.ui.detailPost.DetailPostView
import es.chewiegames.bloggie.util.EXTRA_POST
import javax.inject.Inject
import es.chewiegames.bloggie.model.Post

class DetailPostPresenter @Inject constructor(): IDetailPostPresenter, IDetailPostInteractor.InteractorListener {

    private lateinit var view: DetailPostView

    private var mPost: Post? = null

    @Inject
    lateinit var mDetailPostInteractor: DetailPostInteractor

    @Inject
    lateinit var context: Context

    override fun setView(view: DetailPostView) {
        this.view = view
    }

    override fun loadData(extras: Bundle) {
        val post : Post? = extras.getSerializable(EXTRA_POST) as Post
        if (post != null){
            mPost = post
            view.fillValues(post)
            if(post.content.isNotEmpty()){
                view.setAdapter(post.content)
            }
        }
    }

    override fun setCurrentAnimatorDuration() {
        val mShortAnimationDuration = context.resources.getInteger(android.R.integer.config_shortAnimTime)
        mDetailPostInteractor.setCurrentAnimatorDuration(mShortAnimationDuration)
    }

    override fun zoomDetailPostImage(thumbView: View, expandedImage: ImageView, post: Post) {
        mDetailPostInteractor.zoomImageFromThumb(thumbView, expandedImage, post.titleImage!!, this)
    }

    override fun zoomDetailPostImage(thumbView: View, expandedImage: ImageView, post: PostContent?) {
        mDetailPostInteractor.zoomImageFromThumb(thumbView, expandedImage, post!!.content!!, this)
    }

    override fun closeExpandedImage(expandedImage: ImageView) {
        mDetailPostInteractor.closeExpandedImage(expandedImage)
    }

    override fun handleBack() {
        if(mDetailPostInteractor.isExpandedImage()){
            view.closeExpandedImage()
        }else{
            view.goBack()
        }
    }

    override fun getPost(): Post? {
        return  mPost
    }

    //interactor listener
    override fun displayExpandedImage(content: String) {
        view.displayExpandedImage(content)
    }
}
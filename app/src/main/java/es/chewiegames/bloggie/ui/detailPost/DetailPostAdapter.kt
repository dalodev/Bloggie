package es.chewiegames.bloggie.ui.detailPost

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import es.chewiegames.bloggie.R
import es.chewiegames.bloggie.model.PostContent
import es.chewiegames.bloggie.ui.newPost.PostImageViewHolder
import es.chewiegames.bloggie.ui.newPost.PostTextViewHolder
import javax.inject.Inject
import es.chewiegames.bloggie.util.*


class DetailPostAdapter @Inject constructor() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var postContent: ArrayList<PostContent>

    @Inject
    lateinit var context: Context

    @Inject
    lateinit var mListener: DetailPostAdapterListener

    override fun getItemViewType(position: Int): Int {
        return postContent!![position].viewType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TEXT_VIEW, EDITTEXT_VIEW -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.textview_content, parent, false)
                PostTextViewHolder(view, true)
            }
            IMAGE_VIEW -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.imageview_content, parent, false)
                PostImageViewHolder(view, true)
            }
            else -> {
                PostTextViewHolder(null, true)
            }
        }
    }

    override fun getItemCount(): Int {
        return postContent!!.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val content = postContent[position]

        when (content.viewType) {
            TEXT_VIEW -> {
                (holder as PostTextViewHolder).foregroundView!!.visibility = View.VISIBLE
                holder.textContent!!.visibility = View.VISIBLE
                holder.editTextLayout!!.visibility = View.GONE
                holder.okButton!!.visibility = View.GONE
                holder.textContent!!.text = content.content
            }
            IMAGE_VIEW -> {
                if (content.content != null) {
                    displayImage(holder as PostImageViewHolder, holder.mProgressBar, content.content!!)
                    holder.foregroundView.setOnClickListener { mListener.onClickImage(holder.image!!, postContent[holder.getAdapterPosition()]) }
                }
            }
        }

    }

    /**
     * This method display an image into view with specific size
     *
     * @param holder item views to show
     */
    private fun displayImage(holder: PostImageViewHolder, pb: ProgressBar, urlImage: String?) {
        val size = Math.ceil(Math.sqrt((MAX_WIDTH * MAX_HEIGHT).toDouble())).toInt()
        holder.image!!.post {
            if (urlImage != null) {
                Picasso.with(context)
                        .load(urlImage)
                        .transform(BitmapTransform(MAX_WIDTH, MAX_HEIGHT))
                        .resize(size, size)
                        .centerInside()
                        .into(holder.image, object : Callback {
                            override fun onSuccess() {
                                pb.visibility = View.GONE
                                holder.foregroundView.visibility = View.VISIBLE
                            }

                            override fun onError() {
                                pb.visibility = View.GONE
                                holder.foregroundView.visibility = View.VISIBLE
                            }
                        })
            }else{
                //TODO error no image
            }
        }

    }

    fun setPostContent(content: ArrayList<PostContent>) {
        this.postContent = content
    }

    interface DetailPostAdapterListener {
        fun onClickImage(thumbView: View, postContent: PostContent)
    }
}
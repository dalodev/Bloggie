package es.chewiegames.bloggie.ui.detailPost

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import es.chewiegames.bloggie.R
import es.chewiegames.bloggie.ui.newPost.PostImageViewHolder
import es.chewiegames.bloggie.ui.newPost.PostTextViewHolder
import es.chewiegames.bloggie.util.EDITTEXT_VIEW
import es.chewiegames.bloggie.util.IMAGE_VIEW
import es.chewiegames.bloggie.util.TEXT_VIEW
import es.chewiegames.bloggie.viewmodel.DetailPostViewModel
import es.chewiegames.data.model.PostContentData
import es.chewiegames.domain.model.PostContent
import javax.inject.Inject

class DetailPostAdapter @Inject constructor(private val viewModel: DetailPostViewModel) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var postContent: ArrayList<PostContent>

    @Inject
    lateinit var context: Context

    @Inject
    lateinit var mListener: DetailPostAdapterListener

    override fun getItemViewType(position: Int): Int {
        return when (postContent[position].viewType) {
            TEXT_VIEW -> R.layout.textview_content
            EDITTEXT_VIEW -> R.layout.edittext_content
            IMAGE_VIEW -> R.layout.imageview_content
            else -> postContent[position].viewType
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, viewType, parent, false)
        return when (viewType) {
            TEXT_VIEW, EDITTEXT_VIEW -> PostTextViewHolder(binding, false)
            IMAGE_VIEW -> PostImageViewHolder(binding, false)
            else -> PostTextViewHolder(binding, false)
        }
    }

    override fun getItemCount(): Int {
        return postContent!!.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val content = postContent[position]

        /* when (content.viewType) {
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
         }*/
    }

    /**
     * This method display an image into view with specific size
     *
     * @param holder item views to show
     */
    /*private fun displayImage(holder: PostImageViewHolder, pb: ProgressBar, urlImage: String?) {
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

    }*/

    fun setPostContent(content: ArrayList<PostContent>) {
        this.postContent = content
    }

    interface DetailPostAdapterListener {
        fun onClickImage(thumbView: View, postContent: PostContentData)
    }
}

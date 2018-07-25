package es.chewiegames.bloggie.ui.newPost

import android.content.Context
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import es.chewiegames.bloggie.R
import es.chewiegames.bloggie.model.PostContent
import javax.inject.Inject
import com.squareup.picasso.Picasso
import android.widget.ProgressBar
import com.squareup.picasso.Callback
import es.chewiegames.bloggie.util.*
import java.util.*

class PostAdapter @Inject constructor() : RecyclerView.Adapter<RecyclerView.ViewHolder>(), ItemTouchHelperAdapter {

    interface PostAdapterListener {
        fun onAddTextContent(content: PostContent, textContent: String)

        fun onEditTextContent(content: PostContent)

        fun onItemSwiped(deletedItem: PostContent, deletedIndex: Int)

        fun onImageClicked(content: PostContent)
    }

    @Inject
    lateinit var context: Context

    @Inject
    lateinit var mListener: PostAdapterListener

    @Inject
    lateinit var postContent: ArrayList<PostContent>

    override fun getItemViewType(position: Int): Int {
        return postContent[position].viewType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TEXT_VIEW, EDITTEXT_VIEW -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.textview_content, parent, false)
                PostTextViewHolder(view, false)
            }
            IMAGE_VIEW -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.imageview_content, parent, false)
                PostImageViewHolder(view, false)
            }
            else -> {
                PostTextViewHolder(null, false)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val content = postContent[position]
        when (content.viewType) {
            TEXT_VIEW -> {
                (holder as PostTextViewHolder).textContent!!.visibility = View.VISIBLE
                holder.editTextLayout!!.visibility = View.GONE
                holder.okButton!!.visibility = View.GONE
                holder.textContent!!.text = content.content
                holder.container!!.setOnClickListener { mListener.onEditTextContent(postContent[holder.adapterPosition]) }
            }
            EDITTEXT_VIEW -> {
                (holder as PostTextViewHolder).editTextContent!!.setText(content.content)
                holder.textContent!!.visibility = View.GONE
                holder.editTextLayout!!.visibility = View.VISIBLE
                holder.okButton!!.visibility = View.VISIBLE
                holder.okButton!!.setOnClickListener { mListener.onAddTextContent(content, holder.editTextContent!!.text.toString()) }
            }
            IMAGE_VIEW -> {
                if (content.content != null) {
                    displayImage(holder as PostImageViewHolder, holder.mProgressBar, null, content.content)
                } else if (content.uriImage != null) {
                    displayImage(holder as PostImageViewHolder, holder.mProgressBar, Uri.parse(content.uriImage), null)
                }
                (holder as PostImageViewHolder).image!!.setOnClickListener { mListener.onImageClicked(postContent[holder.adapterPosition]) }
            }
        }
    }

    override fun getItemCount(): Int {
        return postContent.size
    }

    /**
     * This method display an image into view with specific size
     *
     * @param holder   item views to show
     * @param imageUri Uri image to display
     */
    private fun displayImage(holder: PostImageViewHolder, pb: ProgressBar, imageUri: Uri?, urlImage: String?) {
        val size = Math.ceil(Math.sqrt((MAX_WIDTH * MAX_HEIGHT).toDouble())).toInt()
        holder.image!!.post {
            when {
                urlImage != null -> Picasso.with(context)
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
                imageUri != null -> Picasso.with(context)
                        .load(imageUri)
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
                else -> {
                    //TODO display error image
                }
            }
        }
    }

    fun restoreItem(item: PostContent, position: Int) {
        postContent.add(position, item)
        // notify item added by position
        notifyItemInserted(position)
    }

    fun removeItem(position: Int) {
        postContent.removeAt(position)
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position)
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        postContent[fromPosition].position = toPosition
        postContent[toPosition].position = fromPosition
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(postContent, i, i + 1)
                postContent[i].position = i + 1
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(postContent, i, i - 1)
                postContent[i].position = i - 1
            }
        }
        notifyItemMoved(fromPosition, toPosition)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int, position: Int) {
        val deletedItem: PostContent = postContent[viewHolder.adapterPosition]
        val deletedIndex: Int = viewHolder.adapterPosition
        mListener.onItemSwiped(deletedItem, deletedIndex)
    }
}
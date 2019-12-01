package es.chewiegames.bloggie.ui.newPost

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import es.chewiegames.bloggie.R
import es.chewiegames.bloggie.util.EDITTEXT_VIEW
import es.chewiegames.bloggie.util.IMAGE_VIEW
import es.chewiegames.bloggie.util.TEXT_VIEW
import es.chewiegames.bloggie.viewmodel.NewPostViewModel
import es.chewiegames.domain.model.PostContent
import java.util.Collections
import kotlin.collections.ArrayList

class PostAdapter(
    private val viewModel: NewPostViewModel,
    private var postContent: ArrayList<PostContent> = viewModel.postContent.value ?: arrayListOf()
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), ItemTouchHelperAdapter {

    override fun getItemViewType(position: Int): Int {
        return when (postContent[position].viewType) {
            TEXT_VIEW -> R.layout.textview_content
            EDITTEXT_VIEW -> R.layout.edittext_content
            IMAGE_VIEW -> R.layout.imageview_content
            else -> R.layout.textview_content
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, viewType, parent, false)
        return when (viewType) {
            R.layout.edittext_content, R.layout.textview_content -> PostTextViewHolder(binding, false)
            R.layout.imageview_content -> PostImageViewHolder(binding, false)
            else -> PostTextViewHolder(binding, false)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val content = postContent[position]
        when (content.viewType) {
            TEXT_VIEW -> (holder as PostTextViewHolder).bind(content, viewModel, holder.adapterPosition)
            EDITTEXT_VIEW -> (holder as PostTextViewHolder).bind(content, viewModel, holder.adapterPosition)
            IMAGE_VIEW -> (holder as PostImageViewHolder).bind(content, viewModel, holder.adapterPosition)
        }
    }

    override fun getItemCount() = postContent.size

    fun restoreItem(item: PostContent) {
        val position = item.position
        postContent.add(position, item)
        // notify item added by position
        notifyItemInserted(position)
    }

    private fun removeItem(position: Int) {
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
        removeItem(deletedIndex)
        viewModel.itemSwiped(deletedItem, deletedIndex)
    }

    fun updateAdapterView(content: PostContent) {
        notifyItemChanged(content.position)
    }

    fun removeContent(content: PostContent) {
        notifyItemRemoved(content.position)
    }

    fun addItem(content: PostContent) {
        notifyItemInserted(itemCount - 1)
    }
}

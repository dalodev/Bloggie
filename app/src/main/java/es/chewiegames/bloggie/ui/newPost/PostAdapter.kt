package es.chewiegames.bloggie.ui.newPost

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import es.chewiegames.bloggie.R
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import es.chewiegames.bloggie.util.*
import es.chewiegames.bloggie.viewmodel.NewPostViewModel
import es.chewiegames.domain.model.PostContent
import java.util.*
import kotlin.collections.ArrayList

class PostAdapter(private val viewModel: NewPostViewModel,
                  private var postContent: ArrayList<PostContent> = viewModel.postContent.value ?: arrayListOf())
    : RecyclerView.Adapter<RecyclerView.ViewHolder>(), ItemTouchHelperAdapter {

    override fun getItemViewType(position: Int): Int {
        return when (postContent[position].viewType) {
            TEXT_VIEW -> R.layout.textview_content
            EDITTEXT_VIEW -> R.layout.edittext_content
            IMAGE_VIEW -> R.layout.imageview_content
            else ->  R.layout.textview_content
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
        val position = postContent.indexOf(item)
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

    fun updateAdapterView(position: Int) {
        notifyItemChanged(position)
    }

    fun removeContent(position: Int) {
        notifyItemRemoved(position)
    }

    fun addItem() {
        notifyItemInserted(itemCount - 1)
    }
}
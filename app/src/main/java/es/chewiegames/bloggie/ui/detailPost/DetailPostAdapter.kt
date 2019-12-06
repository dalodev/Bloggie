package es.chewiegames.bloggie.ui.detailPost

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import es.chewiegames.bloggie.R
import es.chewiegames.bloggie.util.IMAGE_VIEW
import es.chewiegames.bloggie.util.TEXT_VIEW
import es.chewiegames.bloggie.viewmodel.DetailPostViewModel
import es.chewiegames.domain.model.PostContent

class DetailPostAdapter(
    val viewModel: DetailPostViewModel,
    var postContent: ArrayList<PostContent> = viewModel.postContent.value ?: arrayListOf()
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return when (postContent[position].viewType) {
            TEXT_VIEW -> R.layout.detail_textview_content
            IMAGE_VIEW -> R.layout.detail_imageview_content
            else -> R.layout.detail_textview_content
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, viewType, parent, false)
        return when (viewType) {
            R.layout.detail_textview_content -> DetailPostTextViewHolder(binding, true)
            R.layout.detail_imageview_content -> DetailPostImageViewHolder(binding, true)
            else -> DetailPostTextViewHolder(binding, true)
        }
    }

    override fun getItemCount() = postContent.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val content = postContent[position]
        when (content.viewType) {
            TEXT_VIEW -> (holder as DetailPostTextViewHolder).bind(content, viewModel, holder.adapterPosition)
            IMAGE_VIEW -> (holder as DetailPostImageViewHolder).bind(content, viewModel)
            else -> (holder as DetailPostTextViewHolder).bind(content, viewModel, holder.adapterPosition)
        }
    }
}

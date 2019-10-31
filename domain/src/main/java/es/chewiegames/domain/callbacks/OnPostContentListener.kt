package es.chewiegames.domain.callbacks

interface OnPostContentListener {
    fun onChangeViewType(viewType: Int, position: Int)
    fun removeContent(position: Int)
    fun onAddImageContent()
    fun onChangeImageContent(position: Int)
}
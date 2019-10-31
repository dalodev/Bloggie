package es.chewiegames.data.callbacks

interface OnPostContentCallback {
    fun onChangeViewType(viewType: Int, position: Int)
    fun removeContent(position: Int)
    fun onAddImageContent()
    fun onChangeImageContent(position: Int)
}
/*
 * Copyright 2019 littledavity
 */
package es.littledavity.data.callbacks

interface OnPostContentCallback {
    fun onChangeViewType(viewType: Int, position: Int)
    fun removeContent(position: Int)
    fun onAddImageContent()
    fun onChangeImageContent(position: Int)
}

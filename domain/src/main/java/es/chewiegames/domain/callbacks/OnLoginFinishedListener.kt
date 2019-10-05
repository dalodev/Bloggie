package es.chewiegames.domain.callbacks

interface OnLoginFinishedListener {
    fun onError(message: String)
    fun onSuccess()
    fun showProgressDialog()
    fun hideProgressDialog()
}
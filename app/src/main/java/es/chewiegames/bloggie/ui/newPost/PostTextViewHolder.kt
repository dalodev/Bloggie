package es.chewiegames.bloggie.ui.newPost

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.RelativeLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.widget.EditText
import android.widget.TextView
import androidx.cardview.widget.CardView
import kotlinx.android.synthetic.main.edittext_add_content.view.*
import kotlinx.android.synthetic.main.textview_content.view.*

class PostTextViewHolder constructor(rootView: View?, isDetailPost: Boolean) : RecyclerView.ViewHolder(rootView!!) {

    var textContent: TextView? = rootView!!.textview
    var editTextLayout: RelativeLayout? = rootView!!.sendMsgLayout
    var foregroundView: CardView? = rootView!!.foregroundView
    var editTextContent: EditText? = rootView!!.editTextContent
    var okButton: FloatingActionButton? = rootView!!.okButton
    var container: View? = rootView!!.rootView
}
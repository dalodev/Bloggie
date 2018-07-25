package es.chewiegames.bloggie.ui.newPost

import android.graphics.Canvas
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import es.chewiegames.bloggie.R
import android.graphics.Paint
import android.support.v7.widget.CardView
import es.chewiegames.bloggie.util.Utils


class SimpleItemTouchHelperCallback constructor(dragDirs: Int, swipeDirs: Int) : ItemTouchHelper.SimpleCallback(dragDirs, swipeDirs) {

    var adapter: ItemTouchHelperAdapter? = null
    var paintLeft: Paint? = null
    var paintRight: Paint? = null
    var drawableLeft: Drawable? = null
    var drawableRight: Drawable? = null
    private var swipeEnabled: Boolean = false

    constructor(builder: Builder) : this(builder.dragDirs, builder.swipeDirs) {
        paintLeft = Paint(Paint.ANTI_ALIAS_FLAG)
        paintRight = Paint(Paint.ANTI_ALIAS_FLAG)
        setPaintColor(paintLeft!!, builder.bgColorSwipeLeft)
        setPaintColor(paintRight!!, builder.bgColorSwipeRight)
        drawableLeft = builder.drawableSwipeLeft
        drawableRight = builder.drawableSwipeRight
        swipeEnabled = builder.swipeEnabled
        this.adapter = builder.adapter
    }

    private fun setPaintColor(paint: Paint, color: Int) {
        paint.color = color
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return true
    }

    override fun isLongPressDragEnabled(): Boolean {
        return true
    }

    override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?): Boolean {
        adapter!!.onItemMove(viewHolder!!.adapterPosition, target!!.adapterPosition)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
        adapter!!.onSwiped(viewHolder!!, direction, viewHolder.adapterPosition)
    }

    override fun onChildDraw(c: Canvas?, recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {

        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

            val itemView = viewHolder!!.itemView
            val cardView : CardView = itemView.findViewById(R.id.foregroundView)

            //background points
            val height = itemView.bottom.toFloat() - itemView.top.toFloat()
            val width = height / 3
            val cardViewHeight = cardView.getBottom().toFloat() - cardView.getTop().toFloat()
            val cardViewWidth = cardViewHeight / 3
            val top = itemView.top.toFloat() + cardView.getTop().toFloat()
            val bottom = top + (cardView.getBottom() - cardView.getTop())
            val left = cardView.getLeft().toFloat()
            val right = cardView.getRight().toFloat()

            if (dX > 0) {
                val background = RectF(left, top, right + dX, bottom)
                val iconDest = RectF(left + cardViewWidth, top + cardViewWidth, left + 2 * cardViewWidth, bottom - cardViewWidth)
                c!!.drawRect(background, paintLeft)
                c.drawBitmap(Utils.getBitmap(drawableLeft!!), null, iconDest, paintLeft)
            } else if (dX < 0) {
                val background = RectF(right + dX, top, right, bottom)
                val iconDest = RectF(right - 2 * cardViewWidth, top + cardViewWidth, right - cardViewWidth, bottom - cardViewWidth)
                c!!.drawRect(background, paintRight)
                c.drawBitmap(Utils.getBitmap(drawableRight!!), null, iconDest, paintRight)
            }
        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

    override fun convertToAbsoluteDirection(flags: Int, layoutDirection: Int): Int {
        return super.convertToAbsoluteDirection(flags, layoutDirection)
    }

    class Builder(var dragDirs: Int, var swipeDirs: Int) {
        var drawableSwipeLeft: Drawable? = null
        var drawableSwipeRight: Drawable? = null
        var bgColorSwipeLeft: Int = 0
        var bgColorSwipeRight: Int = 0
        var swipeEnabled: Boolean = false
        var adapter: ItemTouchHelperAdapter? = null

        fun drawableSwipeLeft(`val`: Drawable): Builder {
            drawableSwipeLeft = `val`
            return this
        }

        fun drawableSwipeRight(`val`: Drawable): Builder {
            drawableSwipeRight = `val`
            return this
        }

        fun bgColorSwipeLeft(`val`: Int): Builder {
            bgColorSwipeLeft = `val`
            return this
        }

        fun bgColorSwipeRight(`val`: Int): Builder {
            bgColorSwipeRight = `val`
            return this
        }

        fun setSwipeEnabled(`val`: Boolean): Builder {
            swipeEnabled = `val`
            return this
        }

        fun setAdapter(`val`: ItemTouchHelperAdapter): Builder {
            adapter = `val`
            return this
        }

        fun build(): SimpleItemTouchHelperCallback {
            return SimpleItemTouchHelperCallback(this)
        }
    }
}
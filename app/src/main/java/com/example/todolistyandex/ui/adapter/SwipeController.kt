package com.example.todolistyandex.ui.adapter

import android.graphics.Canvas
import android.graphics.Paint
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.todolistyandex.R

class SwipeController(private val adapter: ListOfTaskAdapter) :
    ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        val task = adapter.currentList[position]

        when (direction) {
            ItemTouchHelper.RIGHT -> {
                task.completeFlag = !task.completeFlag
                adapter.notifyItemChanged(position)
            }

            ItemTouchHelper.LEFT -> {
                adapter.removeItem(position)
            }
        }
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val itemView: View = viewHolder.itemView
        val paint = Paint()

        if (dX > 0) { // Swiping to the right
            paint.color = ContextCompat.getColor(itemView.context, R.color.color_green)
            c.drawRect(
                itemView.left.toFloat(),
                itemView.top.toFloat(),
                itemView.left.toFloat() + dX,
                itemView.bottom.toFloat(),
                paint
            )
            ContextCompat.getDrawable(itemView.context, R.drawable.ic_done)?.let {
                val iconMargin = (itemView.height - it.intrinsicHeight) / 2
                val iconTop = itemView.top + (itemView.height - it.intrinsicHeight) / 2
                it.setBounds(
                    itemView.left + iconMargin,
                    iconTop,
                    itemView.left + iconMargin + it.intrinsicWidth,
                    iconTop + it.intrinsicHeight
                )
                it.draw(c)
            }
        } else if (dX < 0) { // Swiping to the left
            paint.color = ContextCompat.getColor(itemView.context, R.color.color_red)
            c.drawRect(
                itemView.right.toFloat() + dX,
                itemView.top.toFloat(),
                itemView.right.toFloat(),
                itemView.bottom.toFloat(),
                paint
            )
            ContextCompat.getDrawable(itemView.context, R.drawable.ic_delete)?.let {
                val iconMargin = (itemView.height - it.intrinsicHeight) / 2
                val iconTop = itemView.top + (itemView.height - it.intrinsicHeight) / 2
                it.setBounds(
                    itemView.right - iconMargin - it.intrinsicWidth,
                    iconTop,
                    itemView.right - iconMargin,
                    iconTop + it.intrinsicHeight
                )
                it.draw(c)
            }
        }

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }
}

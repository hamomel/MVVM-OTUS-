package com.example.mvvm

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class SwipeToDeleteCallback(
    private val context: Context,
    private val onItemRemoved: (Int) -> Unit
) : ItemTouchHelper.Callback() {
    private val background = ColorDrawable(ContextCompat.getColor(context, R.color.chat_item_background))
    private val icon = ContextCompat.getDrawable(context, R.drawable.ic_delete)!!
    private val iconWidth = icon.intrinsicWidth
    private val iconHeight = icon.intrinsicHeight
    private val iconMargin = context.resources.getDimensionPixelSize(R.dimen.delete_button_margin)

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int =
        makeMovementFlags(0, ItemTouchHelper.LEFT)

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean = false

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        onItemRemoved.invoke(position)
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
        val itemView = viewHolder.itemView

        background.setBounds(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
        background.draw(c)

        val iconLeft = itemView.right - iconWidth - iconMargin
        val iconRight = itemView.right - iconMargin
        val verticalMargin = (itemView.height - iconHeight) / 2
        val iconTop = itemView.top + verticalMargin
        val iconBottom = itemView.bottom - verticalMargin
        icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
        icon.draw(c)

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }
}
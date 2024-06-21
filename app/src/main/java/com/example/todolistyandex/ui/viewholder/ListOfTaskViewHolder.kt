package com.example.todolistyandex.ui.viewholder

import android.content.Context
import android.graphics.Paint
import android.view.View
import android.widget.CheckBox
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.todolistyandex.data.model.ListOfTaskStatus
import com.example.todolistyandex.R
import com.example.todolistyandex.databinding.ResultOfBusinessesBinding
import com.example.todolistyandex.data.model.ToDoItem
import com.example.todolistyandex.ui.fragment.ListOfTaskFragmentDirections

class ListOfTaskViewHolder(
    private val binding: ResultOfBusinessesBinding,
    private val listener: OnTaskCompleteListener
) : RecyclerView.ViewHolder(binding.root) {

    private val checkBox: CheckBox = binding.checkBox
    private val taskTextView = binding.taskTextView
    private val infoImageView = binding.infoImageView

    fun bind(toDoItem: ToDoItem) {
        taskTextView.text = toDoItem.text

        if (toDoItem.deadlineComplete.isNullOrEmpty()) {
            binding.dataText.text = itemView.context.getString(R.string.date)
            binding.dataText.visibility = View.VISIBLE
        } else {
            binding.dataText.text = toDoItem.deadlineComplete
            binding.dataText.visibility = View.VISIBLE
        }

        val context: Context = binding.root.context

        updateTaskAppearance(context, toDoItem)

        checkBox.isChecked = toDoItem.completeFlag

        checkBox.setOnClickListener {
            toDoItem.completeFlag = checkBox.isChecked
            updateTaskAppearance(context, toDoItem)
            listener.onTaskComplete(adapterPosition, checkBox.isChecked)
        }

        infoImageView.setOnClickListener {
            val action =
                ListOfTaskFragmentDirections.actionListOfTaskFragmentToCreateNewTaskFragment(
                    toDoItem.id
                )
            it.findNavController().navigate(action)
        }
    }

    private fun updateTaskAppearance(context: Context, toDoItem: ToDoItem) {
        if (toDoItem.completeFlag) {
            checkBox.buttonTintList = ContextCompat.getColorStateList(context, R.color.color_green)
            taskTextView.setTextColor(ContextCompat.getColor(context, R.color.label_tertiary))
            taskTextView.paintFlags = taskTextView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            taskTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null)
        } else {
            checkBox.buttonTintList = ContextCompat.getColorStateList(context, R.color.color_grey)
            taskTextView.setTextColor(
                ContextCompat.getColor(
                    context,
                    android.R.color.primary_text_light
                )
            )
            taskTextView.paintFlags = taskTextView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()

            when (toDoItem.priority) {
                ListOfTaskStatus.HIGH -> {
                    taskTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        ContextCompat.getDrawable(context, R.drawable.high_prioritet),
                        null,
                        null,
                        null
                    )
                    checkBox.buttonTintList =
                        ContextCompat.getColorStateList(context, R.color.color_red)
                    checkBox.setButtonDrawable(R.drawable.high_checkbox)
                }

                ListOfTaskStatus.LOW -> {
                    taskTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        ContextCompat.getDrawable(context, R.drawable.low_prioritet),
                        null,
                        null,
                        null
                    )
                    checkBox.setButtonDrawable(R.drawable.low_checkbox)
                }

                else -> {
                    taskTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        null,
                        null,
                        null,
                        null
                    )
                    checkBox.setButtonDrawable(R.drawable.low_checkbox)
                }
            }
        }
    }
}

interface OnTaskCompleteListener {
    fun onTaskComplete(position: Int, isComplete: Boolean)
}

package com.example.todolistyandex.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.todolistyandex.ui.viewholder.ListOfTaskViewHolder
import com.example.todolistyandex.ui.viewholder.OnTaskCompleteListener
import com.example.todolistyandex.databinding.ResultOfBusinessesBinding
import com.example.todolistyandex.data.model.ToDoItem

class ListOfTaskAdapter :
    ListAdapter<ToDoItem, ListOfTaskViewHolder>(ToDoItemDiffCallback()), OnTaskCompleteListener {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListOfTaskViewHolder {
        val binding =
            ResultOfBusinessesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListOfTaskViewHolder(binding, this)
    }

    override fun onBindViewHolder(holder: ListOfTaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onTaskComplete(position: Int, isComplete: Boolean) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            currentItem.completeFlag = isComplete
            notifyItemChanged(position)
        }
    }

    fun removeItem(position: Int) {
        val currentList = currentList.toMutableList()
        currentList.removeAt(position)
        submitList(currentList)
    }
}

class ToDoItemDiffCallback : DiffUtil.ItemCallback<ToDoItem>() {
    override fun areItemsTheSame(oldItem: ToDoItem, newItem: ToDoItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ToDoItem, newItem: ToDoItem): Boolean {
        return oldItem == newItem
    }
}

package com.dodemy.roomcrudapp.ui.tasklist

import com.dodemy.roomcrudapp.data.entities.Task

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dodemy.roomcrudapp.databinding.ItemTaskBinding



class TaskListAdapter(
    private val onItemClickListener: (Task) -> Unit,
    private val onDeleteClickListener: (Task) -> Unit
) : ListAdapter<Task, TaskListAdapter.TaskViewHolder>(TaskDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TaskViewHolder(private val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                root.setOnClickListener {
                    val position = bindingAdapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        onItemClickListener(getItem(position))
                    }
                }
                btnDeleteTask.setOnClickListener {
                    val position = bindingAdapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        onDeleteClickListener(getItem(position))
                    }
                }
            }
        }

        fun bind(task: Task) {
            binding.apply {
                tvTaskTitle.text = task.title
                tvTaskDescription.text = task.description
                cbTaskCompleted.isChecked = task.isCompleted
            }
        }
    }

    class TaskDiffCallback : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }
    }
}

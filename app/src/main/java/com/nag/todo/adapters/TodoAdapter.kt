package com.nag.todo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nag.todo.R
import com.nag.todo.model.TodoEntity

class TodoAdapter(private val onItemClickListener: (TodoEntity) -> Unit)
    : ListAdapter<TodoEntity, TodoAdapter.TodoHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.todo_item,parent,false)
        return TodoHolder(itemView)
    }

    override fun onBindViewHolder(holder: TodoHolder, position: Int) {
        with(getItem(position)) {
            holder.tvTitle.text = title
            holder.tvDescription.text = content
        }
    }

    fun getTodoEntityAt(position: Int) = getItem(position)


    inner class TodoHolder(view:View):RecyclerView.ViewHolder(view){
        val tvTitle: TextView = itemView.findViewById(R.id.todo_title)
        val tvDescription: TextView = itemView.findViewById(R.id.todo_description)
    }
}

private val diffCallback = object : DiffUtil.ItemCallback<TodoEntity>() {
    override fun areItemsTheSame(oldItem: TodoEntity, newItem: TodoEntity) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: TodoEntity, newItem: TodoEntity) =
                oldItem.title == newItem.title
                && oldItem.content == newItem.content
}
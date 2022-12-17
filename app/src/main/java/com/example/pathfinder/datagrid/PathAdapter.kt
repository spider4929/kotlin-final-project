package com.example.pathfinder.datagrid

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pathfinder.database.Path
import com.example.pathfinder.databinding.ListItemPathBinding

class PathAdapter(val clickListener: PathListener) : ListAdapter<Path, PathAdapter.ViewHolder>(PathDiffCallback()){

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        holder.bind(getItem(position)!!, clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ListItemPathBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: Path, clickListener: PathListener) {
            binding.path = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemPathBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class PathDiffCallback : DiffUtil.ItemCallback<Path>() {

    override fun areItemsTheSame(oldItem: Path, newItem: Path): Boolean {
        return oldItem.pathId == newItem.pathId
    }

    override fun areContentsTheSame(oldItem: Path, newItem: Path): Boolean {
        return oldItem == newItem
    }
}


class PathListener(val clickListener: (pathId: Long) -> Unit) {
    fun onClick(path: Path) = clickListener(path.pathId)
}
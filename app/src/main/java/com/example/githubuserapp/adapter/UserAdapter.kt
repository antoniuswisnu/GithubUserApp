package com.example.githubuserapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserapp.data.response.ItemsItem
import com.example.githubuserapp.databinding.ItemUserBinding

class UserAdapter(private val onItemClickListener: OnItemClickListener) : ListAdapter<ItemsItem, UserAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val pengguna = getItem(position)
        holder.bind(pengguna, onItemClickListener)
        holder.itemView.setOnClickListener {
            onItemClickListener.onItemClick(pengguna)
        }
    }

    class MyViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(users: ItemsItem, onItemClickListener: OnItemClickListener?){
            val avatarUrl = users.avatarUrl
            Glide.with(binding.imgUser)
                .load(avatarUrl)
                .into(binding.imgUser)
            binding.tvUser.text = users.login
            itemView.setOnClickListener {
                onItemClickListener?.onItemClick(users)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(user: ItemsItem)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}

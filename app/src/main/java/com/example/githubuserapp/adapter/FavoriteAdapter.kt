package com.example.githubuserapp.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserapp.data.response.ItemsItem
import com.example.githubuserapp.databinding.ItemUserBinding
import com.example.githubuserapp.helper.FavoriteDiffCallback

class FavoriteAdapter(private var listUser: List<ItemsItem>) :
    RecyclerView.Adapter<FavoriteAdapter.ListViewHolder>() {
        private lateinit var onItemClickCallback: OnItemClickCallback

        fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
            this.onItemClickCallback = onItemClickCallback
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
            val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ListViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
            val user = listUser[position]
            val photo = user.avatarUrl
            val username = user.login

            holder.binding.tvUser.text = username

            Glide.with(holder.itemView.context)
                .load(photo)
                .into(holder.binding.imgUser)

            holder.itemView.setOnClickListener {
                onItemClickCallback.onItemClicked(listUser[holder.adapterPosition])
            }
        }

        override fun getItemCount(): Int = listUser.size
        @SuppressLint("NotifyDataSetChanged")
        fun setList(it: ArrayList<ItemsItem>) {
            val diffUtil = FavoriteDiffCallback(listUser as ArrayList<ItemsItem>, it)
            val diffResults = DiffUtil.calculateDiff(diffUtil)
            listUser = it
            diffResults.dispatchUpdatesTo(this)
        }

        class ListViewHolder(var binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)

        interface OnItemClickCallback {
            fun onItemClicked(data: ItemsItem)
        }
}


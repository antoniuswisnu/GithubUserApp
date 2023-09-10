package com.example.githubuserapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserapp.data.response.ItemsItem
import com.example.githubuserapp.databinding.ItemUserBinding

class FollowingAdapter : RecyclerView.Adapter<FollowingAdapter.FollowingViewHolder>() {

    private val followingList = mutableListOf<ItemsItem>()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<ItemsItem>) {
        followingList.clear()
        followingList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowingViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemUserBinding.inflate(inflater, parent, false)
        return FollowingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FollowingViewHolder, position: Int) {
        val following = followingList[position]
        holder.bind(following)
    }

    override fun getItemCount(): Int {
        return followingList.size
    }

    inner class FollowingViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(following: ItemsItem) {
            binding.apply {
                Glide.with(root.context)
                    .load(following.avatarUrl)
                    .into(imgUser)
                tvUser.text = following.login
            }
        }
    }
}

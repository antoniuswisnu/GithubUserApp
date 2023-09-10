package com.example.githubuserapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserapp.data.response.ItemsItem
import com.example.githubuserapp.databinding.ItemUserBinding

class FollowersAdapter : RecyclerView.Adapter<FollowersAdapter.FollowersViewHolder>() {

    private var followersList = mutableListOf<ItemsItem>()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newList: List<ItemsItem>) {
        followersList.clear()
        followersList.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowersViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemUserBinding.inflate(inflater, parent, false)
        return FollowersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FollowersViewHolder, position: Int) {
        val followers = followersList[position]
        holder.bind(followers)
    }

    override fun getItemCount(): Int {
        return followersList.size
    }

    inner class FollowersViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(followers: ItemsItem) {
            binding.apply {
                Glide.with(root.context)
                    .load(followers.avatarUrl)
                    .into(imgUser)
                tvUser.text = followers.login
            }
        }
    }
}
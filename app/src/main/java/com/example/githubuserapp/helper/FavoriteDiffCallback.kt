package com.example.githubuserapp.helper

import androidx.recyclerview.widget.DiffUtil
import com.example.githubuserapp.data.response.ItemsItem

class FavoriteDiffCallback(
    private val oldItems: ArrayList<ItemsItem>,
    private val newItems: ArrayList<ItemsItem>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldItems.size

    override fun getNewListSize(): Int = newItems.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition].id == newItems[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition].login == newItems[newItemPosition].login
    }

}
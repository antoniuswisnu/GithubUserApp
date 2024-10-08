package com.example.githubuserapp.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.adapter.FavoriteAdapter
import com.example.githubuserapp.data.response.ItemsItem
import com.example.githubuserapp.database.FavoriteUser
import com.example.githubuserapp.databinding.ActivityFavoriteUserBinding
import com.example.githubuserapp.model.favorite.FavoriteModelFactory


class FavoriteUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteUserBinding
    private lateinit var listAdapter: FavoriteAdapter
    private lateinit var viewModel: FavoriteModelFactory

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listAdapter = FavoriteAdapter(ArrayList())
        listAdapter.notifyDataSetChanged()
        viewModel = ViewModelProvider(this)[FavoriteModelFactory::class.java]

        listAdapter.setOnItemClickCallback(object : FavoriteAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ItemsItem) {
                Intent(this@FavoriteUserActivity, DetailActivity::class.java).also {
                    it.putExtra("USERNAME", data.login)
                    it.putExtra("ID", data.id)
                    it.putExtra("AVATAR_URL", data.avatarUrl)
                    startActivity(it)
                }
            }
        })

        val layoutManager = LinearLayoutManager(this)
        binding.rvUsersFavorite.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUsersFavorite.addItemDecoration(itemDecoration)
        binding.rvUsersFavorite.adapter = listAdapter

        viewModel.getFavoriteUser()?.observe(this) {
            if (it != null) {
                val list = mapList(it)
                listAdapter.setList(list)
            }
        }

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        supportActionBar?.apply {
            title = "Favorite"
            elevation = 0f
            setDisplayHomeAsUpEnabled(true)
        }

        binding.imgBack.setOnClickListener {
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
            }
        }

        binding.imgSetting.setOnClickListener {
            Intent(this, SettingActivity::class.java).also {
                startActivity(it)
            }
        }
    }

    private fun mapList(users: List<FavoriteUser>): ArrayList<ItemsItem> {
        val listUsers = ArrayList<ItemsItem>()
        for (user in users) {
            val userMapped = ItemsItem(
                user.id, user.username, user.avatarUrl
            )
            listUsers.add(userMapped)
        }
        return listUsers
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}
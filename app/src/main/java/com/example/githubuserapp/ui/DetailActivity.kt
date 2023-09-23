package com.example.githubuserapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.githubuserapp.R
import com.example.githubuserapp.adapter.SectionsPagerAdapter
import com.example.githubuserapp.data.response.DetailResponse
import com.example.githubuserapp.databinding.ActivityDetailBinding
import com.example.githubuserapp.model.detail.DetailViewModel
import com.example.githubuserapp.model.favorite.FavoriteViewModel
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.e("DetailActivity", "onCreate() called")
        val username = intent.getStringExtra("USERNAME")
        val avatarUrl = intent.getStringExtra("AVATAR_URL")

        if (username != null) {
            detailViewModel.setUsername(username)
            detailViewModel.getDetailUser(username)
        } else {
            Log.e("DetailActivity", "Username is null")
        }

        detailViewModel.detailUser.observe(this) { detailUser ->
            detailUser?.let {
                displayUserDetail(it)
            }
        }

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        val adapter = SectionsPagerAdapter  (this)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Followers"
                1 -> tab.text = "Following"
            }
        }.attach()

        var viewModel: FavoriteViewModel = ViewModelProvider(this)[FavoriteViewModel::class.java]
        val id = intent.getIntExtra("ID", 0)

        var isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkUser(id)
            withContext(Dispatchers.Main) {
                if (count != null) {
                    if (count > 0) {
                        binding.fab.isChecked = true
                        binding.fab.setBackgroundResource(R.drawable.ic_favorite_fill)
                        isChecked = true
                    } else {
                        binding.fab.isChecked = false
                        binding.fab.setBackgroundResource(R.drawable.ic_favorite_border)
                        isChecked = false
                    }
                }
            }
        }

        binding.fab.setOnClickListener {
            isChecked = !isChecked
            if (isChecked) {
                viewModel.addToFavorite(username.toString(), id, avatarUrl.toString())
                Toast.makeText(this, "Added to Favorite", Toast.LENGTH_SHORT).show()
                binding.fab.setBackgroundResource(R.drawable.ic_favorite_fill)
            } else {
                viewModel.removeFromFavorite(id)
                Toast.makeText(this, "Removed from Favorite", Toast.LENGTH_SHORT).show()
                binding.fab.setBackgroundResource(R.drawable.ic_favorite_border)
            }
            binding.fab.isChecked = isChecked
        }
    }

    private fun displayUserDetail(user: DetailResponse) {
        with(binding) {
            Glide.with(this@DetailActivity)
                .load(user.avatarUrl)
                .into(imgDetailUser)
            tvLogin.text = user.login
            tvName.text = user.name
            tvFollowers.text = "${user.followers}"
            tvFollowing.text = "${user.following}"
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}
package com.example.githubuserapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.example.githubuserapp.adapter.SectionsPagerAdapter
import com.example.githubuserapp.data.response.DetailResponse
import com.example.githubuserapp.databinding.ActivityDetailBinding
import com.example.githubuserapp.model.DetailViewModel
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra("USERNAME")

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

        val adapter = SectionsPagerAdapter(this)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Followers"
                1 -> tab.text = "Following"
            }
        }.attach()
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

}
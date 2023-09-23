package com.example.githubuserapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toolbar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.R
import com.example.githubuserapp.adapter.UserAdapter
import com.example.githubuserapp.data.response.ItemsItem
import com.example.githubuserapp.databinding.ActivityMainBinding
import com.example.githubuserapp.model.main.MainViewModel
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), UserAdapter.OnItemClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var userAdapter: UserAdapter
    private val mainViewModel by viewModels<MainViewModel>()
    private var toolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val layoutManager = LinearLayoutManager(this)
        binding.rvUsers.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUsers.addItemDecoration(itemDecoration)

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView.editText.setOnEditorActionListener { _, _, _ ->
                val query = searchView.text.toString()
                searchBar.text = query
                searchView.hide()
                mainViewModel.searchUsers(query)
                true
            }
        }

        mainViewModel.listUserGithub.observe(this){
            listUserGithub -> setGithubData(listUserGithub)
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        mainViewModel.snackbarText.observe(this) {
            Snackbar.make(window.decorView.rootView, it, Snackbar.LENGTH_SHORT).show()
        }

        userAdapter = UserAdapter(object : UserAdapter.OnItemClickListener {
            override fun onItemClick(user: ItemsItem) {
                Log.e("MainActivity", "User clicked: ${user.login}")
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra("USERNAME", user.login)
                intent.putExtra("AVATAR_URL", user.avatarUrl)
                intent.putExtra("ID", user.id)
                startActivity(intent)
            }
        })
        binding.rvUsers.adapter = userAdapter
    }

    override fun onItemClick(user: ItemsItem) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("USERNAME", user.login)
        startActivity(intent)
    }

    private fun setGithubData(user: List<ItemsItem>) {
        userAdapter.submitList(user)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_favorite -> {
                startActivity(Intent(this, FavoriteUserActivity::class.java))
                true
            }
            R.id.menu_setting -> {
                startActivity(Intent(this, SettingActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }



}
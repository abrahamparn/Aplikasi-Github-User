package com.example.mygithubuser.main

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mygithubuser.Favorites.Favorites
import com.example.mygithubuser.R
import com.example.mygithubuser.detail.UserDetailActivity
import com.example.mygithubuser.detail.UserDetailActivity.Companion.EXTRA_PERSON
import com.example.mygithubuser.adapters.ListUserAdapter
import com.example.mygithubuser.data.Items
import com.example.mygithubuser.data.ItemsItem
import com.example.mygithubuser.databinding.ActivityMainBinding
import com.example.mygithubuser.detail.UserDetailActivity.Companion.EXTRA_AVATAR
import com.example.mygithubuser.detail.UserDetailActivity.Companion.EXTRA_ID
import com.example.mygithubuser.settings.*

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel.listUsers.observe(this, { user ->
            setUsersGithub(user)
        })

        mainViewModel.isSearching.observe(this, {
            showFrontView(it)
        })

        mainViewModel.isLoading.observe(this, {
            showLoading(it)
        })

        binding.svUser.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.svUser.clearFocus()
                if (query != null) {
                    mainViewModel.findUser(query)
                }
                return false
            }
            override fun onQueryTextChange(query: String?): Boolean {
                return false
            }

        })

        val pref = SettingsPreferences.getInstance(dataStore)
        val sainViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
            SettingsViewModel::class.java
        )
        sainViewModel.getThemeSettings().observe(this,
            { isDarkModeActive: Boolean ->
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

                }
            })

    }

    private fun showRecyclerList(list: ArrayList<Items>) {

        if(applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            binding.rvUsers.layoutManager = GridLayoutManager(this , 2)
        }else {
            binding.rvUsers.layoutManager = LinearLayoutManager(this)
        }

        val listUserAdapter = ListUserAdapter(list)
        binding.rvUsers.adapter = listUserAdapter

        listUserAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Items) {
                val intentDetail = Intent(this@MainActivity, UserDetailActivity::class.java)
                intentDetail.putExtra(EXTRA_PERSON, data.login)
                intentDetail.putExtra(EXTRA_ID, data.id)
                intentDetail.putExtra(EXTRA_AVATAR, data.avatarUrl)
                startActivity(intentDetail)
            }
        })
    }

    private fun setUsersGithub(users: List<ItemsItem>) {
        val listUsers = ArrayList<Items>()
        for (user in users) {
            val userItem = Items(
                user.login,
                user.avatarUrl,
                user.id
            )
            listUsers.add(userItem)
        }
        showRecyclerList(listUsers)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
    }

    private fun showFrontView(isSearching: Boolean) {
        binding.textView4.visibility = if(isSearching) View.INVISIBLE else View.VISIBLE
        binding.rvUsers.visibility = if(isSearching) View.VISIBLE else View.INVISIBLE
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_favorites, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.favorites_menu ->{
                val i =  Intent(this, Favorites::class.java)
                startActivity(i)
                return true
            }
            R.id.settings_menu ->{
                val a = Intent(this, Settings::class.java)
                startActivity(a)
                return true
            }
            else -> return true
        }
    }
}
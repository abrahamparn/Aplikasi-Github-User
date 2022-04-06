package com.example.mygithubuser.Favorites

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mygithubuser.R
import com.example.mygithubuser.adapters.FavoriteUserAdapter
import com.example.mygithubuser.adapters.ListUserAdapter
import com.example.mygithubuser.data.Items
import com.example.mygithubuser.data.handlingfavorite.FavoriteUsers
import com.example.mygithubuser.databinding.ActivityFavoritesBinding
import com.example.mygithubuser.detail.UserDetailActivity


class Favorites : AppCompatActivity() {

    private lateinit var binding: ActivityFavoritesBinding
    private lateinit var favoritesViewModel: FavoritesViewModel
    private lateinit var favoriteUserAdapter: FavoriteUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        favoriteUserAdapter = FavoriteUserAdapter()
        favoriteUserAdapter.notifyDataSetChanged()
        favoritesViewModel = ViewModelProvider(this)[FavoritesViewModel::class.java]
        favoriteUserAdapter.setOnItemClickCallback(object : FavoriteUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Items) {
                val intentDetail = Intent(this@Favorites, UserDetailActivity::class.java)
                intentDetail.putExtra(UserDetailActivity.EXTRA_PERSON, data.login)
                intentDetail.putExtra(UserDetailActivity.EXTRA_ID, data.id)
                intentDetail.putExtra(UserDetailActivity.EXTRA_AVATAR, data.avatarUrl)
                startActivity(intentDetail)
            }
        })

        supportActionBar?.title = "The Favorite Users"

        binding.apply {
            showRecyclerList()
            binding.RvFavoriteUsers.adapter = favoriteUserAdapter
        }
        favoritesViewModel.getFavoriteUsers()?.observe(this) {
            if (it!=null){
                val list = mapList(it)
                favoriteUserAdapter.setList(list)
            }
        }


    }

    private fun mapList(users: List<FavoriteUsers>): ArrayList<Items>{
        val listUsers = ArrayList<Items>()
        for (user in users) {
            val userItem = Items(
                user.login,
                user.avatar_url,
                user.id
            )
            listUsers.add(userItem)
        }
        return listUsers
    }

    private fun showRecyclerList() {
        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.RvFavoriteUsers.layoutManager = GridLayoutManager(this, 2)
        } else {
            binding.RvFavoriteUsers.layoutManager = LinearLayoutManager(this)
        }
        binding.RvFavoriteUsers.adapter = favoriteUserAdapter
    }

}
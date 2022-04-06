package com.example.mygithubuser.Favorites

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.mygithubuser.data.handlingfavorite.FavoriteUsers
import com.example.mygithubuser.data.handlingfavorite.FavoriteUsersDao
import com.example.mygithubuser.data.handlingfavorite.UserDatabase

class FavoritesViewModel(application: android.app.Application) : AndroidViewModel(application)  {

    private var userDao: FavoriteUsersDao?
    private var userDb: UserDatabase? = UserDatabase.getDatabase(application)

    init {
        userDao = userDb?.favoriteUsersDao()
    }

    fun getFavoriteUsers(): LiveData<List<FavoriteUsers>>?{
        return userDao?.getFavoriteUsers()
    }


}
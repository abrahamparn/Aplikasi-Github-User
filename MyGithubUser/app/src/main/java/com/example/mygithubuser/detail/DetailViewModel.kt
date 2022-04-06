package com.example.mygithubuser.detail

import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mygithubuser.api.ApiConfig
import com.example.mygithubuser.data.DetailResponse
import com.example.mygithubuser.data.handlingfavorite.FavoriteUsers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.mygithubuser.data.handlingfavorite.FavoriteUsersDao
import com.example.mygithubuser.data.handlingfavorite.UserDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class DetailViewModel(application: android.app.Application) : AndroidViewModel(application) {

    private var userDao: FavoriteUsersDao?
    private var userDb: UserDatabase? = UserDatabase.getDatabase(application)

    init {
        userDao = userDb?.favoriteUsersDao()
    }

    fun addUserToFavorite(username: String, id:Int, avatarUrl: String){
        CoroutineScope(Dispatchers.IO).launch {
            val users = FavoriteUsers(
                username,
                id,
                avatarUrl
            )
            userDao?.addToFavoriteUsers(users)
        }
    }

    fun checkUsers(id:Int) = userDao?.checkUsers(id)

    fun removeFromFavoriteUsers(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.removeFromFavoriteUsers(id)
        }
    }


    private val _listUsers = MutableLiveData<DetailResponse>()
    val listUsers : LiveData<DetailResponse> = _listUsers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "DetailViewModel"
    }

    fun getDetailUser(userName: String) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getUserGithub(userName)
        client.enqueue(object : Callback<DetailResponse> {
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listUsers.value = response.body()
                } else {
                    Log.e(TAG, "error : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure : ${t.message.toString()}")
            }
        })
    }
}
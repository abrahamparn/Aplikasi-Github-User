package com.example.mygithubuser.follows

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mygithubuser.api.ApiConfig
import com.example.mygithubuser.data.FollowerResponseItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowerViewModel : ViewModel() {
    private val _follower = MutableLiveData<List<FollowerResponseItem>>()
    val follower: LiveData<List<FollowerResponseItem>> = _follower

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "FollowerViewModel"
    }

    fun getFollowerUser(query: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowers(query)
        client.enqueue(object : Callback<List<FollowerResponseItem>> {
            override fun onResponse(call: Call<List<FollowerResponseItem>>, response: Response<List<FollowerResponseItem>>) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _follower.value = response.body()
                } else {
                    Log.e(TAG, "error : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<FollowerResponseItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure : ${t.message}")
            }

        })
    }
}
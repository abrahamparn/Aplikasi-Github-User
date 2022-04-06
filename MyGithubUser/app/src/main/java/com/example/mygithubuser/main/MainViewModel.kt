package com.example.mygithubuser.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mygithubuser.api.ApiConfig
import com.example.mygithubuser.data.ResponseIn
import com.example.mygithubuser.data.ItemsItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel  : ViewModel(){

    private val _listUsers = MutableLiveData<List<ItemsItem>>()
    val listUsers: MutableLiveData<List<ItemsItem>> = _listUsers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isSearching = MutableLiveData<Boolean>()
    val isSearching: LiveData<Boolean> = _isSearching

    companion object{
        private const val TAG = "MainViewModel"
    }

    fun findUser(query: String) {
        _isLoading.value = true
        _isSearching.value = true

        val client = ApiConfig.getApiService().getSearchUsers(query)
        client.enqueue(object : Callback<ResponseIn> {
            override fun onResponse(call: Call<ResponseIn>, response: Response<ResponseIn>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listUsers.value = response.body()?.items
                } else { Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResponseIn>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}
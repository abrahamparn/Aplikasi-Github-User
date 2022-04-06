package com.example.mygithubuser.follows

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mygithubuser.api.ApiConfig
import com.example.mygithubuser.data.FollowingResponseItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel : ViewModel() {
    private val _following = MutableLiveData<List<FollowingResponseItem>>()
    val following : LiveData<List<FollowingResponseItem>> = _following

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "FollowingViewModel"
    }

    fun getFollowingUser(query: String){
        _isLoading.value = true

        val client = ApiConfig.getApiService().getFollowings(query)
        client.enqueue(object : Callback<List<FollowingResponseItem>> {
            override fun onResponse(call: Call<List<FollowingResponseItem>>, response: Response<List<FollowingResponseItem>>) {
                if(response.isSuccessful) {
                    _isLoading.value = false
                    _following.value = response.body()
                } else {
                    Log.e(TAG, "error : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<FollowingResponseItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure : ${t.message.toString()}")
            }
        })
    }
}
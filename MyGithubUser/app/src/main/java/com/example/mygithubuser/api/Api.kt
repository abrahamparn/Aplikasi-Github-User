package com.example.mygithubuser.api

import com.example.mygithubuser.data.DetailResponse
import com.example.mygithubuser.data.FollowerResponseItem
import com.example.mygithubuser.data.FollowingResponseItem
import com.example.mygithubuser.data.ResponseIn
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @Headers("Authorization: token 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")
    @GET("search/users")
    fun getSearchUsers(@Query("q") q : String, ): Call<ResponseIn>

    @Headers("Authorization: token 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")
    @GET("users/{username}")
    fun getUserGithub(@Path("username") username : String): Call<DetailResponse>

    @Headers("Authorization: token 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")
    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username : String): Call<List<FollowerResponseItem>>

    @Headers("Authorization: token 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")
    @GET("users/{username}/following")
    fun getFollowings(@Path("username") username : String): Call<List<FollowingResponseItem>>
}
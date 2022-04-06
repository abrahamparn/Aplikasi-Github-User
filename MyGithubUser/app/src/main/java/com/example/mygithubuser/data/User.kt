package com.example.mygithubuser.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Items(val login: String, val avatarUrl: String, val id: Int) : Parcelable

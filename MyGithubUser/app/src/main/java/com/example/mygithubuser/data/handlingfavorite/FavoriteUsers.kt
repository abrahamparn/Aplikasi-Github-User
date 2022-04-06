package com.example.mygithubuser.data.handlingfavorite

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
    (tableName = "favorite_user") data class FavoriteUsers( val login: String, @PrimaryKey var id: Int, val avatar_url : String
) : Parcelable
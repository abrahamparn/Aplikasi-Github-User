package com.example.mygithubuser.data.handlingfavorite

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteUsersDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addToFavoriteUsers(favoriteUsers: FavoriteUsers)

    @Query("SELECT * FROM favorite_user ORDER BY id ASC")
    fun getFavoriteUsers(): LiveData<List<FavoriteUsers>>

    @Query("SELECT count(*) FROM favorite_user WHERE favorite_user.id = :id")
    fun checkUsers(id: Int): Int

    @Query("DELETE FROM favorite_user WHERE favorite_user.id = :id")
    fun removeFromFavoriteUsers(id: Int): Int

}
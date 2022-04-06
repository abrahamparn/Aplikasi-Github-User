package com.example.mygithubuser.data.handlingfavorite

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase



@Database(entities = [FavoriteUsers::class], version = 1)
abstract class UserDatabase: RoomDatabase() {
    abstract fun favoriteUsersDao(): FavoriteUsersDao

    companion object{
        @Volatile
        private var INSTANCE : UserDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): UserDatabase?{
            if(INSTANCE ==null){
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        UserDatabase::class.java,
                        "user_database"
                    ).build()
                }
            }
            return INSTANCE
        }
    }
}
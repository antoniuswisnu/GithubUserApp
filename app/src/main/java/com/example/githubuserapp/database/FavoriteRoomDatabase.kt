package com.example.githubuserapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoriteUser::class], version = 1)
abstract class FavoriteRoomDatabase: RoomDatabase() {
    companion object {
        private const val DATABASE_NAME = "favorite_database"
        private var INSTANCE: FavoriteRoomDatabase? = null
        fun getDatabase(context: Context): FavoriteRoomDatabase {
            if (INSTANCE == null) {
                synchronized(FavoriteRoomDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        FavoriteRoomDatabase::class.java,
                        DATABASE_NAME
                    ).build()
                }
            }
            return INSTANCE as FavoriteRoomDatabase
        }
    }

    abstract fun favoriteUserDao(): FavoriteDAO
}
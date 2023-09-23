package com.example.githubuserapp.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "favorite_user")
@Parcelize
data class FavoriteUser(
    @PrimaryKey val id: Int,
    val username: String,
    val avatarUrl: String
) : Parcelable
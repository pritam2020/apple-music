package com.example.apple

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
@Entity
data class entity (
     @PrimaryKey val trackId : Int,
    @ColumnInfo(name = "artist_name") val name : String,
    @ColumnInfo(name = "release_date") val releaseDate : String  ,
    @ColumnInfo(name = "track_name") val trackName : String ,
    @ColumnInfo(name = "primary_genre_name") val primaryGenreName : String,
    @ColumnInfo(name = "artwork_url100") val artWorkUrl100 : String ,

  )
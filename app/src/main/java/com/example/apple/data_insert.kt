package com.example.apple

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface data_insert {

    @GET("search?")
    fun get_search_result(@Query("term") key:String):Call<model>
}
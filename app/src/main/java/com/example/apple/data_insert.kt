package com.example.apple

import retrofit2.Call
import retrofit2.http.GET

interface data_insert {

    @GET("search?term=arijit")
    fun get_search_result():Call<model>
}
package com.example.apple

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.GridView
import android.widget.SearchView
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    var searchview:SearchView?=null
    var list_lang: GridView? = null
    var adapter: grid_view? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




    }




    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.serarch, menu)
        var searchItem:MenuItem?= menu?.findItem(R.id.action_search)
         searchview=searchItem?.actionView as SearchView
        return true;
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_search->function()
        }
        return super.onOptionsItemSelected(item)
    }





    private fun function() {
        searchview?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                var search_text:String?=p0
                if (search_text != null) {
                    fetching_data(search_text)
                }
                return false
            }
            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })
    }

    private fun fetching_data(search_text:String) {
        isNetworkConnected();
        val request = data_insert_interface.buildService(data_insert::class.java)
        val call = request.get_search_result(search_text);
        call.enqueue(object : retrofit2.Callback<model> {
            override fun onFailure(call: Call<model>?, t: Throwable?) {
                Log.e("MainActivity", "Problem calling Github API ${t?.message}")
            }
            override fun onResponse(call: Call<model>?, response: Response<model>?) {
                var rresult: List<Results>? = response?.body()?.results
                list_lang= findViewById<GridView>(R.id.list_lang) as GridView
                if (response != null) {
                    adapter = response.body()?.results?.let { grid_view(it) }
                    list_lang?.adapter=adapter
                }

            }
        })

    }


    private fun isNetworkConnected(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager //1
        val networkInfo = connectivityManager.activeNetworkInfo //2
        return networkInfo != null && networkInfo.isConnected //3
    }

}
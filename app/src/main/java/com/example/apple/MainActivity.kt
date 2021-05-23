package com.example.apple

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.GridView
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.isInvisible
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    var searchview:SearchView?=null
    var list_lang: GridView? = null
    var adapter: grid_view? = null
    var dbHelper: DatabaseHelperImpl? =null
    var progress :ProgressBar?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        list_lang = findViewById<GridView>(R.id.list_lang) as GridView
        progress=findViewById<ProgressBar>(R.id.progress) as ProgressBar






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
                var search_text:String?= p0?.trim()
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

    private  fun fetching_data(search_text:String) {

        if(isNetworkConnected(this)) {
            progress?.visibility
            val request = data_insert_interface.buildService(data_insert::class.java)
            val call = request.get_search_result(search_text);
            call.enqueue(object : retrofit2.Callback<model> {
                override fun onFailure(call: Call<model>?, t: Throwable?) {
                    Log.e("MainActivity", "Problem calling Github API ${t?.message}")
                    progress?.isInvisible
                }

                override fun onResponse(call: Call<model>?, response: Response<model>?) {
                    var rresult: model? = response?.body();

                    if (response != null) {
                        adapter = response.body()?.results?.let { grid_view(it) }
                        list_lang?.adapter = adapter
                        if (rresult != null) {
                            roomDatabase().save_to_room(rresult,applicationContext)
                        }

                    }
                    progress?.isInvisible

                }
            })
        }else {// to call the data from room
            GlobalScope.launch(Dispatchers.Main){
                list_lang?.let { progress?.let { it1 ->
                    roomDatabase().query_to_room(search_text,applicationContext, it,
                        it1)
                } }
            }

        }

    }


    fun isNetworkConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var activeNetworkInfo: NetworkInfo? = null
        activeNetworkInfo = cm.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
    }

    suspend fun fetchUsers() {



        try {
            val usersFromDb = dbHelper?.getUsers()
            var cow=usersFromDb
            Toast.makeText(this, cow?.get(0).toString(),Toast.LENGTH_SHORT).show()

            // here you have your usersFromDb

        } catch (e: Exception) {
            // handler error
        }
    }

}
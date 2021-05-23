package com.example.apple

import android.content.Context
import android.widget.GridView
import android.widget.ProgressBar
import androidx.core.view.isInvisible
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class roomDatabase {
    var dbHelper: DatabaseHelperImpl? =null
    var aadaptre : grid_view?=null

    fun save_to_room (list: model,context:Context){
        dbHelper= DatabaseHelperImpl(DatabaseBuilder.getInstance(context))
        GlobalScope.launch(Dispatchers.Main){
            uoload(list.results)
//            list.results.get(1).
        }
    }
    fun query_to_room(search_text:String,context:Context ,gridView: GridView,progressBar: ProgressBar){
        progressBar?.visibility
        dbHelper= DatabaseHelperImpl(DatabaseBuilder.getInstance(context))

        var acr:MutableList<Results> = mutableListOf<Results>()
        GlobalScope.launch(Dispatchers.Main){
            var list: MutableList<entity>? = mutableListOf<entity>()
            try {
                list= dbHelper?.getUsers() as MutableList<entity>?
            }catch (e:java.lang.Exception){

            }

         for (i in list?.indices!!) {
             var index=list.get(i)
             if(index.name.contains(search_text, ignoreCase = true)){
             acr.add(Results("","",0,0,"",index.name,index.trackName,"","","","","","","",index.artWorkUrl100,0.00,0.00,0.00,0.00,0.00,0.00,index.releaseDate,"","",0,"","",index.primaryGenreName,"","", emptyList(), emptyList()))
         }
         }
            if(acr.size!=0) {
                aadaptre = acr.let { grid_view(it) }
                gridView.adapter = aadaptre
            }
            progressBar?.isInvisible
        }
    }

    suspend fun uoload(list:List<Results>){
        for (i in list.indices) {

           try {
               var llist: MutableList<entity> = mutableListOf<entity>()
               llist.add(entity(list.get(i).trackId, list.get(i).artistName, list.get(i).releaseDate, list.get(i).trackName, list.get(i).primaryGenreName, list.get(i).artworkUrl100))
               dbHelper?.insertAll(llist)
           }catch (e:Exception){}
        }
    }
}
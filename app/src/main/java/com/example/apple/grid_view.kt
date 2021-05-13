package com.example.apple

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import retrofit2.Callback

data class grid_view(val dataList:List<Results>):BaseAdapter(){
    override fun getItem(position: Int): Any {
        return dataList.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return dataList.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
       // val view: View = View.inflate(activity,R.layout.grid_layout,null)
        val inflater = parent?.context?.
        getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.grid_layout,null)

        val artist = view.findViewById<TextView>(R.id.artist_name) as TextView
        val relese_date = view.findViewById<TextView>(R.id.release_date) as TextView
        val  track= view.findViewById<TextView>(R.id.track_name) as TextView
        val  primary_genere= view.findViewById<TextView>(R.id.primary_genere) as TextView
        val image=view.findViewById<ImageView>(R.id.image)

        var rresult: List<Results>? = dataList


                artist.text=rresult?.get(position)?.artistName
                relese_date.text=rresult?.get(position)?.releaseDate
                track.text=rresult?.get(position)?.trackName
                primary_genere.text=rresult?.get(position)?.primaryGenreName
                Picasso.get().load(rresult?.get(position)?.artworkUrl100).into(image)




        return view
    }
}

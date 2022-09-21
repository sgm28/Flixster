package com.example.flixster

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


//Adapter class uses to bind data
//Adapter is a abstract class
private const val TAG = "MovieAdapter"
class MovieAdapter(private val context: Context, private val  movies: List<Movie>)
    : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {


    //Expensive operation: create a view
    //Create viewHolder of type ViewHolder on line 36
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false)
        return ViewHolder(view)


    }
    // cheap: simply bind data to an existing viewHolder.
    //Given a data and position bind the data together
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.i(TAG, "onBindViewHolder $position")
        val movie = movies[position]
        //define in ViewHolder class
          holder.bind(movie)

    }

    override fun getItemCount() = movies.size

// Allows the programmer to use methods without instantiating the object
inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val ivPoster = itemView.findViewById<ImageView>(R.id.ivPoster)
    private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
    private val tvOverView = itemView.findViewById<TextView>(R.id.tvOverview)

    fun bind(movie: Movie) {
        tvTitle.text = movie.title
        tvOverView.text = movie.overview

        Glide.with(context).load(movie.posterImageUrl).into(ivPoster)


    }
}
    }




package com.example.flixster

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


//Adapter class uses to bind data
//Adapter is a abstract class
const val MOVIE_EXTRA = "MOVIE_EXTRA"
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


        if(isLandScape) {
            holder.bindLandscape(movie)
        }
        else
        {
            holder.bind(movie)
        }


    }

    override fun getItemCount() = movies.size

// Allows the programmer to use methods without instantiating the object, @View. OnClickListener when a user clicks on an item in the list
inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    private val ivPoster = itemView.findViewById<ImageView>(R.id.ivPoster)
    private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
    private val tvOverView = itemView.findViewById<TextView>(R.id.tvOverview)

    //Register the click listener each time a ViewHolder is created
    init {
        //this refers to ViewHolder
        itemView.setOnClickListener(this)
    }

    fun bind(movie: Movie) {
        tvTitle.text = movie.title
        tvOverView.text = movie.overview

        Glide.with(context).load(movie.posterImageUrl).into(ivPoster)



    }

    fun bindLandscape(movie: Movie) {
        tvTitle.text = movie.title
        tvOverView.text = movie.overview

       movie.posterPath = movie.backdrop
        movie.posterImageUrl = "https://image.tmdb.org/t/p/w342/${movie.backdrop}"
        Glide.with(context).load(movie.posterImageUrl).into(ivPoster)





    }
    //When a user click on a list this function will get call
    override fun onClick(v: View?) {

        // 1. Get notified of the particular movie which was clicked
        val movie = movies[adapterPosition]

        // 2. user the intent system to navigate to the new activity
        //@param1 -???,  @param2 - the activity you want to go to
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra(MOVIE_EXTRA, movie)
        context.startActivity(intent)

    }


}


    }







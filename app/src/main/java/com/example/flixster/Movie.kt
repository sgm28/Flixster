package com.example.flixster

import android.content.res.Configuration
import org.json.JSONArray


//This class is used to extract data from the JSON object and convert it to an object
data class Movie (
    //selecting the keys wanted out of JSON object
    val movieId: Int,
    internal var posterPath: String,
    val backdrop : String,
    val title: String,
    val overview: String,
)
{
    //The full path to the image with image size of w342
    //Need a full url
    var posterImageUrl = "https://image.tmdb.org/t/p/w342/$posterPath"
    //Allows you call methods on the movie class without instantiating  the object
    companion object{

        fun fromJsonArray(movieJsonArray: JSONArray) : List<Movie> {




            val movies = mutableListOf<Movie>()
            for (i in 0 until movieJsonArray.length())
            {
                //Getting Json object at that index
                val movieJson = movieJsonArray.getJSONObject(i)
                movies.add(
                    Movie(
                        movieJson.getInt("id"),
                        movieJson.getString("poster_path"),
                        movieJson.getString("backdrop_path"),
                        movieJson.getString("title"),
                        movieJson.getString("overview"),


                    )
                )
            }
            return movies //returning the movie list
        }
    }


}


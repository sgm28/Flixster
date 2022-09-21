package com.example.flixster

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.json.JSONException

private const val TAG = "MainActivity"
//URL FOR now playing videos from The Movie Database API
private const val NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed"
private lateinit var rvMovies: RecyclerView
class MainActivity : AppCompatActivity() {

    // 1. Define a data model class as the data source - DONE
    // 2. Add the RecyclerView to the layout - DONE
    // 3. Create a custom row layout XML file to visualize the item - DONE
    // 4. Create an Adapter and ViewHolder to render the item - DONE
    // 5. Bind the adapter to the data source to populate the RecyclerView - DONE
    // 6. Bind a layout manager to the RecyclerView - DONE

    //Initially empty
    private val movies = mutableListOf<Movie>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //reference to the Recycler view
        rvMovies = findViewById(R.id.rvMovies)

        // Movie adapter object from the MovieAdapter class
        val movieAdapter = MovieAdapter(this,movies)

        //Binding the adapter to the Recycler view
        rvMovies.adapter = movieAdapter

        //Bind the layout manager to the recycler viewer
        rvMovies.layoutManager = LinearLayoutManager(this)


        //NETWORK REQUEST SECTION
        val client = AsyncHttpClient()
                                    //Second parameter is callback function
                                    //Runs on the background thread
                                    //Once network request is done, the call back function get called
        client.get(NOW_PLAYING_URL, object: JsonHttpResponseHandler() {
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.e(TAG, "onFailure $statusCode")
            }

            override fun onSuccess(
                statusCode: Int,
                headers: Headers?, //question means it could be null
                json: JSON)
            {
                    Log.i(TAG, "onSuccess: JSON data $json")
                    //Contains Json object from network request
                    val movieJsonArray = json.jsonObject.getJSONArray("results")
                    //This variable contains the extract from the Movie.kt class
                    movies.addAll(Movie.fromJsonArray(movieJsonArray))
                    Movie.fromJsonArray(movieJsonArray)
                    //Ensuring that if the data changes in the future("the keys i.e, Id, title,...)
                    //the user will be notify
                    try {


                    movies.addAll(Movie.fromJsonArray(movieJsonArray))
                        //Notify the adapter that data has been changed
                        movieAdapter.notifyDataSetChanged()
                    Log.i(TAG,"Movie list $movies")
                    } catch (e: JSONException)
                    {
                        Log.e(TAG,"Encountered exception $e")
                    }
            }

        })
    }


}
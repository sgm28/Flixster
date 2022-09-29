package com.example.flixster

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.FilterQueryProvider
import android.widget.RatingBar
import android.widget.TextView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import okhttp3.Headers

private const val YOUTUBE_API_KEY = "AIzaSyDZcvXS0CIiiQnyF89LaZLJwPFQadYOzas"
private const val TRAILERS_URL = "https://api.themoviedb.org/3/movie/%d/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed"
private const val TAG = "DetailActivity"
                        // Need YouTubeBaseActivity for YouTube intent
class DetailActivity : YouTubeBaseActivity() {
    private lateinit var tvTitle: TextView
    private lateinit var tvOverview: TextView
    private lateinit var ratingBar: RatingBar
    private lateinit var ytPlayerView: YouTubePlayerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        //finding the views in DetailActivity.xml
        tvTitle = findViewById(R.id.tvTitle)
        tvOverview = findViewById(R.id.tvOverview)
        ratingBar = findViewById(R.id.rbVoteAverage)
        ytPlayerView = findViewById(R.id.player)

        //Get the parcel data
        val movie = intent.getParcelableExtra<Movie>(MOVIE_EXTRA) as Movie
        Log.i(TAG, "Movie is $movie")

        //Connecting data
        tvTitle.text = movie.title
        tvOverview.text = movie.overview
        ratingBar.rating = movie.voteAverage.toFloat()


        //Making a get request on URL
       val client = AsyncHttpClient()
        //param2 callback

        client.get(TRAILERS_URL.format(movie.movieId), object: JsonHttpResponseHandler()
        {
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.e(TAG, "onFailure $statusCode")
            }

            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON) {
                Log.i(TAG, "onSuccess")
                val results = json.jsonObject.getJSONArray("results")
                if (results.length() == 0){
                    Log.w(TAG, "No movie trailer found")
                return
            }
                val movieTrailerJson = results.getJSONObject(0)
                val youtubeKey = movieTrailerJson.getString("key")
                //play youtube video with this trailer
                initializeYoutube(youtubeKey)
            }

        })





    }

 private fun initializeYoutube(youtubeKey: String) {
     //YouTube Trailer video
     ytPlayerView.initialize(YOUTUBE_API_KEY, object: YouTubePlayer.OnInitializedListener {
         override fun onInitializationSuccess(
             provider: YouTubePlayer.Provider?,
             player: YouTubePlayer?,
             p2: Boolean
         ) {
             Log.i(TAG,"onInitializationSuccess")
             player?.cueVideo(youtubeKey) //? mean nullable
         }


         override fun onInitializationFailure(
             p0: YouTubePlayer.Provider?,
             p1: YouTubeInitializationResult?
         ) {
             Log.i(TAG,"onInitializationFailure")
         }
     })
 }
}
package com.example.movieapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.movieapp.api.MovieAPI
import com.example.movieapp.utils.Constants.TAG
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.movieapp.data.Constants

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var  movieAPI: MovieAPI
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CoroutineScope(Dispatchers.IO).launch {
            val response=movieAPI.getMovies()

            Log.d(TAG,response.body().toString())
        }

        setContentView(R.layout.activity_main)
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name : CharSequence = "My Notification"
            val description : String = "My notification description"

            val importance : Int = NotificationManager.IMPORTANCE_DEFAULT

            val notificationChannel : NotificationChannel = NotificationChannel(Constants.CHANNEL_ID, name, importance)
            notificationChannel.description = description

            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

}
package com.example.musicplayer
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class splashscreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("Inside","Ishan")
    }

    override fun onStart() {
        var intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
        super.onStart()
    }
}
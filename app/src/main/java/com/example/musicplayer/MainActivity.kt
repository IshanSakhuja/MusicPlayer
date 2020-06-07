package com.example.musicplayer

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.ticket.view.*
var songactive : Boolean = false
var mediaPlayer : MediaPlayer = MediaPlayer()
var songlist: ArrayList<songsload> = ArrayList<songsload>()
class MainActivity : AppCompatActivity() {
    var Albumid = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestpermission()
    }

    fun requestpermission() {
        if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 123)
        } else
            getsongs()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 123 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_LONG).show()
            getsongs()
        }
    }

    fun getsongs() {
       var songsURI = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        var mp3 = MediaStore.Audio.Media.IS_MUSIC + "!=0"
        var cursor = contentResolver.query(songsURI, null, mp3, null, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    var songname: String =
                        cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME))
                    var songURL: String =
                        cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                    var artist: String =
                        cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                    var album: String =
                        cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM))
                    songlist.add(songsload(songURL, songname, artist, album))
                } while (cursor.moveToNext())
            }
            cursor.close()
            var myadapter : myAdapter = myAdapter()
            list_item.adapter = myadapter
        }
    }

        inner class myAdapter : BaseAdapter {
            constructor()

            override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
                var layoutInflater =
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                var view = layoutInflater.inflate(R.layout.ticket, null)
                view.tvsongname.text = songlist[p0].songname
                view.album.text = songlist[p0].album
                view.artist.text = songlist[p0].artist
                view.setOnClickListener {
                    activity(songlist[p0].songURI!!)
                }
                return view
            }

            override fun getItem(p0: Int): Any {
                return p0
            }

            override fun getItemId(p0: Int): Long {
                return p0.toLong()
            }

            override fun getCount(): Int {
                return songlist.size
            }

        }
    fun activity(songURI : String)
    {
        var intent = Intent(this,songplay::class.java)
        var bundle : Bundle = Bundle()
        intent.putExtra("songuri",songURI)
        if(songactive == false)
            startActivity(intent)
        else if(songactive == true)
        {
            mediaPlayer.reset()
            startActivity(intent)
        }
    }
}
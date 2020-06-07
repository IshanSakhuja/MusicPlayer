package com.example.musicplayer

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_songplay.*
import java.util.*


class songplay : AppCompatActivity() {
    var play : Boolean = true
    var songcount : Int = 0
    var songuri : String? = null
    var loop : Boolean = false
    var onShuffle : Boolean = false
    var countDownTimer : CountDownTimer ?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_songplay)
        mediaPlayer.reset()
        songuri = intent.extras!!.getString("songuri")
        while(songuri != songlist[songcount].songURI)
            songcount += 1
        mediaPlayer!!.setDataSource(songuri)
        mediaPlayer!!.prepare()
        songactive = true
        mediaPlayer!!.start()
        while(songuri != songlist[songcount].songURI)
            ++songcount
        seekBar.max = mediaPlayer.duration
        countdowntimer()
        seekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                mediaPlayer.seekTo(progress)
            }
        })
        songplay()
    }
    fun countdowntimer()
    {
        countDownTimer = object : CountDownTimer(mediaPlayer.duration.toLong()-100, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                seekBar.setProgress(mediaPlayer.currentPosition)
            }
            override fun onFinish() {
                ivpause.setImageResource(R.drawable.play)
            }
        }.start()
    }
    fun songplay()
    {
        tvsongname.setText(songlist[songcount].songname)
    }
    fun pause(view : View)
    {
        if(play == true) {
            play = false
            ivpause.setImageResource(R.drawable.play)
            mediaPlayer.pause()
        }
        else
        {
            play = true
            ivpause.setImageResource(R.drawable.pause)
            mediaPlayer.start()
        }

    }
    fun next(view : View)
    {
        if(onShuffle == false) {
            if (songlist.size == 1) {
                Toast.makeText(this, "No more songs", Toast.LENGTH_LONG).show()
                return
            }
            if (songcount + 2 <= songlist.size) {
                mediaPlayer.reset()
                mediaPlayer.setDataSource(songlist[songcount].songURI)
                mediaPlayer.prepare()
                mediaPlayer.start()
                tvsongname.setText(songlist[songcount].songname)
                countDownTimer!!.cancel()
                countdowntimer()
            } else {
                mediaPlayer.reset()
                songcount = 0
                mediaPlayer.setDataSource(songlist[songcount].songURI)
                mediaPlayer.prepare()
                mediaPlayer.start()
                tvsongname.setText(songlist[songcount].songname)
                countDownTimer!!.cancel()
                countdowntimer()
            }
        }
        else
        {
            if (songlist.size == 1) {
                Toast.makeText(this, "No more songs", Toast.LENGTH_LONG).show()
                return
            }
            else {
                    var random: Random = Random()
                    var temp = random.nextInt(songlist.size)
                    songcount = temp
                    if (songcount + 2 <= songlist.size) {
                        mediaPlayer.reset()
                        mediaPlayer.setDataSource(songlist[songcount].songURI)
                        mediaPlayer.prepare()
                        mediaPlayer.start()
                        tvsongname.setText(songlist[songcount].songname)
                        countDownTimer!!.cancel()
                        countdowntimer()
                    } else {
                        mediaPlayer.reset()
                        songcount = 0
                        mediaPlayer.setDataSource(songlist[songcount].songURI)
                        mediaPlayer.prepare()
                        mediaPlayer.start()
                        tvsongname.setText(songlist[songcount].songname)
                        countDownTimer!!.cancel()
                        countdowntimer()
                    }
                }
            }
    }
    fun prev(view : View)
    {
        if(onShuffle == false) {
            if (songlist.size == 1) {
                Toast.makeText(this, "No more songs", Toast.LENGTH_LONG).show()
                return
            }
            if (songcount != 0) {
                mediaPlayer.reset()
                songcount = songlist.size - 1
                mediaPlayer.setDataSource(songlist[songcount].songURI)
                mediaPlayer.prepare()
                mediaPlayer.start()
                tvsongname.setText(songlist[songcount].songname)
                countDownTimer!!.cancel()
                countdowntimer()
            } else {
                mediaPlayer.reset()
                songcount = songcount - 1
                mediaPlayer.setDataSource(songlist[songcount].songURI)
                mediaPlayer.prepare()
                mediaPlayer.start()
                tvsongname.setText(songlist[songcount].songname)
                countDownTimer!!.cancel()
                countdowntimer()
            }
        }
        else
        {
            if (songlist.size == 1) {
                Toast.makeText(this, "No more songs", Toast.LENGTH_LONG).show()
                return
            }
            var random: Random = Random()
            var temp = random.nextInt(songlist.size)
            songcount = temp
            if (songcount != 0) {
                mediaPlayer.reset()
                songcount = songlist.size - 1
                mediaPlayer.setDataSource(songlist[songcount].songURI)
                mediaPlayer.prepare()
                mediaPlayer.start()
                tvsongname.setText(songlist[songcount].songname)
                countDownTimer!!.cancel()
                countdowntimer()
            } else {
                mediaPlayer.reset()
                songcount = songcount - 1
                mediaPlayer.setDataSource(songlist[songcount].songURI)
                mediaPlayer.prepare()
                mediaPlayer.start()
                tvsongname.setText(songlist[songcount].songname)
                countDownTimer!!.cancel()
                countdowntimer()
            }
        }
    }
    fun loop(view : View)
    {
        if(loop == false) {
            loop = true
            mediaPlayer.isLooping = true
            Toast.makeText(this,"On Loop",Toast.LENGTH_LONG).show()
        }
        else {
            loop = false
            mediaPlayer.isLooping = false
            Toast.makeText(this,"Not On Loop",Toast.LENGTH_LONG).show()
        }
    }
    fun shuffle(view : View)
    {
        if(onShuffle == false)
        {
            onShuffle=true
            Toast.makeText(this,"Active On Shuffle",Toast.LENGTH_LONG).show()
        }
        else
        {
            onShuffle=false
            Toast.makeText(this,"Not Active On Shuffle",Toast.LENGTH_LONG).show()
        }
    }
}
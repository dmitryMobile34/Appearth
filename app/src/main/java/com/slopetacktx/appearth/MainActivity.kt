package com.slopetacktx.appearth

import android.content.Intent
import android.content.pm.ActivityInfo
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.SeekBar
import android.widget.Toast
import androidx.core.os.postDelayed
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var mp: MediaPlayer? = null
    private var currentSong = mutableListOf(
        R.raw.shumpadayuscheyvodyivlesu,
        R.raw.muzyikadlyasnarelaks,
        R.raw.nejnayamelodiyadlyasna,
        R.raw.tihiydojdik,
        R.raw.uspokaivayuschayamelodiya
    )
    private var animationArray = mutableListOf(
        R.raw.infinityloading,
        R.raw.loaderloop,
        R.raw.loadingwaves,
        R.raw.loopingsucess,
        R.raw.penrosefivepointedstar
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

        val selectedAnimation = animationArray.random()
        lottieView.setAnimation(selectedAnimation)

        val selectedAudio = currentSong.random()

        when (selectedAudio) {
            R.raw.shumpadayuscheyvodyivlesu -> subTitle.text = "Лесная симфония"
            R.raw.muzyikadlyasnarelaks -> subTitle.text = "Светлое сознание"
            R.raw.nejnayamelodiyadlyasna -> subTitle.text = "Ясное сознание"
            R.raw.tihiydojdik -> subTitle.text = "Дождливая безмятежность"
            R.raw.uspokaivayuschayamelodiya -> subTitle.text = "Успокаивающая душа"
        }

        controlSound(selectedAudio)

        btnMenu.setOnClickListener {
            Toast.makeText(this, "Функция еще находится в разработке.", Toast.LENGTH_SHORT).show()
        }

        btnGuide.setOnClickListener {
            startActivity(Intent(this@MainActivity,GuideActivity::class.java))
        }




    }

    private fun controlSound(id: Int) {
        btnPlayAudio.setOnClickListener {
            if (mp == null) {
                mp = MediaPlayer.create(this, id)
                initialiseSeekBar()
                btnPlayAudio.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_stop, 0,  0, 0)
                lottieView.loop(true)
                lottieView.playAnimation()
                lottieView.isVisible = true
                textState.isVisible = true
            } else {
                mp?.stop()
                mp?.reset()
                mp?.release()
                mp = null
                btnPlayAudio.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_play, 0,  0, 0)
                lottieView.pauseAnimation()
                lottieView.isVisible = false
                textState.isVisible = false
            }
            mp?.start()

        }

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
//                if (fromUser) mp?.seekTo(progress)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) { }

            override fun onStopTrackingTouch(p0: SeekBar?) { }

        })
    }

    private fun initialiseSeekBar() {

        seekBar.max = mp!!.duration

        Handler().postDelayed(object : Runnable{
            override fun run() {
                try {
                    seekBar.progress = mp!!.currentPosition
                    Handler().postDelayed(this, 1000)
                } catch (e: Exception) {
                    seekBar.progress = 0
                }
            }
        }, 0)
    }



}
package com.example.mindmatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.WindowManager
import com.example.mindmatch.ui.screen.game.GameScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main){
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
       // this.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

    }
}
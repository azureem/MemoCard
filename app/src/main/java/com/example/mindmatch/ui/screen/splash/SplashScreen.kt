package com.example.mindmatch.ui.screen.splash

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mindmatch.R
import dagger.hilt.android.AndroidEntryPoint


class SplashScreen: Fragment(R.layout.screen_splash) {
    @SuppressLint("ObsoleteSdkInt")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requireActivity().window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        android.os.Handler().postDelayed({
            findNavController().navigate(R.id.action_splashScreen_to_menuScreen)
        }, 1500)

    }
}
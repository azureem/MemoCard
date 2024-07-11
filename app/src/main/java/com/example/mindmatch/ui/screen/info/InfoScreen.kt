package com.example.mindmatch.ui.screen.info

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mindmatch.R
import com.example.mindmatch.databinding.ScreenInfoBinding


class InfoScreen: Fragment(R.layout.screen_info) {

    private var binding: ScreenInfoBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =  ScreenInfoBinding.inflate(inflater, container, false)
        return  binding!!.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(requireActivity(), callback)
//        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
//        requireActivity().window.statusBarColor = Color.parseColor("#F1FAEE")
//        requireActivity().window.navigationBarColor = Color.parseColor("#1D3557")
        binding!!.back.setOnClickListener {
            findNavController().navigateUp()
        }


        binding!!.tgAnim.playAnimation()
        binding!!.tgAnim.setOnClickListener {
            telegram()
        }
        binding!!.instaAnim.playAnimation()
        binding!!.instaAnim.setOnClickListener {
            instagram()
        }
        binding!!.gmailAnim.playAnimation()
        binding!!.gmailAnim.setOnClickListener {
            composeEmail()
        }

    }

    private fun telegram() {
        try {
            val telegramUri = Uri.parse("https://t.me/mgalibovna")
            val telegramIntent = Intent(Intent.ACTION_VIEW, telegramUri)
            startActivity(telegramIntent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(requireContext(), "Telegram app not installed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun instagram() {
        val instagramUri = Uri.parse("https://instagram.com/_u/m.galibovna") //
        val instagramIntent = Intent(Intent.ACTION_VIEW, instagramUri)

        try {
            startActivity(instagramIntent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(requireContext(), "Instagram app not installed", Toast.LENGTH_SHORT).show()
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/m.galibovna")))
        }
    }

    private fun composeEmail() {
        val emailIntent = Intent(Intent.ACTION_SENDTO)
        emailIntent.data = Uri.parse("mailto:mehrigalibovna@gmail.com") // Replace with your email address
        startActivity(emailIntent)
    }

    private val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
        }
    }

}
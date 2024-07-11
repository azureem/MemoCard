package com.example.mindmatch.ui.screen.level

import android.graphics.Color
import android.os.Bundle
import android.system.Os.bind
import android.view.View
import android.view.WindowManager
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mindmatch.R
import com.example.mindmatch.data.model.CategoryLevel
import com.example.mindmatch.databinding.ScreenMenuBinding
import com.example.mindmatch.ui.dialog.Exit
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuScreen : Fragment(R.layout.screen_menu) {

    private val binding by viewBinding(ScreenMenuBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        requireActivity().getWindow().setNavigationBarColor(Color.TRANSPARENT)

        requireActivity().onBackPressedDispatcher.addCallback(requireActivity(), callback)

        binding.infoGo.setOnClickListener {
            findNavController().navigate(R.id.action_menuScreen_to_infoScreen)
        }
        binding.easy.setOnClickListener {
            findNavController().navigate(
                R.id.action_menuScreen_to_gameScreen,
                bundleOf("level" to CategoryLevel.EASY)
            )
        }
        binding.medium.setOnClickListener {
            findNavController().navigate(
                R.id.action_menuScreen_to_gameScreen,
                bundleOf("level" to CategoryLevel.MIDDLE)
            )
        }


        binding.hard.setOnClickListener {
            findNavController().navigate(
                R.id.action_menuScreen_to_gameScreen,
                bundleOf("level" to CategoryLevel.HARD)
            )
        }

    }

    private val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            var dlg = Exit()
            dlg.show(parentFragmentManager, "")
            dlg.onClickYes {
                requireActivity().finish()
            }
        }
    }
}
package com.example.mindmatch.ui.screen.win

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mindmatch.R
import com.example.mindmatch.databinding.WinDiaolgBinding

class WinDialog  : DialogFragment(R.layout.win_diaolg) {
    private val binding by viewBinding (WinDiaolgBinding::bind)
    private var clickedPlay: (() -> Unit)? = null
    private var clickedMenu: (() -> Unit)? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

//        binding.playWin.setOnClickListener {
//          clickedPlay?.invoke()
//
//        }


        binding!!.root.setOnClickListener {
            dismiss()
        }

        binding.menuWin.setOnClickListener {
            clickedMenu?.invoke()
        }
    }


    fun clickedPlay(block:(()->Unit)){
        this.clickedPlay = block
    } fun clickedMenu(block:(()->Unit)){
        this.clickedMenu = block
    }



    override fun onStart() {
        super.onStart()
        val window = dialog!!.window
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }
}
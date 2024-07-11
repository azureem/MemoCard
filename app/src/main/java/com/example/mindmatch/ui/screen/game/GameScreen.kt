package com.example.mindmatch.ui.screen.game

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.setMargins
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mindmatch.R
import com.example.mindmatch.data.model.CardData
import com.example.mindmatch.data.model.CategoryLevel
import com.example.mindmatch.databinding.ActivityMainBinding.bind
import com.example.mindmatch.databinding.ScreenGameBinding
import com.example.mindmatch.ui.dialog.GoHome
import com.example.mindmatch.ui.screen.level.MenuScreen
import com.example.mindmatch.ui.screen.viewModel.GameViewModel
import com.example.mindmatch.ui.screen.viewModel.impl.GameViewModelImpl
import com.example.mindmatch.ui.screen.win.WinDialog
import com.example.mindmatch.utils.closeAnim
import com.example.mindmatch.utils.hideAnim
import com.example.mindmatch.utils.openFirstAnim
import com.example.mindmatch.utils.openSecondAnim
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


@AndroidEntryPoint
class GameScreen : Fragment(R.layout.screen_game) {
    private val binding: ScreenGameBinding by viewBinding(ScreenGameBinding::bind)
    private val viewModel: GameViewModel by viewModels<GameViewModelImpl>()


    private val views = ArrayList<ImageView>()
    private var cardWidth = 0f
    private var cardHeight = 0f
    private var firstChosen = -1
    private var secondChosen = -1
    private var level = CategoryLevel.EASY
    private var wonCardsCount = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        // requireActivity().window.statusBarColor = Color.parseColor("#951A1B")
        requireActivity().window.navigationBarColor = Color.parseColor("#951A1B")


        binding.gohome.setOnClickListener {
            var dlg = GoHome()
            dlg.show(parentFragmentManager, "")
            dlg.onClickYes {
                findNavController().navigateUp()
            }
        }

        level = requireArguments().getSerializable("level") as CategoryLevel

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requireActivity().window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }


//        binding.reload.setOnClickListener {
//            reStart()
//            closeAllViews()
//            viewModel.closeAllViewsFlow
//                .onEach { closeAllViews() }
//                .launchIn(lifecycleScope)
//
//        }


        var t = level.toString().toLowerCase()
        binding.levelContainer.text = t


        binding.container.post {
            cardHeight = binding.container.height.toFloat()
            cardWidth = binding.container.width.toFloat()

            viewModel.loadCardsByLevel(level)
        }

//        binding.gohome.setOnClickListener {
//            findNavController().navigateUp()
//        }

        viewModel.cardFlow
            .onEach { loadViewsWithCardData(level, it) }
            .launchIn(lifecycleScope)

        viewModel.closeAllViewsFlow
            .onEach { closeAllViews() }
            .launchIn(lifecycleScope)
    }


    private fun loadViewsWithCardData(level: CategoryLevel, list: List<CardData>) {
        binding.container.columnCount = level.horCount
        binding.container.rowCount = level.verCount
        for (i in 0 until level.horCount) {
            for (j in 0 until level.verCount) {
                val img = ImageView(requireContext())
                binding.container.addView(img)
                img.isClickable = false
                img.tag = list[i * level.verCount + j]
                img.scaleType = ImageView.ScaleType.CENTER_CROP
                img.setImageResource(list[i * level.verCount + j].drawId)
                views.add(img)

                (img.layoutParams as GridLayout.LayoutParams).apply {
                    width = (cardWidth / level.horCount).roundToInt() - 40
                    height = (cardHeight / level.verCount).roundToInt() - 40
                    setMargins(20, 20, 20, 20)
                }
            }
        }
        clickReactions()
    }

//    private fun loadViewsWithCardData(level: CategoryLevel, list: List<CardData>) {
//        for (i in 0 until level.horCount) {
//            for (j in 0 until level.verCount) {
//                val img = ImageView(requireContext())
//                img.x = i * cardWidth
//                img.y = j * cardHeight
//                binding.container.addView(img)
//                img.isClickable = false
//                img.tag = list[i * level.verCount + j]
//                img.scaleType = ImageView.ScaleType.CENTER_CROP
//
//                img.setImageResource(list[i * level.verCount + j].drawId)
//                views.add(img)
//
//                val layPar = img.layoutParams as ViewGroup.MarginLayoutParams
//                layPar.apply {
//                    layPar.width = cardWidth.toInt()
//                    layPar.height = cardHeight.toInt()
//                    layPar.setMargins(30)
//                }
//                img.layoutParams = layPar
//
//            }
//        }
//        clickReactions()
//    }


    private fun clickReactions() {
        var media=MediaPlayer.create(requireActivity(), R.raw.start)
        views.forEachIndexed { index, imageView ->
            imageView.setOnClickListener {

                if (firstChosen != -1 && secondChosen != -1) return@setOnClickListener
                if (firstChosen == index || secondChosen == index) return@setOnClickListener

                if (firstChosen == -1) {
                    firstChosen = index
                    media.start()
                    imageView.openFirstAnim()
                } else {
                    secondChosen = index
                    media.start()
                    imageView.openSecondAnim {
                        check()
                    }
                }
            }
        }
    }

    private fun clearCards() {
        views.forEach { imageView ->
            binding.container.removeView(imageView)
        }
        views.clear()
        firstChosen = -1
        secondChosen = -1
        wonCardsCount = 0
    }


    @SuppressLint("LogNotTimber")
    private fun check() {
        val firstCard = views[firstChosen].tag as CardData
        val secondCard = views[secondChosen].tag as CardData
        var media = MediaPlayer.create(requireActivity(), R.raw.correct)
        var media2 = MediaPlayer.create(requireActivity(), R.raw.closed)
        if (firstCard.id == secondCard.id) {
            media.start()
            Log.d("TTT", "check: hiding both anim ")
            views[firstChosen].hideAnim()
            views[secondChosen].hideAnim {
                firstChosen = -1
                secondChosen = -1
                wonCardsCount += 2
                finish()
            }
        } else {
            media2.start()
            views[firstChosen].closeAnim()
            views[secondChosen].closeAnim {
                firstChosen = -1
                secondChosen = -1

            }
        }
    }

    private fun closeAllViews() {
        views.forEach { imageView ->
            imageView.closeAnim {
                imageView.isClickable = true
            }
        }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun finish() {
        if (wonCardsCount == level.verCount * level.horCount) {
            val winDialog = WinDialog()
            winDialog.show(parentFragmentManager, "show")
            winDialog.clickedMenu {
                winDialog.dismiss()
                backToMenu()
            }
            winDialog.clickedPlay {

            }
        }
    }

    private fun backToMenu() {
        findNavController().navigateUp()
    }

    fun reStart() {
        clearCards()
//        lifecycleScope.launch {  // Launch coroutine for closing animations within viewModelScope
//            views.forEach { imageView ->
//                imageView.closeAnim {
//                    imageView.isClickable = true  // Set clickable after animation
//                }
//            }
//        }
        viewModel.loadCardsByLevel(level)  // Load new cards after closing
    }

}
// will be created function which is responsible for dynamically creating and displaying the game cards
// based on the specified level and the list of card data received from the ViewModel.
//img.x = i * cardWidth:
// This line sets the horizontal position (left-to-right) of the card.
// It multiplies the column index i by the width of each card (cardWidth).
// So, if i is 0, the card will be positioned at the left edge of the screen.
// If i is 1, the card will be positioned to the right of the first card, and so on.

//img.y = j * cardHeight: This line sets the vertical position (top-to-bottom) of the card.
// It multiplies the row index j by the height of each card (cardHeight).
// So, if j is 0, the card will be positioned at the top of the screen.
// If j is 1, the card will be positioned below the first row of cards, and so on.


//    private fun loadViewsWithCardData(level: CategoryLevel, list: List<CardData>) {
//        val container = (binding.container.getRootView() as? ConstraintLayout) ?: return
//
//        for (i in 0 until level.horCount) {
//            for (j in 0 until level.verCount) {
//                val img = ImageView(requireContext())
//                img.isClickable = false
//                img.tag = list[i * level.verCount + j]
//                img.scaleType = ImageView.ScaleType.CENTER_CROP
//                img.setImageResource(list[i * level.verCount + j].drawId)
//                views.add(img)
//
//                // Calculate margins based on card dimensions
//                val leftMargin = (cardWidth * 0.1f * i).toInt() // Adjust multiplier for desired margin size
//                val topMargin = (cardHeight * 0.1f * j).toInt()
//                val rightMargin = ((level.horCount - 1 - i) * cardWidth * 0.1f).toInt()
//                val bottomMargin = ((level.verCount - 1 - j) * cardHeight * 0.1f).toInt()
//
//                // Create a new LayoutParams object
//                val layoutParams = ConstraintLayout.LayoutParams(
//                    ConstraintLayout.LayoutParams.WRAP_CONTENT,
//                    ConstraintLayout.LayoutParams.WRAP_CONTENT
//                )
//
//                // Set margins
//                layoutParams.setMargins(leftMargin, topMargin, rightMargin, bottomMargin)
//
//                // Apply layout params to the image view
//                img.layoutParams = layoutParams
//
//                container.addView(img)
//            }
//        }
//        clickReactions()
//    }
//
//


//
//    private fun loadViewsWithCardData(level: CategoryLevel, list: List<CardData>) {
//        for (i in 0 until level.horCount) {
//            for (j in 0 until level.verCount) {
//                val img = ImageView(requireContext())
//                img.isClickable = false
//                img.tag = list[i * level.verCount + j]
//                img.scaleType = ImageView.ScaleType.CENTER_CROP
//                img.setImageResource(list[i * level.verCount + j].drawId)
//                views.add(img);
//
//                // Create a new ConstraintLayout.LayoutParams for margin control
//                val layoutParams = ConstraintLayout.LayoutParams(
//                    ConstraintLayout.LayoutParams.WRAP_CONTENT,
//                    ConstraintLayout.LayoutParams.WRAP_CONTENT
//                );
//                layoutParams.setMargins(30, 30, 30, 30); // Set margins (left, top, right, bottom)
//
//                // Constrain the image using ConstraintLayout.LayoutParams setters
//                layoutParams.marginStart = 30; // Set left margin
//                layoutParams.marginEnd = 30;  // Set right margin
//                layoutParams.topMargin = 30;   // Set top margin
//                layoutParams.bottomMargin = 30; // Set bottom margin
//
//                // Calculate image position based on card dimensions and grid layout
//                val xPosition = i * cardWidth;
//                val yPosition = j * cardHeight;
//
//                // Set the layout params with margins and constraints
//                img.layoutParams = layoutParams;
//            }
//        }
//        clickReactions();
//    }
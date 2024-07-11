package com.example.mindmatch.utils

import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.widget.ImageView
import com.example.mindmatch.R
import com.example.mindmatch.data.model.CardData

fun ImageView.openFirstAnim() {
    this.animate()
        .setDuration(400)
        .rotationY(89f)
        .withEndAction {
            this.rotationY = -89f
            val cardData = this.tag as CardData
            this.setImageResource(cardData.drawId)
            this.animate()
                .setDuration(400)
                .scaleY(1f)
                .rotationY(0f)
                .start()
        }
        .start()
}


fun ImageView.openSecondAnim(endAnim: () -> Unit) {
    this.animate()
        .setDuration(400)
        .rotationY(89f)
        .withEndAction {
            this.rotationY = -89f
            val cardData = this.tag as CardData
            this.setImageResource(cardData.drawId)
            this.animate()
                .setDuration(400)
                .rotationY(0f)
                .withEndAction(endAnim)
                .start()
        }
        .start()
}

fun ImageView.closeAnim() {
    this.animate()
        .setDuration(400)
        .rotationY(-89f)
        .withEndAction {
            this.rotationY = 89f
            this.setImageResource(R.drawable.mbackcard)
            this.animate()
                .setDuration(400)
                .rotationY(0f)
                .start()
        }
        .start()

}


fun ImageView.closeAnim(endAnim: () -> Unit) {
    this.animate()
        .setDuration(400)
        .rotationY(-89f)
        .withEndAction {
            this.rotationY = 89f
            this.setImageResource(R.drawable.mbackcard)
            this.animate()
                .setDuration(400)
                .rotationY(0f)
                .withEndAction(endAnim)
                .start()
        }
        .start()

}


//fun ImageView.hideAnim(){
//    this.animate()
//        .setDuration(400)
//        .scaleY(0f)
//        .scaleX(0f)
//        .start()
//}
//
//fun ImageView.hideAnim(endAnim: () -> Unit){
//    this.animate()
//        .setDuration(400)
//        .scaleY(0f)
//        .scaleX(0f)
//        .withEndAction(endAnim)
//        .start()
//}
val lowContrastMatrix = ColorMatrix().apply {
    setSaturation(0f) // Set saturation to 0 for grayscale
}

fun ImageView.hideAnim() {
    val colorFilter = ColorMatrixColorFilter(lowContrastMatrix)
    this.animate()
        .setDuration(400)
        .scaleY(1f) // Keep scale at 1 to maintain image size
        .scaleX(1f)
        .withEndAction { this.setColorFilter(colorFilter)
            this.isClickable = false} // Set filter at the end
        .start()
}

fun ImageView.hideAnim(endAnim: () -> Unit) {
    val colorFilter = ColorMatrixColorFilter(lowContrastMatrix)
    this.animate()
        .setDuration(400)
        .scaleY(1f)
        .scaleX(1f)
        .withEndAction {
            this.setColorFilter(colorFilter)
            this.isClickable = false
            endAnim()
        }
        .start()
}
package com.enesuzumcu.boyakazan

import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.enesuzumcu.boyakazan.model.Player

fun updateBackgroundResource(player: Player?, imageView: ImageView, isPlayer1: Boolean) {
    if (player == null) {
        if (isPlayer1) {
            imageView.setBackgroundResource(R.drawable.custom_button_black)
        } else {
            imageView.setBackgroundResource(R.drawable.custom_button_red)
        }
    } else {
        when (player.color) {
            ContextCompat.getColor(imageView.context, R.color.lime) -> {
                imageView.setBackgroundResource(R.drawable.custom_button_lime)
            }
            ContextCompat.getColor(imageView.context, R.color.gray) -> {
                imageView.setBackgroundResource(R.drawable.custom_button_gray)
            }
            ContextCompat.getColor(imageView.context, R.color.black) -> {
                imageView.setBackgroundResource(R.drawable.custom_button_black)
            }
            ContextCompat.getColor(imageView.context, R.color.green) -> {
                imageView.setBackgroundResource(R.drawable.custom_button_green)
            }
            ContextCompat.getColor(imageView.context, R.color.orange) -> {
                imageView.setBackgroundResource(R.drawable.custom_button_orange)
            }
            ContextCompat.getColor(imageView.context, R.color.red) -> {
                imageView.setBackgroundResource(R.drawable.custom_button_red)
            }
            ContextCompat.getColor(imageView.context, R.color.blue) -> {
                imageView.setBackgroundResource(R.drawable.custom_button_blue)
            }
            ContextCompat.getColor(imageView.context, R.color.brown) -> {
                imageView.setBackgroundResource(R.drawable.custom_button_brown)
            }
            ContextCompat.getColor(imageView.context, R.color.yellow) -> {
                imageView.setBackgroundResource(R.drawable.custom_button_yellow)
            }
        }
    }
}

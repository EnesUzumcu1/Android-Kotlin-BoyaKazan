package com.enesuzumcu.boyakazan.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Player(var name: String, var color: Int) : Parcelable{
    companion object {
        var player1 : Player? = null
        var player2 : Player? = null
        fun setPlayer1(name: String,color: Int){
            player1 = Player( name, color)
        }
        fun setPlayer2(name: String,color: Int){
            player2 = Player( name, color)
        }
    }
}
package com.enesuzumcu.boyakazan.model

data class Player(var name: String, var color: Int){
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
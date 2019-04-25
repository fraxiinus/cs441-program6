package com.etirps.zhu.gachasimulator

import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class GameController(private val game_layout: View) {

    private val onButtonClick = View.OnClickListener { view ->
        Toast.makeText(game_layout.context, "button clicked!!!", Toast.LENGTH_SHORT).show()
        game_layout.findViewById<TextView>(R.id.message).text = (game_layout.context.applicationContext as ApplicationData).cashMoney.toString()
    }

    init {
        game_layout.findViewById<Button>(R.id.button).setOnClickListener(onButtonClick)
    }

}
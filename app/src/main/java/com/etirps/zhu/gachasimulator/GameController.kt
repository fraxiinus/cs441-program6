package com.etirps.zhu.gachasimulator

import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.beust.klaxon.JsonObject

class GameController(private val game_layout: View) {

    private val redditFunctions = RedditFunctions(game_layout.context)

    private val onButtonClick = View.OnClickListener { view ->
        //Toast.makeText(game_layout.context, "button clicked!!!", Toast.LENGTH_SHORT).show()
        //game_layout.findViewById<TextView>(R.id.message).text = (game_layout.context.applicationContext as ApplicationData).cashMoney.toString()

        redditFunctions.pullNewCharacter(RARITY_SCALE.values().random(), object: ServerCallback {

            override fun onSuccess(result: RedditData) {
                game_layout.findViewById<TextView>(R.id.message).text = "${result.rarity} - ${result.title}"
            }

            override fun onFailure(reason: String) {
                game_layout.findViewById<TextView>(R.id.message).text = reason
            }

        })
    }

    init {
        game_layout.findViewById<Button>(R.id.button).setOnClickListener(onButtonClick)
    }

}
package com.etirps.zhu.gachasimulator

import android.view.View
import android.widget.Button
import android.widget.TextView

class ShopController(private val shop_layout: View): ViewController(shop_layout) {

    private val orbCounter = shop_layout.findViewById<TextView>(R.id.orb_counter)
    //private val appData =  shop_layout.context.applicationContext as ApplicationData

    private val add1Orbs = View.OnClickListener { view ->
        appData.cashMoney += 1
        orbCounter.text = appData.cashMoney.toString()
    }

    private val add5Orbs = View.OnClickListener { view ->
        appData.cashMoney += 5
        orbCounter.text = appData.cashMoney.toString()
    }

    private val add10Orbs = View.OnClickListener { view ->
        appData.cashMoney += 10
        orbCounter.text = appData.cashMoney.toString()
    }

    override fun updateView() {
        orbCounter.text = appData.cashMoney.toString()
    }

    init {
        shop_layout.findViewById<Button>(R.id.orb_button_1).setOnClickListener(add1Orbs)
        shop_layout.findViewById<Button>(R.id.orb_button_2).setOnClickListener(add5Orbs)
        shop_layout.findViewById<Button>(R.id.orb_button_3).setOnClickListener(add10Orbs)
    }

}
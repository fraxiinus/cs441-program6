package com.etirps.zhu.gachasimulator

import android.view.View
import android.widget.TextView

class ShopController(private val shop_layout: View): ViewController(shop_layout) {

    private val messageTV = shop_layout.findViewById<TextView>(R.id.message)
    //private val appData =  shop_layout.context.applicationContext as ApplicationData

    override fun updateView() {
        messageTV.text = appData.cashMoney.toString()
    }

}
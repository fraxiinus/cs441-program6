package com.etirps.zhu.gachasimulator

import android.view.View
import android.widget.ListView
import android.widget.TextView
import com.android.volley.toolbox.ImageLoader

class CollectionController(private val collection_layout: View, val imageLoader: ImageLoader): ViewController(collection_layout) {

    override fun updateView() {
        collection_layout.findViewById<TextView>(R.id.message).text = appData.memeCollection.size.toString()

        val adapter = CharacterAdapter(collection_layout.context, appData.memeCollection, imageLoader)
        val listView = collection_layout.findViewById<ListView>(R.id.collection_list)
        listView.adapter = adapter
    }

}
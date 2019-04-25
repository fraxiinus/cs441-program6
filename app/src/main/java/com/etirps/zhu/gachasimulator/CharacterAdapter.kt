package com.etirps.zhu.gachasimulator

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.NetworkImageView

data class ViewHolder(var title: TextView?, var rarity: TextView?, var image: NetworkImageView?)

class CharacterAdapter(context: Context, characters: List<RedditData>, var imageLoader: ImageLoader): ArrayAdapter<RedditData>(context, R.layout.character_item, characters.reversed()) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        var cview = convertView
        val character = getItem(position)

        lateinit var viewHolder: ViewHolder

        if(cview == null) {
            val inflater = LayoutInflater.from(context)
            cview = inflater.inflate(R.layout.character_item, parent, false)
            viewHolder = ViewHolder(title = cview?.findViewById(R.id.title_tv), rarity = cview?.findViewById(R.id.rarity_tv), image = cview?.findViewById(R.id.iv1))
            cview?.tag = viewHolder
        } else {
            viewHolder = cview.tag as ViewHolder
        }

        viewHolder.title?.text = character?.title
        viewHolder.rarity?.text = character?.rarity
        viewHolder.image?.setImageUrl(character?.url, imageLoader)

        when(character?.rarityValue) {
            0 -> {
                viewHolder.rarity?.setTextAppearance(R.style.ZeroRarityText)
            }

            1 -> {
                viewHolder.rarity?.setTextAppearance(R.style.OneRarityText)
            }

            2 -> {
                viewHolder.rarity?.setTextAppearance(R.style.TwoRarityText)
            }

            3 -> {
                viewHolder.rarity?.setTextAppearance(R.style.ThreeRarityText)
            }
        }

        return cview
    }
}
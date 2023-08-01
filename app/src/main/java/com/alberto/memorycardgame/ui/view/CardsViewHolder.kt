package com.alberto.memorycardgame.ui.view

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.alberto.memorycardgame.R
import com.alberto.memorycardgame.data.model.Card
import com.alberto.memorycardgame.databinding.CardsViewBinding
import com.alberto.memorycardgame.ui.view.activities.GameActivity

class CardsViewHolder(view : View) : ViewHolder(view) {

    private val binding = CardsViewBinding.bind(view)

    private val cardWidth = GameActivity.getScreenWidth()

    private val params: ViewGroup.LayoutParams = binding.cvCardItem.layoutParams

    fun bind(card: Card, onClickListener : (Card) -> Unit){
        params.width = cardWidth/5
        binding.cvCardItem.layoutParams = params
        binding.ivCardImage.setImageResource(R.drawable.card_back_red)
        binding.cvCardItem.setOnClickListener {
            onClickListener(card)
        }
    }

    fun bindFlipped(card : Card){
        params.width = cardWidth/5
        binding.cvCardItem.layoutParams = params
        binding.ivCardImage.scaleType = ImageView.ScaleType.CENTER_INSIDE
        binding.ivCardImage.setImageResource(card.imageContent)
    }

}
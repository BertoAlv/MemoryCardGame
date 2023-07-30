package com.alberto.memorycardgame.domain

import com.alberto.memorycardgame.data.model.Card
import javax.inject.Inject

class InitializeCardListUseCase @Inject constructor() {

    operator fun invoke(cardList : MutableList<Card>, cardImages : Array<Int>) : List<Card> {
        val allIndices = mutableListOf<Int>()

        for (i in cardImages.indices) {
            allIndices.add(i)
            allIndices.add(i)
        }

        allIndices.shuffle()
        allIndices.shuffle()

        for (i in 0 until allIndices.size) {
            val randomIndex = allIndices[i]
            val imageResourceId = cardImages[randomIndex]
            cardList.add(Card(i,imageResourceId, false))
        }

        return cardList
    }
}
package com.alberto.memorycardgame.domain

import com.alberto.memorycardgame.data.model.Card
import javax.inject.Inject

class UpdateCardUseCase @Inject constructor() {

    operator fun invoke(cardList: MutableList<Card>, updateCard : Card) : List<Card> {

        val index = cardList.indexOfFirst { it.id == updateCard.id }

        return if (index != -1) {
            cardList[index] = updateCard
            cardList
        } else {
            emptyList()
        }
    }
}
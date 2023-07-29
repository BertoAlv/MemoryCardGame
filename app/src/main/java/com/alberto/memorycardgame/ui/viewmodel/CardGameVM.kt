package com.alberto.memorycardgame.ui.viewmodel

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.alberto.memorycardgame.R
import com.alberto.memorycardgame.data.model.Card
import com.alberto.memorycardgame.domain.InitializeCardListUseCase
import com.alberto.memorycardgame.domain.UpdateCardUseCase

import dagger.hilt.android.lifecycle.HiltViewModel

import javax.inject.Inject

@HiltViewModel
class CardGameVM @Inject constructor(private val initializeCardList : InitializeCardListUseCase, private val updateCard : UpdateCardUseCase) : ViewModel() {

    val cardListLD = MutableLiveData<List<Card>>()
    var cardList = mutableListOf<Card>()
    private val flippedCards = mutableListOf<Card>()
    private var canClick : Boolean = true

    private val cardImageResourceIds = arrayOf(
        R.drawable.frutas_cereza,
        R.drawable.frutas_mandarina,
        R.drawable.frutas_melon,
        R.drawable.frutas_pera,
        R.drawable.frutas_pi_a,
        R.drawable.frutas_platano,
        R.drawable.frutas_sandia,
        R.drawable.frutas_tomate)

    fun initializeCardList() {
        val initializedList = initializeCardList(cardList,cardImageResourceIds)
        cardListLD.postValue(initializedList)
    }

    private fun updateCard(updatedCard: Card) {
        cardListLD.postValue(updateCard(cardList, updatedCard))
    }

    fun flipCard(card: Card) {
        if ((!card.isRevealed) && (canClick)){
            card.isRevealed = true
            flippedCards.add(card)
            updateCard(card)
        }
        if (flippedCards.size == 2) {
            canClick = false
            val card1 = flippedCards[0]
            val card2 = flippedCards[1]
            if (!areCardsMatching(card1, card2)) {
                wrongGuess(card1,card2)
            }
            else{
                rightGuess()
            }
            flippedCards.clear()
        }

    }

    private fun areCardsMatching(card1: Card, card2: Card): Boolean {
        return card1.imageContent == card2.imageContent
    }

    private fun wrongGuess(card1: Card, card2: Card){
        Handler(Looper.getMainLooper()).postDelayed({
            card1.isRevealed = false
            card2.isRevealed = false
            updateCard(card1)
            updateCard(card2)
            canClick=true
        }, 800)
    }

    private fun rightGuess(){
        canClick=true
    }

}
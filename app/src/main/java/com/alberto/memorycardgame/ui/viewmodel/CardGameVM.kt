package com.alberto.memorycardgame.ui.viewmodel

import android.app.AlertDialog
import android.media.Image
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
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
    val winDialog = MutableLiveData<Boolean>()
    val loseDialog = MutableLiveData<Boolean>()
    val playerScore = MutableLiveData<Int>()
    var score = 0
    val remainingLives = MutableLiveData<Int>()
    var lives = 6
    private var cardList = mutableListOf<Card>()
    private val flippedCards = mutableListOf<Card>()
    private var canClick : Boolean = true

    private val cardImageResourceIds = arrayOf(
        R.drawable.cherries_no_bg,
        R.drawable.clementine_no_bg,
        R.drawable.melon_no_bg,
        R.drawable.pear_no_bg,
        R.drawable.pineapple_no_bg,
        R.drawable.banana_no_bg,
        R.drawable.watermelon_no_bg,
        R.drawable.tomato_no_bg)

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
            if (checkIfFinish()){
                winDialog.postValue(true)
            }
        }

    }

    private fun areCardsMatching(card1: Card, card2: Card): Boolean {
        return card1.imageContent == card2.imageContent
    }

    private fun wrongGuess(card1: Card, card2: Card){
        score -= 25
        playerScore.postValue(score)
        lives -= 1
        remainingLives.postValue(lives)
        Handler(Looper.getMainLooper()).postDelayed({
            card1.isRevealed = false
            card2.isRevealed = false
            updateCard(card1)
            updateCard(card2)
            canClick=true
        }, 800)
        if (lives == 0){
            loseDialog.postValue(true)
        }
    }

    private fun rightGuess() {
        score+=100
        playerScore.postValue(score)
        canClick=true
    }

    private fun checkIfFinish() : Boolean = cardList.all { it.isRevealed }

    fun restartGame() {
        cardList.clear()
        initializeCardList()
        score = 0
        lives = 6
    }
}
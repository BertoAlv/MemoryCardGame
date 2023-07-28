package com.alberto.memorycardgame.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alberto.memorycardgame.R
import com.alberto.memorycardgame.data.model.Card
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class CardGameVM @Inject constructor() : ViewModel() {

    val cardListLD = MutableLiveData<List<Card>>()
    var cardList = mutableListOf<Card>()

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
        val allIndices = mutableListOf<Int>()

        for (i in cardImageResourceIds.indices) {
            allIndices.add(i)
            allIndices.add(i)
        }

        allIndices.shuffle()

        for (i in 0 until allIndices.size) {
            val randomIndex = allIndices[i]
            val imageResourceId = cardImageResourceIds[randomIndex]
            cardList.add(Card(imageResourceId, false))
        }

        cardListLD.postValue(cardList)
    }

    fun updateCard(updatedCard: Card) {
        val currentList = cardList
        val index = currentList.indexOf(updatedCard)
        if (index != -1) {
            currentList[index] = updatedCard
            cardListLD.postValue(currentList)
            cardList = currentList
        }
    }

}
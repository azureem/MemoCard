package com.example.mindmatch.domain

import com.example.mindmatch.R
import com.example.mindmatch.data.model.CardData
import com.example.mindmatch.data.model.CategoryLevel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepositoryImpl @Inject constructor() : AppRepository {

    private val listOfCardData = ArrayList<CardData>()

    init {
        loadingAllCards()
    }

    private fun loadingAllCards() {
        listOfCardData.add(CardData(1, R.drawable.mfrog))
        listOfCardData.add(CardData(2, R.drawable.mcorn))
        listOfCardData.add(CardData(4, R.drawable.mflower))
        listOfCardData.add(CardData(5, R.drawable.mgggg))
        listOfCardData.add(CardData(6, R.drawable.melep))
        listOfCardData.add(CardData(7, R.drawable.mdolphin))
        listOfCardData.add(CardData(8, R.drawable.mpinguin))
        listOfCardData.add(CardData(9, R.drawable.mcoffe))
        listOfCardData.add(CardData(10, R.drawable.yedinorog))
        listOfCardData.add(CardData(11, R.drawable.mbee1))
        listOfCardData.add(CardData(12, R.drawable.mstar))
        listOfCardData.add(CardData(13, R.drawable.ot))
        listOfCardData.add(CardData(15, R.drawable.mbird))
        listOfCardData.add(CardData(16, R.drawable.mbee2))

        listOfCardData.add(CardData(17, R.drawable.mdog))


        listOfCardData.add(CardData(18, R.drawable.sobaka))
        listOfCardData.add(CardData(19, R.drawable.mcat))
        listOfCardData.add(CardData(21, R.drawable.mice2))
        listOfCardData.add(CardData(22, R.drawable.mlion))
        listOfCardData.add(CardData(23, R.drawable.msun))
        listOfCardData.add(CardData(24, R.drawable.mdolphin2))
        listOfCardData.add(CardData(25, R.drawable.municorn3))
        listOfCardData.add(CardData(26, R.drawable.mrabbit))
        listOfCardData.add(CardData(27, R.drawable.sobaka))
        listOfCardData.add(CardData(28, R.drawable.mcake))

    }


    override fun gettingCardsByLevel(level: CategoryLevel): Flow<List<CardData>> = flow {
        listOfCardData.shuffle() //just shuffling the values
        val finalList = ArrayList<CardData>() // new list for loading cardData according its levelEnum
        val numberOfUniqueImg = level.horCount * level.verCount / 2  // dividing by 2, because every image must be twice
        finalList.addAll(
            listOfCardData.subList(0, numberOfUniqueImg)
        ) //adding the same values twice
        finalList.addAll(listOfCardData.subList(0, numberOfUniqueImg))
        finalList.shuffle() //again shuffling
        emit(finalList) // this emit was created by flow = {}
    }.flowOn(Dispatchers.IO)

}
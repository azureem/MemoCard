package com.example.mindmatch.domain

import com.example.mindmatch.data.model.CardData
import com.example.mindmatch.data.model.CategoryLevel
import kotlinx.coroutines.flow.Flow

interface AppRepository {

    fun gettingCardsByLevel(level: CategoryLevel) : Flow<List<CardData>>

}
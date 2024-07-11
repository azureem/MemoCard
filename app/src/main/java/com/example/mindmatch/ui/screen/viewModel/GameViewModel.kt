package com.example.mindmatch.ui.screen.viewModel

import com.example.mindmatch.data.model.CardData
import com.example.mindmatch.data.model.CategoryLevel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

interface GameViewModel {

     val cardFlow: SharedFlow<List<CardData>>
     val closeAllViewsFlow: SharedFlow<Unit>
     fun loadCardsByLevel(level: CategoryLevel)
}
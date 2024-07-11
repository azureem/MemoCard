package com.example.mindmatch.ui.screen.viewModel.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mindmatch.data.model.CardData
import com.example.mindmatch.data.model.CategoryLevel
import com.example.mindmatch.domain.AppRepository
import com.example.mindmatch.ui.screen.viewModel.GameViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModelImpl @Inject constructor(private val repo: AppRepository) : GameViewModel, ViewModel() {
    override val cardFlow = MutableSharedFlow<List<CardData>>(replay = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)
    override val closeAllViewsFlow = MutableSharedFlow<Unit>(replay = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)


    init {
        viewModelScope.launch {
            delay(1000)
            closeAllViewsFlow.emit(Unit)
        }
    }

    override fun loadCardsByLevel(level: CategoryLevel) {
        repo.gettingCardsByLevel(level)
            .onEach { cardFlow.emit(it) }
            .launchIn(viewModelScope)
    }
}
package com.dgelormini.mvisample.presentation.notelist

import androidx.lifecycle.ViewModel
import com.dgelormini.mvisample.domain.GetNoteListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.scan

@OptIn(ExperimentalCoroutinesApi::class)
class NoteListViewModel(
    private val loadNoteListUseCase: GetNoteListUseCase
) : ViewModel()

// TODO: Implement

//    override val initialState = initialState ?: State(isIdle = true)

/*    private val reducer: Reducer<State, Change> = { state, change ->
        when (change) {
            is Change.Loading -> state.copy(
                isIdle = false,
                isLoading = true,
                notes = emptyList(),
                isError = false
            )
            is Change.Notes -> state.copy(
                isLoading = false,
                notes = change.notes
            )
            is Change.Error -> state.copy(
                isLoading = false,
                isError = true
            )
        }
    }*/
/*
    init {
        viewModelScope.launch {
            bindActions()
        }
    }

    @ExperimentalCoroutinesApi
    private suspend fun bindActions() {
        val loadNotesChange: Flow<Change> = actions.filterIsInstance<Action.LoadNotes>()
            .mapLatest { Change.Notes(loadNoteListUseCase.loadAll().ifEmpty { emptyList() }) }
            .catch<Change> { emit(Change.Error(it)) } // TODO: Do we need to emit?
            .flowOn(Dispatchers.IO)
            .onStart { emit(Change.Loading) }

        loadNotesChange.scan(initialState) { state, change -> reducer(state, change) }
            .filterNot { it.isIdle }
            .distinctUntilChanged()
            .collect {
                state.value = it
            }
    }*/


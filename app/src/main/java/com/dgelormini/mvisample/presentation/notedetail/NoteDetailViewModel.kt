package com.dgelormini.mvisample.presentation.notedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dgelormini.mvisample.domain.DeleteNoteUseCase
import com.dgelormini.mvisample.domain.GetNoteDetailUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import timber.log.Timber

class NoteDetailViewModel(
    private val noteDetailUseCase: GetNoteDetailUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase
) : ViewModel() {

    // TODO: Implement

    /*override val initialState = initialState ?: State(isIdle = true)

    private val reducer: Reducer<State, Change> = { state, change ->
        when (change) {
            is Change.Loading -> state.copy(
                isLoading = true,
                note = null,
                isIdle = false,
                isLoadError = false,
                isDeleteError = false
            )
            is Change.NoteDetail -> state.copy(
                isLoading = false,
                note = change.note
            )
            is Change.NoteLoadError -> state.copy(
                isLoading = false,
                isLoadError = true
            )
            Change.NoteDeleted -> state.copy(
                isLoading = false,
                isNoteDeleted = true
            )
            is Change.NoteDeleteError -> state.copy(
                isLoading = false,
                isDeleteError = true
            )
        }
    }

    init {
        viewModelScope.launch {
            bindActions()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private suspend fun bindActions() {
        val loadNoteChange = actions.filterIsInstance<Action.LoadNoteDetail>()
            .mapLatest { action ->
                Timber.v("Received action: $action, thread; ${Thread.currentThread().name}")
                Change.NoteDetail(noteDetailUseCase.findById(action.noteId))
            }
            .flowOn(Dispatchers.IO)
            .onStart<Change> { emit(Change.Loading) }
            .catch { emit(Change.NoteLoadError(it)) }

        val deleteNoteChange = actions.filterIsInstance<Action.DeleteNote>()
            .mapLatest { action ->
                Timber.v("Received action: $action, thread; ${Thread.currentThread().name}")
                val findById = noteDetailUseCase.findById(action.noteId)
                deleteNoteUseCase.delete(findById)
                Change.NoteDeleted
            }
            .onStart<Change> { emit(Change.Loading) }
            .catch { emit(Change.NoteDeleteError(it)) }
            .flowOn(Dispatchers.IO)

        val allChanges = merge(loadNoteChange, deleteNoteChange)

        allChanges.scan(initialState) { state, change -> reducer(state, change) }
            .filter { !it.isIdle && !it.isLoading }
            .distinctUntilChanged()
            .collect {
                state.postValue(it)
            }
    }*/
}

package com.dgelormini.mvisample.presentation.notedetail

import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.dgelormini.mvisample.domain.DeleteNoteUseCase
import com.dgelormini.mvisample.domain.GetNoteDetailUseCase

internal class NoteDetailViewModel(
    private val noteDetailUseCase: GetNoteDetailUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    initialState: State
) : MavericksViewModel<State>(initialState) {
    companion object :
        MavericksViewModelFactory<NoteDetailViewModel, State> {
        override fun create(viewModelContext: ViewModelContext, state: State): NoteDetailViewModel {
            return NoteDetailViewModel(GetNoteDetailUseCase(), DeleteNoteUseCase(), state)
        }

        override fun initialState(viewModelContext: ViewModelContext): State {
            return State(isIdle = true)
        }
    }

    fun loadNoteDetail(noteId: Long) {
        suspend {
            noteDetailUseCase.findById(noteId)
        }.execute {
            copy(note = it.invoke(), isLoading = !it.complete)
        }
    }

    fun deleteNote(noteId: Long) {
        suspend {
            val note = noteDetailUseCase.findById(noteId)
            deleteNoteUseCase.delete(note)
        }.execute {
            val result = it.invoke()
            copy(isLoading = !it.complete, isNoteDeleted = result==Unit, isLoadError = result == null)
        }
    }

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

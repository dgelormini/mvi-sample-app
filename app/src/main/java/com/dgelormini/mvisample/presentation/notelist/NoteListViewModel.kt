package com.dgelormini.mvisample.presentation.notelist

import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.dgelormini.mvisample.domain.GetNoteListUseCase
import com.dgelormini.mvisample.domain.Note
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay

@OptIn(ExperimentalCoroutinesApi::class)
class NoteListViewModel(
    private val loadNoteListUseCase: GetNoteListUseCase,
    initialState: State
) : MavericksViewModel<State>(initialState) {

    companion object : MavericksViewModelFactory<NoteListViewModel, State> {
        override fun create(viewModelContext: ViewModelContext, state: State): NoteListViewModel {
            return NoteListViewModel(GetNoteListUseCase(), state)
        }

        override fun initialState(viewModelContext: ViewModelContext): State {
            return State(isIdle = true)
        }
    }

    fun selectNote(note: Note) {
        // TODO: Implement with MVI; currently just using click listener in Activity.
    }

    fun loadNotes() {
        suspend {
            delay(750)
            loadNoteListUseCase.loadAll()
        }.execute {
            println(this)
            copy(isLoading = !it.complete, notes = it.invoke() ?: emptyList())
        }
    }
}



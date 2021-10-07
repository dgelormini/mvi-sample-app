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
            copy(
                isLoading = !it.complete,
                isNoteDeleted = result == Unit,
                isLoadError = result == null
            )
        }
    }
}

package com.dgelormini.mvisample.presentation.notedetail

import androidx.lifecycle.ViewModel
import com.dgelormini.mvisample.domain.DeleteNoteUseCase
import com.dgelormini.mvisample.domain.GetNoteDetailUseCase
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

internal class NoteDetailViewModel(
    private val noteDetailUseCase: GetNoteDetailUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase
) : ContainerHost<State, SideEffect>, ViewModel() {
    override val container: Container<State, SideEffect> = container(State())

    fun loadNoteDetail(noteId: Long) = intent {
        reduce {
            state.copy(isLoading = true)
        }

        val loadedNote = noteDetailUseCase.findById(noteId)

        reduce {
            state.copy(note = loadedNote)
        }
    }

    fun deleteNote(noteId: Long) = intent {
        reduce {
            state.copy(isLoading = true)
        }

        val deleteSuccess = try {
            val note = noteDetailUseCase.findById(noteId)
            deleteNoteUseCase.delete(note)
            true
        } catch (e: Exception) {
            false
        }

        reduce {
            state.copy(
                isLoading = false,
                isNoteDeleted = deleteSuccess,
                isDeleteError = !deleteSuccess
            )
        }
    }
}

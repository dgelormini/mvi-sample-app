package com.dgelormini.mvisample.presentation.notelist

import androidx.lifecycle.ViewModel
import com.dgelormini.mvisample.domain.GetNoteListUseCase
import com.dgelormini.mvisample.domain.Note
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

@OptIn(ExperimentalCoroutinesApi::class)
class NoteListViewModel(
    private val loadNoteListUseCase: GetNoteListUseCase
) : ContainerHost<State, SideEffect>, ViewModel() {

    override val container: Container<State, SideEffect> = container(State())

    fun selectNote(note: Note) = intent {
        // TODO: Does it make sense to just emit a side-effect?
        postSideEffect(SideEffect.NavigateToDetails(note))
    }

    fun loadNotes() = intent {
        reduce {
            state.copy(isLoading = true, notes = emptyList())
        }

        delay(750) // Give it a chance to show loading indicator

        val notes = loadNoteListUseCase.loadAll().ifEmpty { emptyList() }

        reduce {
            state.copy(isLoading = false, notes = notes)
        }
    }
}

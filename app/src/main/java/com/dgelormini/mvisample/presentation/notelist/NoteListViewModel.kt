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
import kotlin.random.Random

@OptIn(ExperimentalCoroutinesApi::class)
class NoteListViewModel(
    private val loadNoteListUseCase: GetNoteListUseCase
) : ContainerHost<State, SideEffect>, ViewModel() {

    override val container: Container<State, SideEffect> = container<State, SideEffect>(State())

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


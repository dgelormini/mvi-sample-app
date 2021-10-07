package com.dgelormini.mvisample.presentation.notelist

import com.airbnb.mvrx.MavericksState
import com.dgelormini.mvisample.domain.Note

data class State(
    val notes: List<Note> = listOf(),
    val isIdle: Boolean = false,
    val isLoading: Boolean = false,
    val isError: Boolean = false
) : MavericksState

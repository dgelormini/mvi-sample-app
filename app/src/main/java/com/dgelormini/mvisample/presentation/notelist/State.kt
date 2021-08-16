package com.dgelormini.mvisample.presentation.notelist

import com.dgelormini.mvisample.domain.Note

// TODO: Pros + Cons of data class vs sealed classes/objects for state?
data class State(
    val notes: List<Note> = listOf(),
    val isIdle: Boolean = false,
    val isLoading: Boolean = false,
    val isError: Boolean = false
)

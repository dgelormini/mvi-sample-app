package com.dgelormini.mvisample.presentation.notedetail

import com.dgelormini.mvisample.domain.Note

data class State(
    val note: Note? = null,
    val isIdle: Boolean = false,
    val isLoading: Boolean = false,
    val isLoadError: Boolean = false,
    val isNoteDeleted: Boolean = false,
    val isDeleteError: Boolean = false
)

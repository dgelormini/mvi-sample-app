package com.dgelormini.mvisample.presentation.notelist

import com.dgelormini.mvisample.domain.Note

sealed class SideEffect {
    data class NavigateToDetails(val note : Note) : SideEffect()
}

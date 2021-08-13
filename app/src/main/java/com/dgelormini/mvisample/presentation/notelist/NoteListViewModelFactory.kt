package com.dgelormini.mvisample.presentation.notelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dgelormini.mvisample.domain.GetNoteListUseCase

class NoteListViewModelFactory(
    private val noteListUseCase: GetNoteListUseCase
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NoteListViewModel(noteListUseCase) as T
    }
}
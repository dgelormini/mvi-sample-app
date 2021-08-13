package com.dgelormini.mvisample.presentation.notedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dgelormini.mvisample.domain.DeleteNoteUseCase
import com.dgelormini.mvisample.domain.GetNoteDetailUseCase

class NoteDetailViewModelFactory(
    private val noteDetailUseCase: GetNoteDetailUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NoteDetailViewModel(noteDetailUseCase, deleteNoteUseCase) as T
    }
}
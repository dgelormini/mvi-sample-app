package com.dgelormini.mvisample.domain

import com.dgelormini.mvisample.data.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetNoteListUseCase {

    suspend fun loadAll(): List<Note> = withContext(Dispatchers.IO){
        NoteRepository.loadAll()
    }
}

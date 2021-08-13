package com.dgelormini.mvisample.domain

import com.dgelormini.mvisample.data.NoteRepository

class GetNoteDetailUseCase {
    suspend fun findById(id: Long): Note {
        // TODO: Is throwing the right thing to do below?
        return NoteRepository.findById(id) ?: throw IllegalArgumentException("Invalid note id passed in")
    }
}

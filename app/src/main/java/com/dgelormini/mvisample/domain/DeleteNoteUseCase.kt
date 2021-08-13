package com.dgelormini.mvisample.domain

import com.dgelormini.mvisample.data.NoteRepository

class DeleteNoteUseCase {
    suspend fun delete(note: Note) {
        val deleteSuccess = NoteRepository.delete(note)
        if (!deleteSuccess) {
            throw RuntimeException("Unable to delete note $note")
        }
    }
}
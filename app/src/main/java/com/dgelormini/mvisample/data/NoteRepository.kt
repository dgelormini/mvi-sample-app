package com.dgelormini.mvisample.data

import com.dgelormini.mvisample.domain.Note

/**
 * Normally we would implement a repository interface which is injected in the Domain layer
 */
object NoteRepository {
    private val notes = mutableListOf(
        Note(1, "note1"),
        Note(2, "note2"),
        Note(3, "note3")
    )

    fun loadAll(): List<Note> = notes.toList()

    suspend fun findById(id: Long): Note? = notes.firstOrNull { it.id == id }

    suspend fun delete(note: Note): Boolean = notes.remove(note)
}
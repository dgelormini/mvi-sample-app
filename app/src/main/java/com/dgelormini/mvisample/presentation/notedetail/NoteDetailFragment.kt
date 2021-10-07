package com.dgelormini.mvisample.presentation.notedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.airbnb.mvrx.MavericksView
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.dgelormini.mvisample.R
import com.dgelormini.mvisample.databinding.NoteDetailBinding
import com.dgelormini.mvisample.domain.DeleteNoteUseCase
import com.dgelormini.mvisample.domain.GetNoteDetailUseCase
import com.dgelormini.mvisample.domain.Note
import com.dgelormini.mvisample.presentation.notelist.NoteListViewModel

private const val NOTE_ID = "noteId"

class NoteDetailFragment : MavericksView, Fragment() {

    private val noteId by lazy {
        arguments?.getLong(NOTE_ID)
            ?: throw IllegalArgumentException("noteId is required")
    }

    companion object {
        fun newInstance(id: Long): NoteDetailFragment {
            val bundle = Bundle().apply {
                putLong(NOTE_ID, id)
            }
            return NoteDetailFragment().apply {
                arguments = bundle
            }
        }
    }

    private val viewModel: NoteDetailViewModel by fragmentViewModel()
    private var _binding: NoteDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = NoteDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadNoteDetail(noteId)

        // Normally ViewModelFactory should be injected here along with its UseCases injected into it
//        viewModel = ViewModelProvider(
//            this,
//            NoteDetailViewModelFactory(GetNoteDetailUseCase(), DeleteNoteUseCase())
//        ).get(NoteDetailViewModel::class.java)
/*
        viewModel.observableState.observe(this, Observer { state ->
            state?.let { renderState(state) }
        })

        if (savedInstanceState == null) {
            viewModel.dispatch(Action.LoadNoteDetail(noteId))
        }
*/
        binding.deleteNoteButton.setOnClickListener {
            viewModel.deleteNote(noteId)
        }
    }

    /*private fun renderState(state: State) {
        with(state) {
            when {
                isLoadError -> renderLoadNoteDetailError()
                isDeleteError -> renderNoteDeleteError()
                note != null -> renderNoteDetailState(note)
                isNoteDeleted -> renderNoteDeleted()
            }
        }
    }
*/
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun renderNoteDetailState(note: Note) {
        binding.noteIdView.visibility = View.VISIBLE
        binding.noteTextView.visibility = View.VISIBLE
        binding.noteIdView.text = String.format(getString(R.string.note_detail_id), note.id)
        binding.noteTextView.text = String.format(getString(R.string.note_detail_text), note.text)
    }

    private fun renderLoadNoteDetailError() {
        Toast.makeText(requireContext(), R.string.error_loading_note, Toast.LENGTH_LONG).show()
        binding.noteIdView.visibility = View.GONE
        binding.noteTextView.visibility = View.GONE
    }

    private fun renderNoteDeleteError() {
        Toast.makeText(requireContext(), R.string.error_deleting_note, Toast.LENGTH_LONG).show()
    }

    private fun renderNoteDeleted() {
        requireActivity().supportFragmentManager.popBackStack()
    }

    override fun invalidate() = withState(viewModel) {
        with(it) {
            when {
                isLoadError -> renderLoadNoteDetailError()
                isDeleteError -> renderNoteDeleteError()
                note != null -> renderNoteDetailState(note)
                isNoteDeleted -> renderNoteDeleted()
            }
        }
    }
}

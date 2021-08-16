package com.dgelormini.mvisample.presentation.notelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dgelormini.mvisample.R
import com.dgelormini.mvisample.domain.GetNoteListUseCase
import com.dgelormini.mvisample.domain.Note
import com.dgelormini.mvisample.presentation.notedetail.NoteDetailFragment
import org.orbitmvi.orbit.viewmodel.observe

class NoteListFragment : Fragment() {

    private val clickListener: ClickListener = this::onNoteClicked

    private val recyclerViewAdapter = NoteAdapter(clickListener)

    companion object {
        fun newInstance() = NoteListFragment()
    }

    private lateinit var viewModel: NoteListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.note_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        // Normally ViewModelFactory should be injected here along with its UseCases injected into it
        viewModel = ViewModelProvider(this, NoteListViewModelFactory(GetNoteListUseCase()))
            .get(NoteListViewModel::class.java)

        viewModel.observe(state = ::renderState, sideEffect = ::handleSideEffect, lifecycleOwner = this)

        viewModel.loadNotes()
        /*
        viewModel.observableState.observe(this, Observer { state ->
            state?.let { renderState(state) }
        })

        viewModel.dispatch(Action.LoadNotes)*/
    }

    private fun handleSideEffect(sideEffect: SideEffect) {
        when (sideEffect) {
            is SideEffect.NavigateToDetails -> handleNoteClicked(sideEffect.note)
        }
    }


    private fun setupRecyclerView() {
        activity?.findViewById<RecyclerView>(R.id.notesRecyclerView)?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = recyclerViewAdapter
            setHasFixedSize(true)
        }
    }

    private fun onNoteClicked(note: Note) {
        viewModel.selectNote(note)
    }

    private fun handleNoteClicked(note: Note) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container, NoteDetailFragment.newInstance(note.id))
            .addToBackStack(null)
            .commit()
    }

    private fun renderState(state: State) {
        with(state) {
            when {
                isLoading -> renderLoadingState()
                isError -> renderErrorState()
                else -> renderNotesState(notes)
            }
        }
    }

    private fun renderLoadingState() {
        requireActivity().findViewById<View>(R.id.loadingIndicator).visibility = View.VISIBLE
    }

    private fun renderErrorState() {
        requireActivity().findViewById<View>(R.id.loadingIndicator).visibility = View.GONE
        Toast.makeText(requireContext(), R.string.error_loading_notes, Toast.LENGTH_LONG).show()
    }

    private fun renderNotesState(notes: List<Note>) {
        requireActivity().findViewById<View>(R.id.loadingIndicator).visibility = View.GONE
        recyclerViewAdapter.updateNotes(notes)
        requireActivity().findViewById<View>(R.id.notesRecyclerView).visibility = View.VISIBLE
    }
}

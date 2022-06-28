package com.example.mynotesapp.ui.home

import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotesapp.MainActivity
import com.example.mynotesapp.R
import com.example.mynotesapp.data.local.ClientPreferences
import com.example.mynotesapp.data.remote.response.noteResponse.Note
import com.example.mynotesapp.databinding.FragmentHomeBinding
import com.example.mynotesapp.ui.home.adapter.NoteListAdapter
import com.example.mynotesapp.utils.base.BaseFragment
import com.example.mynotesapp.utils.extension.OnItemClickListener
import com.example.mynotesapp.utils.extension.snack
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(
    FragmentHomeBinding::inflate
) {
    override val viewModel by viewModels<HomeViewModel>()
    private val productList: MutableList<Note> = mutableListOf()
    private val temporaryList: MutableList<Note> = mutableListOf()

    override fun onCreateFinished() {
        viewModel.fetchAllNotes(ClientPreferences(requireContext()).getUsername().toString())
        setRecyclerViewAdapter()
    }

    override fun initListeners() {
        binding.fabAddNote.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addNoteFragment)
        }

        binding.swipeToRefresh.setOnRefreshListener {
            refresh()
        }

        binding.fabSettings.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_settingsFragment)
        }
    }

    override fun observeEvents() {
        with(viewModel) {
            noteListResponse.observe(viewLifecycleOwner, Observer {
                it?.let {
                    productList.clear()
                    it.notes?.forEach { note ->
                        productList.add(note)
                    }
                    checkListSize(productList.size)
                    temporaryList.clear()
                    temporaryList.addAll(productList)
                    (binding.rvNotes.adapter as NoteListAdapter).updateList(temporaryList)
                }
            })
            isLoading.observe(viewLifecycleOwner, Observer {
                handleViewActions(it)
            })
            onError.observe(viewLifecycleOwner, Observer {
                snack(requireView(), it.toString())
            })

            deleteNoteResponse.observe(viewLifecycleOwner, Observer {
                //snack(requireView(),it.message.toString())
            })
        }
    }

    private fun setRecyclerViewAdapter() {
        val mLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvNotes.layoutManager = mLayoutManager

        val mAdapter = NoteListAdapter(object : OnItemClickListener {
            override fun onClick(note: Note) {

            }
        })
        binding.rvNotes.adapter = mAdapter
        swipeToDelete(binding.rvNotes, mAdapter)
    }

    private fun handleViewActions(isLoading: Boolean = false) {
        binding.rvNotes.isVisible = !isLoading
        binding.progressBar.isVisible = isLoading

        binding.ivNoDataFound.isVisible = isLoading
        binding.tvNoDataFound.isVisible = isLoading
    }

    private fun swipeToDelete(recyclerView: RecyclerView, adapter: NoteListAdapter) {
        val swipeToDeleteCallback = object : SwipeToDelete() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deletedItem = adapter.noteList[viewHolder.adapterPosition]

                // Delete Item
                viewModel.deleteNote(deletedItem.nodeId.toString())
                viewModel.fetchAllNotes(
                    ClientPreferences(requireContext()).getUsername().toString()
                )

                adapter.notifyItemRemoved(viewHolder.adapterPosition)
                // Restore Deleted Item
                restoreDeletedData(viewHolder.itemView, deletedItem)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun restoreDeletedData(view: View, deletedItem: Note) {
        snack(requireView(), "Deleted '${deletedItem.title}'")
    }

    private fun refresh() {
        viewModel.fetchAllNotes(ClientPreferences(requireContext()).getUsername().toString())
        binding.swipeToRefresh.isRefreshing = false
    }

    private fun checkListSize(size: Int) {
        if (size > 0) {
            binding.ivNoDataFound.visibility = View.INVISIBLE
            binding.tvNoDataFound.visibility = View.INVISIBLE
        } else {
            binding.ivNoDataFound.visibility = View.VISIBLE
            binding.tvNoDataFound.visibility = View.VISIBLE
        }
    }
}
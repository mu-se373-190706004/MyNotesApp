package com.example.mynotesapp.ui.home.add

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.mynotesapp.MainActivity
import com.example.mynotesapp.R
import com.example.mynotesapp.data.local.ClientPreferences
import com.example.mynotesapp.data.local.model.Priority
import com.example.mynotesapp.databinding.FragmentAddNoteBinding
import com.example.mynotesapp.utils.base.BaseFragment
import com.example.mynotesapp.utils.extension.snack
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddNoteFragment : BaseFragment<FragmentAddNoteBinding,AddNoteViewModel>(
    FragmentAddNoteBinding::inflate
) {
    override val viewModel by viewModels<AddNoteViewModel>()
    private var noteId : String = ""

    override fun onCreateFinished() {
        val username = ClientPreferences(requireContext()).getUsername().toString()
        viewModel.createNoteId(username,"Initial comment")
    }

    override fun initListeners() {
        binding.btnAdd.setOnClickListener {
            addNewNote()
        }
        binding.toolbar.ivBack.setOnClickListener {
           requireActivity().onBackPressed()
        }
    }

    override fun observeEvents() {
        with(viewModel){
            addNoteIdResponse.observe(viewLifecycleOwner, Observer {
                it?.let {
                    noteId = it.data?.get(0)?.noteId.toString()
                }
            })
            onError.observe(viewLifecycleOwner, Observer {
                snack(requireView(),it.toString())
            })

            addNewNoteResponse.observe(viewLifecycleOwner, Observer {
                it?.let {
                    if(it.status.equals("true")){
                        snack(requireView(),"Note successfully created.")
                        findNavController().navigate(R.id.action_addNoteFragment_to_homeFragment)
                    }
                }
            })
        }
    }

    private fun addNewNote(){
        val username = ClientPreferences(requireContext()).getUsername().toString()
        val mTitle = binding.tvTitle.text.toString()
        val mPriority = binding.spinnerPriority.selectedItem.toString().lowercase()
        val mDescription = binding.tvDescription.text.toString()

        if(verifyDataFromUser(mTitle,mDescription)){
            viewModel.createNewNote(username,noteId,mTitle,mDescription,mPriority,"false")
            snack(requireView(),mPriority)
        }else
            snack(requireView(),"Please fill out all spaces.")
    }

    private fun verifyDataFromUser(title: String, description: String): Boolean {
        return !(title.isEmpty() || description.isEmpty())
    }

    private fun parsePriority(priority: String): Priority {
        return when(priority){
            "High Priority" -> { Priority.HIGH }
            "Medium Priority" -> { Priority.MEDIUM }
            "Low Priority" -> { Priority.LOW }
            else -> Priority.LOW
        }
    }
}
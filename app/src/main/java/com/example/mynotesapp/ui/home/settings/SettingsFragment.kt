package com.example.mynotesapp.ui.home.settings

import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.mynotesapp.R
import com.example.mynotesapp.data.local.ClientPreferences
import com.example.mynotesapp.databinding.FragmentSettingsBinding
import com.example.mynotesapp.utils.base.BaseFragment
import com.example.mynotesapp.utils.extension.snack
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : BaseFragment<FragmentSettingsBinding,SettingsViewModel>(
    FragmentSettingsBinding::inflate
) {
    override val viewModel by viewModels<SettingsViewModel>()

    override fun onCreateFinished() {
        binding.tvUsername.text = ClientPreferences(requireContext()).getUsername().toString()
    }

    override fun initListeners() {
        binding.toolbar.ivBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.btnDeleteAccount.setOnClickListener {
            deleteAccount()
        }

        binding.btnLogout.setOnClickListener {
            ClientPreferences(requireContext()).clearSharedPref()
            findNavController().navigate(R.id.action_settingsFragment_to_loginFragment)
        }
    }

    override fun observeEvents() {
        with(viewModel){
            deleteAccountResponse.observe(viewLifecycleOwner, Observer {
                ClientPreferences(requireContext()).clearSharedPref()
                findNavController().navigate(R.id.action_settingsFragment_to_loginFragment)
            })
        }
    }

    private fun deleteAccount(){
        viewModel.deleteAccount(ClientPreferences(requireContext()).getUsername().toString())
    }

}
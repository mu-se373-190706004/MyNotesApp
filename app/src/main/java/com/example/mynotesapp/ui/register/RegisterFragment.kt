package com.example.mynotesapp.ui.register


import android.text.Editable
import android.text.TextWatcher
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.mynotesapp.MainActivity
import com.example.mynotesapp.R
import com.example.mynotesapp.data.local.ClientPreferences
import com.example.mynotesapp.databinding.FragmentRegisterBinding
import com.example.mynotesapp.utils.base.BaseFragment
import com.example.mynotesapp.utils.extension.snack
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding, RegisterViewModel>(
    FragmentRegisterBinding::inflate
) {
    override val viewModel by viewModels<RegisterViewModel>()
    private var username: String = ""
    private var password: String = ""

    override fun onCreateFinished() {

    }

    override fun initListeners() {
        binding.etRegisterUsername.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                checkFields()
            }
        })

        binding.etRegisterPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                checkFields()
            }
        })

        binding.btnRegister.setOnClickListener {
            register()
        }

    }

    override fun observeEvents() {
        with(viewModel) {
            registerResponse.observe(viewLifecycleOwner, Observer {
                it?.let {
                    if (it.status.equals("true")) {
                        ClientPreferences(requireContext()).setUsername(username)
                        ClientPreferences(requireContext()).setRememberMe(true)
                        findNavController().navigate(R.id.action_registerFragment_to_homeFragment)
                        snack(requireView(), it.message.toString())
                    }
                }
            })

            isLoading.observe(viewLifecycleOwner, Observer {
                handleViewAction(it)
            })

            onError.observe(viewLifecycleOwner, Observer {
                snack(requireView(), it.toString())
            })
        }
    }

    private fun handleViewAction(isLoading: Boolean = false) {
        binding.progressBar.isVisible = isLoading
    }

    private fun checkFields() {
        if (!binding.etRegisterUsername.text.isNullOrEmpty() && !binding.etRegisterPassword.text.isNullOrEmpty()) {
            binding.btnRegister.isEnabled = true
            binding.btnRegister.alpha = 1F
        } else {
            binding.btnRegister.isEnabled = false
            binding.btnRegister.alpha = 0.2F
        }
    }

    private fun register() {
        username = binding.etRegisterUsername.text.toString()
        password = binding.etRegisterPassword.text.toString()

        viewModel.register(username, password)
    }
}
package com.example.mynotesapp.ui.login

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
import com.example.mynotesapp.databinding.FragmentLoginBinding
import com.example.mynotesapp.utils.base.BaseFragment
import com.example.mynotesapp.utils.extension.snack
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding,LoginViewModel>(
    FragmentLoginBinding::inflate
) {
    override val viewModel by viewModels<LoginViewModel>()
    private var username: String = ""
    private var password: String = ""

    override fun onCreateFinished() {

        isUserLoggedIn()
    }

    override fun initListeners() {
        binding.etLoginUsername.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                checkFields()
            }
        })

        binding.etLoginPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                checkFields()
            }
        })

        binding.btnLogin.setOnClickListener {
            login()
        }

        binding.btnLoginToRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    override fun observeEvents() {
        with(viewModel){
            loginResponse.observe(viewLifecycleOwner, Observer {
                it?.let {
                    if(it.status.equals("true")){
                        ClientPreferences(requireContext()).setUsername(username)
                        ClientPreferences(requireContext()).setRememberMe(true)
                        snack(requireView(),it.message.toString())
                        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                    }else{
                        snack(requireView(),it.message.toString())
                    }
                }
            })

            isLoading.observe(viewLifecycleOwner, Observer {
                handleViewAction(it)
            })

            onError.observe(viewLifecycleOwner, Observer {
                snack(requireView(),it.toString())
            })
        }
    }

    private fun isUserLoggedIn() {
        if (ClientPreferences(requireContext()).isRememberMe()) {
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }
    }   

    private fun handleViewAction(isLoading: Boolean = false) {
        binding.progressBar.isVisible = isLoading
    }

    private fun checkFields() {
        if (!binding.etLoginUsername.text.isNullOrEmpty() && !binding.etLoginPassword.text.isNullOrEmpty()) {
            binding.btnLogin.isEnabled = true
            binding.btnLogin.alpha = 1F
        } else {
            binding.btnLogin.isEnabled = false
            binding.btnLogin.alpha = 0.2F
        }
    }

    private fun login() {
        username = binding.etLoginUsername.text.toString()
        password = binding.etLoginPassword.text.toString()

        viewModel.login(username, password)
    }

}
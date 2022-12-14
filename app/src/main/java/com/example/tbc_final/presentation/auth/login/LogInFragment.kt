package com.example.tbc_final.presentation.auth.login

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.tbc_final.R
import com.example.tbc_final.utils.common.UiState
import com.example.tbc_final.databinding.FragmentLogInBinding
import com.example.tbc_final.presentation.auth.signup.RegisterFragmentDirections
import com.example.tbc_final.presentation.base.BaseFragment
import com.example.tbc_final.utils.extensions.isValidEmail
import com.example.tbc_final.utils.extensions.toast
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LogInFragment : BaseFragment<FragmentLogInBinding>(FragmentLogInBinding::inflate) {

    private val logInViewModel by viewModels<LogInViewModel>()

    override fun onBind() {
        listener()
        observer()



    }


    private fun logIn(email: String, password: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            logInViewModel.logIn(
                email = email,
                password = password
            )

        }
    }

    private fun observer() {
        viewLifecycleOwner.lifecycleScope.launch {
            logInViewModel.logInFlow.collect {
                it?.let {
                    findNavController().navigate(LogInFragmentDirections.actionLogInFragmentToHomeFragment2())
                } ?: binding?.emailInputLayout?.apply {
                    error = context.getString(R.string.invalid)
                }
            }
        }
    }


    private fun validation() {
        binding?.apply {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (email.isEmpty() || !email.isValidEmail()) {
                emailInputLayout.error = getString(R.string.valid_mail)
            } else {
                emailInputLayout.error = null
            }
            if (password.isEmpty()) {
                passwordInputLayout.error = getString(R.string.valid_password)
            } else {
                passwordInputLayout.error = null

            }
            if (email.isValidEmail() || password.isNotEmpty()) {
                logIn(email,password)
            }

        }
    }


    private fun listener() {

        binding?.apply {
            logInBtn.setOnClickListener {
//                findNavController().navigate(LogInFragmentDirections.actionLogInFragmentToHomeFragment2())

               validation()

            }
            logInTVSignUp.setOnClickListener {
                findNavController().navigate(LogInFragmentDirections.actionLogInFragmentToRegisterFragment())
            }
        }

    }
}

package com.tongue.dandelioncourier.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.tongue.dandelioncourier.R
import com.tongue.dandelioncourier.data.domain.Authentication
import com.tongue.dandelioncourier.databinding.FragmentLoginBinding
import com.tongue.dandelioncourier.ui.states.LoginUiState
import com.tongue.dandelioncourier.ui.states.PresentationUiState
import com.tongue.dandelioncourier.ui.viewmodels.ActivityViewModel
import com.tongue.dandelioncourier.ui.viewmodels.LoginViewModel
import com.tongue.dandelioncourier.ui.viewmodels.PresentationViewModel
import com.tongue.dandelioncourier.utils.ValidationRoutines
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LoginFragment: Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val activityViewModel: ActivityViewModel by activityViewModels()
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLoginBinding.inflate(inflater,container,false)
        setUpClickListener()

        launchOnCreate()

        return binding.root

    }

    override fun onStart() {
        super.onStart()
        setUpDefaultValues()
    }

    private fun launchOnCreate(){
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.uiState.collect {
                    when(it){
                        is LoginUiState.LoginSuccessful -> {
                            activityViewModel.authentication = Authentication(it.jwt,"Alexander","Alexander")
                            findNavController().navigate(R.id.homeFragment)
                        }
                        is LoginUiState.WrongCredentials -> {
                            Toast.makeText(requireContext(),it.message,Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun setUpClickListener(){
        binding.loginButton.setOnClickListener {
            val isInputValid = (binding.textFieldUsername.validate() && binding.textFieldPassword.validate())
            if (!isInputValid)
                Toast.makeText(requireContext(),R.string.toast_login_bad_input,Toast.LENGTH_SHORT).show()
            else
                viewModel.login(binding.textFieldUsername.getText(),binding.textFieldPassword.getText())
        }
    }

    private fun setUpDefaultValues(){
        binding.textFieldUsername.setUpResources(R.drawable.ic_baseline_person_24,R.string.login_username_hint)
        binding.textFieldPassword.setUpResources(R.drawable.ic_baseline_lock_24,R.string.login_password_hint)
        binding.textFieldUsername.setValidationRoutine(ValidationRoutines.validateNotEmptyString)
        binding.textFieldPassword.setValidationRoutine(ValidationRoutines.passwordValidator)

    }

}
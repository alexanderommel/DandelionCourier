package com.tongue.dandelioncourier.ui.fragments

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.tongue.dandelioncourier.R
import com.tongue.dandelioncourier.data.model.RegistrationForm
import com.tongue.dandelioncourier.databinding.FragmentRegisterBinding
import com.tongue.dandelioncourier.ui.states.RegistrationUiState
import com.tongue.dandelioncourier.ui.viewmodels.RegisterViewModel
import kotlinx.coroutines.launch

class RegisterFragment: Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentRegisterBinding.inflate(inflater,container,false)

        setUpClickListeners()
        launchOnCreate()

        return binding.root

    }

    override fun onStart() {
        super.onStart()
        setViewInitialData()
    }

    private fun launchOnCreate() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.uiState.collect {
                    when (it) {
                        is RegistrationUiState.SuccessfulRegistration -> {
                            Toast.makeText(requireContext(),getString(R.string.register_successful),Toast.LENGTH_SHORT).show()
                            findNavController().popBackStack()
                        }
                        is RegistrationUiState.ErrorFound -> {
                            Toast.makeText(requireContext(),getString(R.string.register_error),Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun setUpClickListeners(){
        binding.buttonRegister.setOnClickListener {
            val registrationForm = getRegistrationForm()
            viewModel.register(registrationForm)
        }
    }

    private fun getRegistrationForm(): RegistrationForm{
        val email = binding.textFieldUsername.getText()
        val firstname = binding.textFieldFirstname.getText()
        val lastname = binding.textFieldLastname.getText()
        val car = binding.textFieldCar.getText()
        val brand = binding.textFieldBrand.getText()
        val password = binding.textFieldPassword.getText()
        return RegistrationForm(email,firstname,lastname,car,brand,password)
    }

    private fun setViewInitialData(){
        binding.textFieldFirstname.setUpResources(R.drawable.ic_baseline_person_24,R.string.register_firstname)
        binding.textFieldLastname.setUpResources(R.drawable.ic_baseline_person_24,R.string.register_lastname)
        binding.textFieldBrand.setUpResources(R.drawable.ic_baseline_credit_card_24,R.string.register_brand)
        binding.textFieldCar.setUpResources(R.drawable.ic_baseline_directions_car_24,R.string.register_car)
        binding.textFieldUsername.setUpResources(R.drawable.ic_baseline_person_24,R.string.register_username)
        binding.textFieldPassword.setUpResources(R.drawable.ic_baseline_person_24,R.string.register_password,InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD)
        binding.textFieldPassword.setUpAsPasswordInput()
    }

}
package com.tongue.dandelioncourier.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.tongue.dandelioncourier.R
import com.tongue.dandelioncourier.databinding.FragmentPresentationBinding
import com.tongue.dandelioncourier.ui.states.PresentationUiState
import com.tongue.dandelioncourier.ui.viewmodels.ActivityViewModel
import com.tongue.dandelioncourier.ui.viewmodels.PresentationViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect

class PresentationFragment: Fragment() {

    companion object{
        private const val TAG = "PresentationFragment"
    }

    private lateinit var binding: FragmentPresentationBinding
    private lateinit var navController: NavController
    private val activityViewModel: ActivityViewModel by activityViewModels()
    private val viewModel: PresentationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPresentationBinding.inflate(inflater, container, false)
        navController = findNavController()

        launchOnCreate()

        return binding.root

    }

    private fun launchOnCreate(){
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.uiState.collect {
                    when(it){
                        is PresentationUiState.AuthenticationPreferencesFound -> {
                            activityViewModel.authentication = it.authentication
                            var currentPosition = activityViewModel.position
                            //navController.navigate(R.id.storesFragment)
                        }
                        is PresentationUiState.AuthenticationPreferencesNotFound -> {
                            //activityViewModel.connectStomp("")
                            navController.navigate(R.id.loginFragment)
                        }
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        startAppLogoAnimation()
    }

    private fun startAppLogoAnimation(){
        var animation = AlphaAnimation(0.0f,1.0f)
        animation.duration = 1500
        animation.setAnimationListener(object: Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {
            }

            override fun onAnimationEnd(p0: Animation?) {
                viewModel.getAuthenticationPreferences()
                /** Also show a progress bar **/

            }
            override fun onAnimationRepeat(p0: Animation?) {
            }
        })
        binding.imageViewLogo.animation = animation
    }

}
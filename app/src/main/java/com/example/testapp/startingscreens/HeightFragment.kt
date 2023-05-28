package com.example.testapp.startingscreens

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.testapp.MainActivity
import com.example.testapp.R
import com.example.testapp.databinding.FragmentHeightBinding
import com.example.testapp.databinding.FragmentLoginBinding
import com.example.testapp.model.signin.SignInRequest
import com.example.testapp.model.userdetails.UserDetailsRequest
import com.example.testapp.util.TokenManager
import com.example.testapp.viewmodel.LandingScreenViewModel
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class HeightFragment : Fragment() {

    private var _binding: FragmentHeightBinding? = null
    private val binding get() = _binding!!
    private val myViewModel: LandingScreenViewModel by viewModels()

    @Inject
    lateinit var tokenManager: TokenManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHeightBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.nextButton.setOnClickListener {
            showProgressBar()
            val validationResult = validateUserInput()
            if (validationResult.first) {
                getUserRequestLogin()
                val userRequestLogin = getUserRequestLogin()
                myViewModel.userDetails(userRequestLogin)
            } else {
                hideProgressBar()
                Toast.makeText(
                    requireActivity(),
                    "Message : ${validationResult.second}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        observeUIState()
    }

    private fun validateUserInput(): Pair<Boolean, String> {
        val height = binding.heightPicker.value.toString()
        val weight = binding.weightPicker.value.toString()
        val dailyGoal = binding.steps.text.toString().trim()
        return myViewModel.validateUserDetails(
            dailyGoal
        )
    }

    private fun getUserRequestLogin(): UserDetailsRequest {
        return binding.run {
            UserDetailsRequest(
                binding.steps.text.toString().trim(),
                binding.heightPicker.value.toString(),
                binding.weightPicker.value.toString()
            )
        }
    }

    private fun observeUIState() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                myViewModel.userDetailsUiState.collectLatest { userDetailsUIState ->
                    userDetailsUIState.message?.let {
                        Toast.makeText(requireContext(),it, Toast.LENGTH_LONG).show()
                        myViewModel.messageAlreadyDisplayed()
                    }
                    if(userDetailsUIState.details!=null){
                        // Save Token Here
                        tokenManager.saveUserDetails(userDetailsUIState.details)
                        val intent = Intent(requireActivity(), MainActivity::class.java)
                        startActivity(intent)
                        requireActivity().finish()
                    }
                }
            }
        }
    }

    private fun hideProgressBar() {
        binding.circularPBar.visibility = View.GONE
        binding.nextButton.visibility = View.VISIBLE
    }

    private fun showProgressBar() {
        binding.circularPBar.visibility = View.VISIBLE
        binding.nextButton.visibility = View.GONE
    }
}
package com.example.testapp.loginsignup

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.testapp.MainActivity
import com.example.testapp.R
import com.example.testapp.databinding.FragmentLoginBinding
import com.example.testapp.databinding.FragmentSignupBinding
import com.example.testapp.model.signin.SignInRequest
import com.example.testapp.model.signup.SignUpRequest
import com.example.testapp.util.TokenManager
import com.example.testapp.viewmodel.LandingScreenViewModel
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class SignupFragment : Fragment() {

    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!
    private val myViewModel: LandingScreenViewModel by viewModels()

    @Inject
    lateinit var tokenManager: TokenManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<ImageView>(R.id.back).setOnClickListener {
            Navigation.findNavController(requireActivity(), R.id.nav_host)
                .navigate(R.id.action_signupFragment_to_loginFragment)
        }

        view.findViewById<TextView>(R.id.logInText).setOnClickListener {
            findNavController().popBackStack()
        }

        view.findViewById<MaterialButton>(R.id.proceedButton).setOnClickListener {
            showProgressBar()
            val validationResult = validateUserInput()
            if (validationResult.first) {
                val userRequestSignUp = getUserRequestSignUp()
                myViewModel.signUp(userRequestSignUp)
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
        val name = binding.name.text.toString().trim()
        val emailAddress = binding.emailInput.text.toString().trim()
        val password = binding.paswordInput.text.toString().trim()
        val confirmPassword = binding.confirmPaswordInput.text.toString().trim()
        return myViewModel.validateCredentialsSignup(
            name,
            emailAddress,
            password,
            confirmPassword
        )
    }

    private fun getUserRequestSignUp(): SignUpRequest {
        return binding.run {
            SignUpRequest(
                emailInput.text.toString().trim(),
                paswordInput.text.toString().trim(),
                name.text.toString().trim()
            )
        }
    }

    private fun observeUIState() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                myViewModel.signUpUiState.collectLatest { signUpUIState ->
                    signUpUIState.message?.let {
                        Toast.makeText(requireContext(),it, Toast.LENGTH_LONG).show()
                        myViewModel.messageAlreadyDisplayed()
                    }
                    if(signUpUIState.token!=null){
                        // Save Token Here
                        tokenManager.saveToken(signUpUIState.token)
                        Navigation.findNavController(requireActivity(), R.id.nav_host)
                            .navigate(R.id.action_signupFragment_to_heightFragment2)
                    }
                }
            }
        }
    }

    private fun hideProgressBar() {
        binding.circularPBar.visibility = View.GONE
        binding.proceedButton.visibility = View.VISIBLE
    }

    private fun showProgressBar() {
        binding.circularPBar.visibility = View.VISIBLE
        binding.proceedButton.visibility = View.GONE
    }

}
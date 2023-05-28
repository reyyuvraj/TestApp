package com.example.testapp.loginsignup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.testapp.MainActivity
import com.example.testapp.R
import com.example.testapp.databinding.FragmentLoginBinding
import com.example.testapp.model.signin.SignInRequest
import com.example.testapp.util.Constants.TAG
import com.example.testapp.util.TokenManager
import com.example.testapp.viewmodel.LandingScreenViewModel
import com.google.android.material.button.MaterialButton
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val myViewModel: LandingScreenViewModel by viewModels()

    @Inject
    lateinit var tokenManager: TokenManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<MaterialButton>(R.id.logInButton).setOnClickListener {
            showProgressBar()
            val validationResult = validateUserInput()
            if (validationResult.first) {
                getUserRequestLogin()
                val userRequestLogin = getUserRequestLogin()
                myViewModel.signIn(userRequestLogin)
            } else {
                hideProgressBar()
                Toast.makeText(
                    requireActivity(),
                    "Message : ${validationResult.second}",
                    Toast.LENGTH_LONG
                ).show()
            }

        }
        view.findViewById<TextView>(R.id.SignUpText).setOnClickListener {
            Navigation.findNavController(requireActivity(), R.id.nav_host)
                .navigate(R.id.action_loginFragment_to_signupFragment)
        }
        observeUIState()
    }

    private fun validateUserInput(): Pair<Boolean, String> {
        val email = binding.emailInput.text.toString()
        val password = binding.paswordInput.text.toString()
        return myViewModel.validateCredentialsLogin(
            email,
            password
        )
    }

    private fun getUserRequestLogin(): SignInRequest {
        return binding.run {
            SignInRequest(
                emailInput.text.toString().trim(),
                paswordInput.text.toString().trim(),
            )
        }
    }

    private fun observeUIState() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                myViewModel.signInUiState.collectLatest { signInUIState ->
                    signInUIState.message?.let {
                        Toast.makeText(requireContext(),it, Toast.LENGTH_LONG).show()
                        myViewModel.messageAlreadyDisplayed()
                    }
                    if(signInUIState.token!=null){
                        // Save Token Here
                        tokenManager.saveToken(signInUIState.token)
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
        binding.logInButton.visibility = View.VISIBLE
    }

    private fun showProgressBar() {
        binding.circularPBar.visibility = View.VISIBLE
        binding.logInButton.visibility = View.GONE
    }
}
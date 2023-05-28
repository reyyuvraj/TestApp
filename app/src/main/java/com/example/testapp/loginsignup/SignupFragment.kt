package com.example.testapp.loginsignup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.testapp.R
import com.google.android.material.button.MaterialButton

class SignupFragment : Fragment() {

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
        return inflater.inflate(R.layout.fragment_signup, container, false)
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
            Navigation.findNavController(requireActivity(), R.id.nav_host)
                .navigate(R.id.action_signupFragment_to_heightFragment2)
        }
    }

}
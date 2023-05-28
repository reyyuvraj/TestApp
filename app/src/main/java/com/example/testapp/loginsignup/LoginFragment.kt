package com.example.testapp.loginsignup

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import com.example.testapp.MainActivity
import com.example.testapp.R
import com.google.android.material.button.MaterialButton

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<MaterialButton>(R.id.logInButton).setOnClickListener {
            val intent = Intent(requireActivity(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
        view.findViewById<TextView>(R.id.SignUpText).setOnClickListener {
            Navigation.findNavController(requireActivity(), R.id.nav_host)
                .navigate(R.id.action_loginFragment_to_signupFragment)
        }
    }
}
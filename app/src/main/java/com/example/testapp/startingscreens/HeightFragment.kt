package com.example.testapp.startingscreens

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.testapp.MainActivity
import com.example.testapp.R
import com.google.android.material.button.MaterialButton

class HeightFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_height, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<MaterialButton>(R.id.nextButton).setOnClickListener {
            val intent = Intent(requireActivity(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }
}
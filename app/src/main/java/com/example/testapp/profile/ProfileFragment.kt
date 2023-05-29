package com.example.testapp.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.Modifier
import androidx.fragment.app.Fragment
import com.example.testapp.HomeActivity
import com.example.testapp.MainActivity
import com.example.testapp.R
import com.example.testapp.databinding.FragmentLoginBinding
import com.example.testapp.databinding.FragmentProfileBinding
import com.example.testapp.model.userdetails.Details
import com.example.testapp.util.TokenManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var tokenManager: TokenManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        val userDetails = tokenManager.getUserDetails<Details>()
        if (userDetails!=null) {
            binding.stepsInput.setText(userDetails.daily_step_goal)
             binding.heightInput.setText("${userDetails.height}cm")
             binding.weightInput.setText("${userDetails.weight}kg")
            val height = userDetails.height.toInt()
            val weight = userDetails.weight.toInt()
            val heightInMetre = height/100.0
            Log.d("btao","$height $heightInMetre")
            val bmi = ((weight)/(heightInMetre*heightInMetre))
            binding.bmi.text = bmi.toString()
        }
        binding.logoutButton.setOnClickListener {
            tokenManager.removeData()
            val intent = Intent(requireActivity(), HomeActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
        return binding.root
    }
    
}
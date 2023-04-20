package com.example.testapp.workout

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.testapp.R

class WorkoutFragment : Fragment() {

    private lateinit var composeView: ComposeView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).also {
            composeView = it
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        composeView.setContent {
            WorkoutScreen(onViewMoreNavigate = {
                Toast.makeText(requireContext(),it,Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_workoutFragment_to_exercisesFragment)
            })
        }
    }
}
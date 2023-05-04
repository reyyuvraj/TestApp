package com.example.testapp.exercise

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.testapp.R
import com.example.testapp.viewmodel.StartExerciseViewModel
import com.example.testapp.workout.WorkoutScreen

class ExercisesFragment : Fragment() {

    private lateinit var composeView: ComposeView
    private val viewModel: StartExerciseViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).also {
            composeView = it
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        composeView.setContent {
            ExercisesScreen(
                onBackArrowPressed = {
                    requireActivity().onBackPressed()
                }, onStartPressed = {
                    viewModel.resetCounter()
                    findNavController().navigate(R.id.action_exercisesFragment_to_startExerciseFragment)
                })
        }
    }
}
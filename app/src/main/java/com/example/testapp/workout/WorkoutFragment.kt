package com.example.testapp.workout

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.TextView
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.testapp.R
import timber.log.Timber

class WorkoutFragment : Fragment() {

    private lateinit var composeView: ComposeView
    private var previousTotalSteps = 0f
    private var previousTotalCalorie = 0f
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

        setUpView()
        loadData()

        composeView.setContent {
            WorkoutScreen(
                onViewMoreNavigate = {
//                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_workoutFragment_to_exercisesFragment)
                },
                calorie = previousTotalCalorie.toInt().toString(),
                steps = previousTotalSteps.toInt().toString()
            )
        }
    }

    private fun loadData() {
        val sharedPreferences = activity?.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val savedNumber = sharedPreferences?.getFloat("key1", 0f)
        Timber.tag("MainActivity").d("data = $savedNumber")
        previousTotalSteps = savedNumber ?: 0f
        previousTotalCalorie = ((1000/35)*previousTotalSteps)/1000
    }

    private fun setUpView() {
        view?.findViewById<TextView>(R.id.log_tv)?.apply {
            visibility = View.GONE
        }
    }
}
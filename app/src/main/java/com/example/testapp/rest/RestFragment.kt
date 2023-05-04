package com.example.testapp.rest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.testapp.R
import com.example.testapp.viewmodel.StartExerciseViewModel
import kotlinx.coroutines.launch

class RestFragment : Fragment() {

    private lateinit var composeView: ComposeView
    private val viewModel: StartExerciseViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return ComposeView(requireContext()).also {
            composeView = it
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        composeView.setContent {
            RestScreen(onSkipPressed = {
                viewModel.incrementCount()
                findNavController().navigate(R.id.action_restFragment_to_startExerciseFragment)
            })
        }
    }

}
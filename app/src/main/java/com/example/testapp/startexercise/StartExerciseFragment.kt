package com.example.testapp.startexercise

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.testapp.R
import com.example.testapp.viewmodel.StartExerciseViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.progressindicator.LinearProgressIndicator
import kotlinx.coroutines.launch
import pl.droidsonroids.gif.GifImageView


class StartExerciseFragment : Fragment() {

    private val viewModel: StartExerciseViewModel by activityViewModels()
    var exercise: Int = 0
    private var progress: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_start_exercise, container, false)

        val gifView = view.findViewById<GifImageView>(R.id.gifImageView)
        val progressIndicator = view.findViewById<LinearProgressIndicator>(R.id.progressIndicator)
        val exerciseName = view.findViewById<TextView>(R.id.exerciseName)
        val exerciseRep = view.findViewById<TextView>(R.id.exerciseRep)
        val doneButton = view.findViewById<MaterialButton>(R.id.doneButton)
        val prevButton = view.findViewById<Button>(R.id.prevButton)
        val skipButton = view.findViewById<Button>(R.id.skipButton)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.count.collect {
                    exercise = it
                    progress = (exercise*100)/viewModel.getTotalExercise()
                    progressIndicator.setProgress(progress,false)
                    if(viewModel.getTotalExercise()==exercise){
                        doneButton.text = "Finish"
                    }
                    exerciseRep.text = "$it"
                }
            }
        }

        doneButton.setOnClickListener {
            if(viewModel.getTotalExercise()==exercise){
                doneButton.text = "Finish"
                Toast.makeText(requireContext(),"Completed", Toast.LENGTH_SHORT).show()
                //navigate to workout screen
                requireActivity().onBackPressed()
            }
            else findNavController().navigate(R.id.action_startExerciseFragment_to_restFragment)
        }

        return view
    }
}
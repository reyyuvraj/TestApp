package com.example.testapp.home

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.R
import com.example.testapp.home.adapter.FitAdapter
import com.example.testapp.home.model.WorkoutItem
import com.google.android.material.card.MaterialCardView
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import timber.log.Timber

class HomeFragment : Fragment(R.layout.fragment_home), SensorEventListener,
    FitAdapter.FitInterface {

    private var sensorManager: SensorManager? = null
    private var running = false
    private var totalSteps = 0f
    private var previousTotalSteps = 0f

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadData()
        resetSteps()
        setUpViews()
        setUpListeners()

        /*val progressList = listOf(
            ProgressData(progress1 = 54, progress2 = 36),
            ProgressData(progress1 = 36, progress2 = 18),
            ProgressData(progress1 = 18, progress2 = 27),
            ProgressData(progress1 = 27, progress2 = 45),
            ProgressData(progress1 = 45, progress2 = 63),
            ProgressData(progress1 = 63, progress2 = 81),
            ProgressData(progress1 = 81, progress2 = 54)
        )
        val recyclerView = view.findViewById<RecyclerView>(R.id.fh_goals_rv)
        recyclerView?.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val adapter = ProgressAdapter()
        recyclerView?.adapter = adapter
        adapter.setData(progressList)*/

        val workoutList = listOf(
            WorkoutItem(1, "Workout", R.drawable.ic_weightlifting),
            WorkoutItem(2, "Yoga", R.drawable.ic_weightlifting),
            WorkoutItem(3, "Dance", R.drawable.ic_weightlifting),
            WorkoutItem(4, "Meditate", R.drawable.ic_weightlifting),
            WorkoutItem(5, "Sleep", R.drawable.ic_weightlifting)
        )
        val recyclerView = view.findViewById<RecyclerView>(R.id.fh_fit_rv)
        recyclerView?.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val adapter = FitAdapter(this)
        recyclerView?.adapter = adapter
        adapter.setData(workoutList)

        sensorManager = activity?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    private fun setUpViews() {
        view?.findViewById<TextView>(R.id.fh_progress2_text)?.text =
            previousTotalSteps.toInt().toString()
        view?.findViewById<TextView>(R.id.fh_progress1_text)?.text =
            (previousTotalSteps.toInt() / 10).toString()
        view?.findViewById<CircularProgressBar>(R.id.fh_circularProgressBar2)?.apply {
            setProgressWithAnimation(previousTotalSteps)
        }
        view?.findViewById<CircularProgressBar>(R.id.fh_circularProgressBar)?.apply {
            setProgressWithAnimation(previousTotalSteps / 10)
        }
        view?.findViewById<TextView>(R.id.log_tv)?.apply {
            visibility = View.GONE
        }
    }

    private fun setUpListeners() {
        view?.findViewById<MaterialCardView>(R.id.fh_goals_card)?.setOnClickListener {
            val title = "Your Daily Goals"
            val info =
                "You score Heart Points for each minute of activity that gets your heart pumping, like a brisk walk. Increase the intensity to earn more."
            it.findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToInfoBottomSheetFragment(
                    title, info
                )
            )
        }
        view?.findViewById<MaterialCardView>(R.id.fh_week_card)?.setOnClickListener {
            val title = "Weekly Target"
            val info =
                "You score Heart Points for each minute of activity that gets your heart pumping, like a brisk walk. Increase the intensity to earn more."
            it.findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToInfoBottomSheetFragment(
                    title, info
                )
            )
        }
        view?.findViewById<MaterialCardView>(R.id.fh_weight_card)?.setOnClickListener {
            val title = "Weight"
            val info = "This gives you the insights about your weight."
            it.findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToInfoBottomSheetFragment(
                    title, info
                )
            )
        }
        view?.findViewById<MaterialCardView>(R.id.fh_sleep_card)?.setOnClickListener {
            val title = "Sleep Data"
            val info =
                "Everybody needs a different amount of sleep. To understand how much you need, start by making a note of how you feel each day. If you feel relaxed and energized, you've likely had a good night's rest. If you haven't slept enough, you might feel tired and irritable.\n" + "In addition, keep an eye on your sleep duration. It may sound obvious, but a quick daily check-in can help you spot patterns."
            it.findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToInfoBottomSheetFragment(
                    title, info
                )
            )
        }
        view?.findViewById<ImageView>(R.id.fh_sleep_card_arrow)?.setOnClickListener {
            openBrowser("https://aasm.org/")
        }
        view?.findViewById<MaterialCardView>(R.id.fh_fit_card)?.setOnClickListener {
            val title = "Stay fit"
            val info = "Follow along with curated playlists to help you stay healthy from home."
            it.findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToInfoBottomSheetFragment(
                    title, info
                )
            )
        }
    }

    override fun onResume() {
        super.onResume()
        running = true
        val stepSensor: Sensor? = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        if (stepSensor == null) {
            Toast.makeText(
                requireContext(), "No sensor detected on this device", Toast.LENGTH_SHORT
            ).show()
        } else {
            sensorManager?.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onSensorChanged(p0: SensorEvent?) {
        if (running) {
            totalSteps = p0!!.values[0]
            val currentSteps = totalSteps.toInt() - previousTotalSteps.toInt()
            val stepsTxt = view?.findViewById<TextView>(R.id.fh_progress2_text)
            stepsTxt?.text = currentSteps.toString()

            view?.findViewById<CircularProgressBar>(R.id.fh_circularProgressBar2)?.apply {
                setProgressWithAnimation(currentSteps.toFloat())
            }
            view?.findViewById<CircularProgressBar>(R.id.fh_circularProgressBar)?.apply {
                setProgressWithAnimation(currentSteps.toFloat() / 10)
            }
        }
    }

    private fun resetSteps() {
        view?.findViewById<TextView>(R.id.fh_progress2_text)?.apply {
            setOnClickListener {
                Toast.makeText(requireContext(), "Long Tap to reset steps", Toast.LENGTH_SHORT)
                    .show()
            }
            setOnLongClickListener {
                previousTotalSteps = totalSteps
                this.text = 0.toString()
                view?.findViewById<CircularProgressBar>(R.id.fh_circularProgressBar2)?.apply {
                    setProgressWithAnimation(0.toFloat())
                }
                saveData()
                true
            }
        }
    }

    private fun saveData() {
        val sharedPreferences = activity?.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.putFloat("key1", previousTotalSteps)
        editor?.apply()
    }

    private fun loadData() {
        val sharedPreferences = activity?.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val savedNumber = sharedPreferences?.getFloat("key1", 0f)
        Timber.tag("MainActivity").d("data = $savedNumber")
        previousTotalSteps = savedNumber ?: 0f
    }

    private fun openBrowser(url: String = "") {
        val intent = Intent(Intent.ACTION_VIEW)
        val subscriptionURL = String.format(url)
        intent.data = Uri.parse(subscriptionURL)
        startActivity(intent)
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        // not to be implemented yet
    }

    override fun itemClicked(id: Int) {
        when (id) {
            1 -> {
                openBrowser("https://youtube.com/playlist?list=PLbpi6ZahtOH4OqtS9rvYW_jf-agPEMEsP")
            }
            2 -> {
                openBrowser("https://youtube.com/playlist?list=PLbpi6ZahtOH7ywA3QW1TzGTdke3fs_a3M")
            }
            3 -> {
                openBrowser("https://youtube.com/playlist?list=PLbpi6ZahtOH5EepHG_-wgELWR5KeOaLj0")
            }
            4 -> {
                openBrowser("https://youtube.com/playlist?list=PLbpi6ZahtOH5DkgyNn-zbBWf3CmYT-dRt")
            }
            5 -> {
                openBrowser("https://youtube.com/playlist?list=PLbpi6ZahtOH5g4TSKKX4TfI-TaRhH8q8d")
            }
            else -> {
                openBrowser("https://youtube.com/playlist?list=PLbpi6ZahtOH5EepHG_-wgELWR5KeOaLj0")
            }
        }
    }
}
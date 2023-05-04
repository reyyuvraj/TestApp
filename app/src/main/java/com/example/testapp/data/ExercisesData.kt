package com.example.testapp.data

import com.example.testapp.R
import com.example.testapp.model.SingleExerciseDataClass

fun getExerciseData(): List<SingleExerciseDataClass>{
    return listOf(
        SingleExerciseDataClass(
            gif = R.drawable.testgif,
            exerciseName = "Push up",
            exerciseRep = "x16"
        )
    )
}
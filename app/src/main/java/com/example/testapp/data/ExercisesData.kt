package com.example.testapp.data

import com.example.testapp.R
import com.example.testapp.model.SingleExerciseDataClass

fun getExerciseData(): List<SingleExerciseDataClass>{
    return listOf(
        SingleExerciseDataClass(
            gif = R.drawable.exersice_1,
            exerciseName = "Mountain Climb",
            exerciseRep = "x16"
        ),
        SingleExerciseDataClass(
            gif = R.drawable.exersice_2,
            exerciseName = "Half Crunches",
            exerciseRep = "x20"
        ),
        SingleExerciseDataClass(
            gif = R.drawable.exersice_3,
            exerciseName = "Dips",
            exerciseRep = "x24"
        ),
        SingleExerciseDataClass(
            gif = R.drawable.exersice_4,
            exerciseName = "Alternate Crunhes",
            exerciseRep = "x16"
        ),
        SingleExerciseDataClass(
            gif = R.drawable.exersice_5,
            exerciseName = "Leg Raises",
            exerciseRep = "x12"
        ),
        SingleExerciseDataClass(
            gif = R.drawable.exersice_6,
            exerciseName = "Knee Touch",
            exerciseRep = "x32"
        ),
        SingleExerciseDataClass(
            gif = R.drawable.exersice_7,
            exerciseName = "Half V-ups",
            exerciseRep = "x10"
        ),
        SingleExerciseDataClass(
            gif = R.drawable.exersice_8,
            exerciseName = "Full Crunches",
            exerciseRep = "x16"
        ),
        SingleExerciseDataClass(
            gif = R.drawable.exersice_9,
            exerciseName = "Toe Touch",
            exerciseRep = "x12"
        ),
        SingleExerciseDataClass(
            gif = R.drawable.exersice_10,
            exerciseName = "Push Up hand raises",
            exerciseRep = "x16"
        ),
        SingleExerciseDataClass(
            gif = R.drawable.exersice_11,
            exerciseName = "Plank Leg Raise",
            exerciseRep = "x16"
        ),
        SingleExerciseDataClass(
            gif = R.drawable.exersice_12,
            exerciseName = "Russian Twist",
            exerciseRep = "x32"
        ),
        SingleExerciseDataClass(
            gif = R.drawable.exersice_13,
            exerciseName = "Kegel",
            exerciseRep = "x16"
        ),
        SingleExerciseDataClass(
            gif = R.drawable.exersice_14,
            exerciseName = "Full V-ups",
            exerciseRep = "x12"
        ),
        SingleExerciseDataClass(
            gif = R.drawable.exersice_15,
            exerciseName = "Standing Toe Touch",
            exerciseRep = "x16"
        )
    )
}
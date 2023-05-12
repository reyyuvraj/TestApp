package com.example.testapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.testapp.data.getExerciseData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class StartExerciseViewModel: ViewModel() {

    private val _count = MutableStateFlow(0)
    val count: StateFlow<Int> = _count.asStateFlow()

    fun getTotalExercise(): Int{
        return getExerciseData().size-1
    }

    fun incrementCount() {
        _count.value++
    }

    fun decrementCount() {
        _count.value--
    }

    fun resetCounter(){
        _count.value = 0
    }
}
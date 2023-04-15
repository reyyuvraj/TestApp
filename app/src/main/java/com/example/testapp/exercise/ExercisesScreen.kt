package com.example.testapp.exercise

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.testapp.R

@Composable
fun ExercisesScreen(

){

}


@Preview(showBackground = true)
@Composable
fun ExerciseCard(
){
    Row(modifier = Modifier.fillMaxWidth()) {
        Image(painter = painterResource(id = R.drawable.ic_fullbody), contentDescription = null)
        Column() {
            Image(painter = painterResource(id = R.drawable.ic_fullbody), contentDescription = null)
        }
    }
}
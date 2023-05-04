package com.example.testapp.rest

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testapp.theme.CardEndColorColor
import com.example.testapp.theme.CardStartColor
import com.example.testapp.theme.TextColor
import com.example.testapp.theme.UnselectedChipColor
import java.util.Timer
import java.util.TimerTask


@Composable
fun RestScreen(
    onSkipPressed: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {


        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Get ready for next exercise \n till then",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = UnselectedChipColor
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "Take Rest For...",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                color = UnselectedChipColor
            )
        }

//        Spacer(modifier = Modifier.height(32.dp))
        TimerScreen()

        Button(
            onClick = { onSkipPressed() },
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Blue,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(18.dp)
        ) {
            Text(text = "SKIP")
        }
    }
}

@Composable
fun TimerScreen(
    restTime: Long = 20L
) {
    var remainingTime by remember { mutableStateOf(restTime) } // Set initial time to 60 seconds
    val timer = remember { Timer() }

    // Update the remaining time every second
    LaunchedEffect(Unit) {
        timer.schedule(object : TimerTask() {
            override fun run() {
                if (remainingTime > 0) {
                    remainingTime--
                }
            }
        }, 1000, 1000)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),
        contentAlignment = Alignment.Center
    ) {
        // Create a circular progress bar
//        CircularProgressIndicator(
//            progress = remainingTime.toFloat() / 60,
//            modifier = Modifier.size(200.dp),
//            strokeWidth = 12.dp,
//            color = Color.DarkGray
//        )

        Canvas(modifier = Modifier.size(200.dp)) {
            drawArc(
                color = Color.LightGray,
                -90f,
                360F,
                useCenter = false,
                style = Stroke(
                    24.dp.toPx(), cap = StrokeCap.Round
                )
            )
            drawArc(
                color = Color.DarkGray,
                -90f,
                360 * remainingTime.toFloat()/ restTime.toInt(),
                useCenter = false,
                style = Stroke(
                    12.dp.toPx(), cap = StrokeCap.Round
                )
            )
        }

        // Display the remaining time in the center of the progress bar
        Text(
            text = "$remainingTime sec",
            style = TextStyle(fontSize = 30.sp),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}







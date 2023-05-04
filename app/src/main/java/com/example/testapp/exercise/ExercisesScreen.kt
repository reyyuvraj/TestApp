package com.example.testapp.exercise

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.testapp.R
import com.example.testapp.theme.TextColor
import java.nio.file.WatchEvent
import java.util.Locale

@Composable
fun ExercisesScreen(
    onBackArrowPressed: () -> Unit
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(top = 40.dp, bottom = 36.dp)) {

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.BottomStart
        ) {

            Image(
                painter = painterResource(id = R.drawable.pushupimage),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black
                            ),
                            startY = 160f
                        )
                    )
            )

            Icon(
                imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = "Arrow Back",
                tint = Color.White,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(top = 16.dp, start = 16.dp)
                    .size(24.dp)
//                    .clickable { onBackArrowPressed() }
            )

            Text(
                text = "FullBody Workout",
                fontSize = MaterialTheme.typography.h5.fontSize,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, bottom = 18.dp),
                textAlign = TextAlign.Start
            )
        }

        Text(
            text = "11 mins - 11 workouts",
            fontSize = MaterialTheme.typography.body1.fontSize,
            fontWeight = FontWeight.Bold,
            color = Color.Black.copy(alpha = 0.8f),
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 12.dp)
        )
        Divider(
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f),
            thickness = 1.dp,
//            modifier = Modifier.padding(vertical = 8.dp)
        )

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(vertical = 8.dp, horizontal = 12.dp)
        ) {
            items(count = itemList.size, itemContent = {
                ExerciseCard(
                    exerciseTitle = itemList[it].exerciseTitle,
                    numberOfReps = itemList[it].numberOfReps,
                    imageId = itemList[it].imageId,
                )
                Divider(
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f),
                    thickness = 1.dp,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

            })
        }

    }

}


@Composable
fun ExerciseCard(
    exerciseTitle: String,
    numberOfReps: String,
    imageId: Int

) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = imageId),
            contentDescription = null,
            modifier = Modifier
                .size(100.dp)
                .padding(12.dp)
        )
        Column(
            modifier = Modifier.padding(start = 12.dp),
            verticalArrangement = Arrangement.SpaceAround,
        ) {
            Text(
                text = exerciseTitle.uppercase(Locale.ROOT),
                fontSize = MaterialTheme.typography.subtitle1.fontSize,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 6.dp)
            )
            Text(
                text = numberOfReps,
                fontSize = MaterialTheme.typography.body1.fontSize,
                fontWeight = FontWeight.Bold,
                color = Color.Black.copy(alpha = 0.5f),
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding()
            )
        }
    }
}

val itemList =
    mutableStateListOf(
        ExerciseCardDetails(
            imageId = R.drawable.ic_fullbody,
            exerciseTitle = "Jumping Jacks",
            numberOfReps = "x16"
        ),
        ExerciseCardDetails(
            imageId = R.drawable.ic_fullbody,
            exerciseTitle = "Incline Push-ups",
            numberOfReps = "x12"
        ),
        ExerciseCardDetails(
            imageId = R.drawable.ic_fullbody,
            exerciseTitle = "Knee Push-ups",
            numberOfReps = "x12"
        ),
        ExerciseCardDetails(
            imageId = R.drawable.ic_fullbody,
            exerciseTitle = "push-ups",
            numberOfReps = "x18"
        ),
        ExerciseCardDetails(
            imageId = R.drawable.ic_fullbody,
            exerciseTitle = "Jumping Jacks",
            numberOfReps = "x16"
        ),
        ExerciseCardDetails(
            imageId = R.drawable.ic_fullbody,
            exerciseTitle = "Incline Push-ups",
            numberOfReps = "x12"
        ),
        ExerciseCardDetails(
            imageId = R.drawable.ic_fullbody,
            exerciseTitle = "Knee Push-ups",
            numberOfReps = "x12"
        ),
        ExerciseCardDetails(
            imageId = R.drawable.ic_fullbody,
            exerciseTitle = "push-ups",
            numberOfReps = "x18"
        ),
    )
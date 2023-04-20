package com.example.testapp.workout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.testapp.R
import com.example.testapp.theme.CardEndColorColor
import com.example.testapp.theme.CardStartColor
import com.example.testapp.theme.TextColor

//@Preview(showBackground = true)
@Composable
fun WorkoutScreen(
    onViewMoreNavigate: (String) -> Unit
) {

    var selectedLevel by remember {
        mutableStateOf(-1)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(top = 48.dp, bottom = 36.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Daily Plan",
            fontSize = MaterialTheme.typography.h6.fontSize,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp),
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.height(8.dp))

        DailyPlan(
            titleCalorie = "Calories",
            numberCalorie = "720",
            titleSteps = "Steps",
            numberSteps = "1000"
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "What Do You Want to Train",
            fontSize = MaterialTheme.typography.h6.fontSize,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp),
            textAlign = TextAlign.Start
        )

        Spacer(modifier = Modifier.height(2.dp))

        CategoryChipGroup(
            modifier = Modifier.fillMaxWidth(),
            onCategorySelected = {
                selectedLevel = it ?: 0
            },
            categoryList = listOf(
                ChipItem(0, "Beginner"),
                ChipItem(1, "Intermediate"),
                ChipItem(2, "Advance")
            ),
            unselectedBackgroundColor = Color.Transparent,
            contentSpacing = Arrangement.SpaceEvenly
        )

        Spacer(modifier = Modifier.height(8.dp))
        WorkoutCard(
            title = "Fullbody Workout",
            exerciseTime = "11 Exercises | 32mins",
            imgInt = R.drawable.ic_fullbody,
            onViewMoreNavigate = { onViewMoreNavigate("$selectedLevel FullBody") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        WorkoutCard(
            title = "Lowebody Workout",
            exerciseTime = "12 Exercises | 40mins",
            imgInt = R.drawable.ic_leg,
            onViewMoreNavigate = { onViewMoreNavigate("$selectedLevel LowerBody") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        WorkoutCard(
            title = "AB Workout",
            exerciseTime = "14 Exercises | 20mins",
            imgInt = R.drawable.ic_abs,
            onViewMoreNavigate = { onViewMoreNavigate("$selectedLevel ABsBody") }
        )

    }

}


@Composable
fun DailyPlan(
    titleCalorie: String = "Title",
    numberCalorie: String = "100",
    titleSteps: String = "Title",
    numberSteps: String = "100"
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = CardStartColor,
                    shape = RoundedCornerShape(26.dp)
                )
                .padding(16.dp)
                .weight(1f)
        ) {

            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = titleCalorie,
                        fontSize = MaterialTheme.typography.body1.fontSize,
                        color = Color.Black
                    )
                    Image(
                        painter = painterResource(id = R.drawable.ic_calories),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    text = numberCalorie,
                    fontSize = MaterialTheme.typography.h4.fontSize,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Kcal",
                    fontSize = MaterialTheme.typography.body1.fontSize,
                    color = TextColor.copy(alpha = 0.8f)
                )
            }
        }

        Spacer(modifier = Modifier.width(8.dp))
        Box(
            modifier = Modifier
                .background(
                    color = CardEndColorColor,
                    shape = RoundedCornerShape(26.dp)
                )
                .padding(16.dp)
                .weight(1f)
        ) {

            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = titleSteps,
                        fontSize = MaterialTheme.typography.body1.fontSize,
                        color = Color.Black
                    )
                    Image(
                        painter = painterResource(id = R.drawable.ic_steps),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    text = numberSteps,
                    fontSize = MaterialTheme.typography.h4.fontSize,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Steps",
                    fontSize = MaterialTheme.typography.body1.fontSize,
                    color = TextColor.copy(alpha = 0.8f)
                )
            }
        }
    }
}


@Composable
fun WorkoutCard(
    modifier: Modifier = Modifier,
    title: String = "Title",
    exerciseTime: String = "30min",
    imgInt: Int = R.drawable.ic_fullbody,
    onViewMoreNavigate: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(CardStartColor, CardEndColorColor)
                ),
                shape = RoundedCornerShape(26.dp)
            )
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = title,
                    fontSize = MaterialTheme.typography.h6.fontSize,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = exerciseTime,
                    fontSize = MaterialTheme.typography.body1.fontSize,
                    color = TextColor
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { onViewMoreNavigate() },
                    modifier = Modifier,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.White,
                        contentColor = CardStartColor
                    ),
                    shape = CircleShape,
                    elevation = ButtonDefaults.elevation(
                        defaultElevation = 0.dp,
                        pressedElevation = 0.dp,
                        disabledElevation = 0.dp,
                        hoveredElevation = 0.dp,
                        focusedElevation = 0.dp
                    )
                ) {
                    Text(
                        text = "View more",
                        style = MaterialTheme.typography.body1
                    )
                }
            }

            Box(contentAlignment = Alignment.Center) {
                Image(
                    painter = painterResource(id = R.drawable.ic_ecllipse),
                    contentDescription = null,
                    modifier = Modifier.size(100.dp)
                )

                Image(
                    painter = painterResource(id = imgInt),
                    contentDescription = null,
                    modifier = Modifier.size(120.dp)
                )
            }

        }
    }
}
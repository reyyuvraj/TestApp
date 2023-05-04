package com.example.testapp.workout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.example.testapp.theme.CardEndColorColor
import com.example.testapp.theme.UnselectedChipColor
import com.example.testapp.theme.UnselectedChipTextColor

@Composable
fun CategoryChipGroup(
    modifier: Modifier = Modifier,
    categoryList: List<ChipItem> = generateChipList(),
    fontSize: TextUnit = MaterialTheme.typography.body2.fontSize,
    fontWeight: FontWeight = FontWeight.Normal,
    selectedColor: Color = CardEndColorColor,
    unselectedBackgroundColor: Color = UnselectedChipColor,
    contentSpacing: Arrangement.HorizontalOrVertical = Arrangement.spacedBy(4.dp),
    contentPadding: PaddingValues = PaddingValues(2.dp),
    chipTextVerticalPadding: Dp = 6.dp,
    chipTextHorizontalPadding: Dp = 10.dp,
    onCategorySelected: (Int?) -> Unit
) {
    val (selectedOption, onOptionSelected) = remember {
        mutableStateOf(
            if (categoryList.isNotEmpty()) categoryList[0].id else null
        )
    }

    LaunchedEffect(key1 = selectedOption) {
        onCategorySelected(selectedOption)
    }

    LazyRow(modifier = modifier, horizontalArrangement = contentSpacing, contentPadding = contentPadding) {
        items(categoryList.size) {
            CategoryChip(
                selected = categoryList[it].id == selectedOption,
                category = categoryList[it],
                fontSize = fontSize,
                fontWeight = fontWeight,
                selectedColor = selectedColor,
                unselectedBackgroundColor = unselectedBackgroundColor,
                chipTextVerticalPadding = chipTextVerticalPadding,
                chipTextHorizontalPadding = chipTextHorizontalPadding,
                onOptionSelected = onOptionSelected
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CategoryChip(
    selected: Boolean,
    category: ChipItem,
    fontSize: TextUnit,
    fontWeight: FontWeight,
    selectedColor: Color,
    unselectedBackgroundColor: Color,
    chipTextVerticalPadding: Dp,
    chipTextHorizontalPadding: Dp,
    onOptionSelected: (Int) -> Unit,
) {
    Surface(
        shape = RoundedCornerShape(100),
        color = if (selected) selectedColor else unselectedBackgroundColor,
        onClick = { onOptionSelected(category.id) },
    ) {
        Text(
            text = category.string, style = MaterialTheme.typography.button.copy(
                color = if (selected) Color.White else UnselectedChipTextColor.copy(alpha = 0.5f),
                fontWeight = fontWeight,
                fontSize = fontSize
            ),
            modifier = Modifier.padding(vertical = chipTextVerticalPadding, horizontal = chipTextHorizontalPadding)
        )
    }
}

data class ChipItem(
    val id: Int,
    val string: String
)

private fun generateChipList() = listOf(
    ChipItem(0, "Daily"),
    ChipItem(1, "Weekly"),
    ChipItem(2, "Monthly"),
)
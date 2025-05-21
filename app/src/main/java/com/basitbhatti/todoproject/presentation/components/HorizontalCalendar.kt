package com.basitbhatti.todoproject.presentation.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalCalendar(
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
    daysToShow: Int = 30,
    startDate: LocalDate = LocalDate.now(),
    todayColor: Color = MaterialTheme.colorScheme.primary,
    selectedColor: Color = MaterialTheme.colorScheme.secondary,
    defaultColor: Color = MaterialTheme.colorScheme.onSurface
) {
    val dates = remember(startDate, daysToShow) {
        List(daysToShow) { index ->
            startDate.plusDays(index.toLong())
        }
    }

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val flingBehavior = rememberSnapFlingBehavior(lazyListState = listState)

    Column(modifier = modifier.fillMaxWidth()) {
        // Weekday headers
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(bottom = 10.dp),
//            horizontalArrangement = Arrangement.SpaceAround
//        ) {
////            DayOfWeek.values().forEach { dayOfWeek ->
////                Text(
////                    text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
////                    style = MaterialTheme.typography.bodySmall,
////                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
////                    modifier = Modifier.width(40.dp),
////                    textAlign = TextAlign.Center
////                )
////            }
//        }

        // Dates
        LazyRow(
            state = listState,
            flingBehavior = flingBehavior,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            itemsIndexed(dates) { index, date ->
                CalendarDay(
                    date = date,
                    isSelected = date == selectedDate,
                    isToday = date == LocalDate.now(),
                    onClick = {
                        onDateSelected(date)
                        coroutineScope.launch {
                            listState.animateScrollToItem(index)
                        }
                    },
                    todayColor = todayColor,
                    selectedColor = selectedColor,
                    defaultColor = defaultColor,
                    modifier = Modifier.width(50.dp)
                )
            }
        }
    }
}

@Composable
fun CalendarDay(
    date: LocalDate,
    isSelected: Boolean,
    isToday: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    todayColor: Color,
    selectedColor: Color,
    defaultColor: Color = Color.Gray
) {
    val dayOfMonth = date.dayOfMonth.toString()
    val dayOfWeek = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())

    val bg = if (isSelected) Color.LightGray else Color.Transparent

    Column(
        modifier = modifier
            .padding(start = 5.dp)
            .clip(RoundedCornerShape(5.dp))
            .background(bg)
            .clickable {
                onClick()
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            modifier = Modifier.padding(top = 5.dp),
            text = dayOfWeek,
            style = MaterialTheme.typography.bodySmall,
            color = defaultColor.copy(alpha = 0.6f),
            fontSize = 12.sp
        )

        val backgroundColor = when {
            isSelected -> selectedColor
            isToday -> todayColor
            else -> Color.Transparent
        }

        val textColor = when {
            isSelected || isToday -> Color.DarkGray
            else -> defaultColor
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(vertical = 4.dp)
                .padding(4.dp),
            propagateMinConstraints = true,
        ) {
            Text(
                text = dayOfMonth,
                color = textColor,
                fontSize = 16.sp,
                fontWeight = if (isSelected || isToday) FontWeight.Bold else FontWeight.Normal,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    HorizontalCalendar(LocalDate.now(), onDateSelected = {})
}
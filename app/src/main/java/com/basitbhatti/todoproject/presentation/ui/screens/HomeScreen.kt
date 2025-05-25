package com.basitbhatti.todoproject.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.basitbhatti.todoproject.presentation.components.HorizontalCalendar
import com.basitbhatti.todoproject.presentation.components.dashedBorder
import com.basitbhatti.todoproject.presentation.navigation.Screen
import com.basitbhatti.todoproject.presentation.theme.GrayishColor
import com.basitbhatti.todoproject.presentation.theme.primaryContainer
import com.basitbhatti.todoproject.presentation.viewmodel.TaskViewModel
import com.basitbhatti.todoproject.utils.PERSON_TYPE
import com.pdftoexcel.bankstatementconverter.utils.PrefManager
import java.time.LocalDate

@Composable
fun HomeScreen(
    viewModel: TaskViewModel = hiltViewModel(),
    controller: NavHostController
) {

    val context = LocalContext.current
    val prefManager = PrefManager(context)
    if (prefManager.getString(PERSON_TYPE).isBlank()) {
        controller.navigate(Screen.UserType.route) {
            popUpTo(Screen.Home.route) {
                inclusive = true
            }
        }
    }

    var selectedDate by remember {
        mutableStateOf(LocalDate.now())
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 30.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {

        HorizontalCalendar(
            modifier = Modifier.padding(horizontal = 10.dp),
            selectedDate = selectedDate,
            onDateSelected = { selectedDate = it },
            daysToShow = 7
        )

        Text(
            "Primary Tasks",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 15.dp, vertical = 15.dp),

            )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(primaryContainer)
        ) {
            Column {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .height(45.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(primaryContainer)
                        .dashedBorder(
                            width = 2.dp,
                            color = Color.LightGray,
                            shape = MaterialTheme.shapes.medium,
                            on = 4.dp,
                            off = 4.dp
                        ), verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        imageVector = Icons.Filled.AddCircle,
                        contentDescription = "add",
                        colorFilter = ColorFilter.tint(GrayishColor),
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .size(30.dp)
                    )

                    Text(
                        text = "Add a new task",
                        fontSize = 16.sp,
                        color = GrayishColor
                    )
                }
            }
        }
    }

}

@Preview
@Composable
private fun HomePrev() {
    HomeScreen(controller = rememberNavController())
}
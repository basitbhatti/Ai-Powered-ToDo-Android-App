package com.basitbhatti.todoproject.presentation.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.basitbhatti.todoproject.domain.model.TaskItemEntity
import com.basitbhatti.todoproject.presentation.components.dashedBorder
import com.basitbhatti.todoproject.presentation.navigation.Screen
import com.basitbhatti.todoproject.presentation.theme.AppFontFamily
import com.basitbhatti.todoproject.presentation.theme.GrayishColor
import com.basitbhatti.todoproject.presentation.theme.lighterGray
import com.basitbhatti.todoproject.presentation.theme.primaryContainer
import com.basitbhatti.todoproject.presentation.viewmodel.TaskViewModel
import com.basitbhatti.todoproject.utils.PERSON_TYPE
import com.pdftoexcel.bankstatementconverter.utils.PrefManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate

@Composable
fun HomeScreen(
    viewModel: TaskViewModel = hiltViewModel(), controller: NavHostController
) {

    val context = LocalContext.current
    val prefManager = PrefManager(context)

    var showAddTaskSheet by remember {
        mutableStateOf(false)
    }

    if (!LocalInspectionMode.current) {
        if (prefManager.getString(PERSON_TYPE).isBlank()) {
            controller.navigate(Screen.UserType.route) {
                popUpTo(Screen.Home.route) {
                    inclusive = true
                }
            }
        }
    }

    val activeTasks = viewModel.activeTasks.collectAsState()

    LaunchedEffect(
        activeTasks
    ) {
        viewModel.sendTopPriorityReminder()
    }

    if (showAddTaskSheet) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 35.dp)
        ) {
            AddTaskBottomSheet(onDismiss = {
                showAddTaskSheet = false
            }, onTaskAdded = { item ->
                viewModel.addTask(item)
                showAddTaskSheet = false
            })
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 30.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
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
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 300.dp)
            ) {

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
                        )
                        .clickable {
                            showAddTaskSheet = true
                        }, verticalAlignment = Alignment.CenterVertically
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
                        text = "Add a new task", fontSize = 16.sp, color = GrayishColor
                    )
                }

                if (activeTasks.value.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(activeTasks.value.sortedByDescending { it.priority }) {
                            TaskItem(it) { item ->
                                viewModel.updateTask(item)
                            }
                        }
                    }

                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No Tasks Added Yet",
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp
                        )

                    }
                }

            }
        }
    }
}


@Composable
fun TaskItem(item: TaskItemEntity, onItemChecked: (TaskItemEntity) -> Unit) {

    val alpha = animateFloatAsState(
        targetValue = if (item.isCompleted) 1f else 0f
    )

    val scope = rememberCoroutineScope()

    var isStrikeThrough by remember {
        mutableStateOf(item.isCompleted)
    }

    Row(
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 5.dp)
            .fillMaxWidth()
            .height(65.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(lighterGray),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(0.8f)
                .padding(start = 10.dp),
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = item.title,
                textDecoration = if (isStrikeThrough) TextDecoration.LineThrough else TextDecoration.None,
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontFamily = AppFontFamily,
                fontWeight = FontWeight.Medium
            )

            Spacer(Modifier.height(5.dp))

            Text(
                text = item.description ?: "",
                textDecoration = if (item.isCompleted) TextDecoration.LineThrough else TextDecoration.None,
                fontSize = 12.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color.Gray,
                fontFamily = AppFontFamily,
                fontWeight = FontWeight.Normal
            )
        }

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .weight(0.2f), contentAlignment = Alignment.Center
        ) {
            Checkbox(checked = item.isCompleted, onCheckedChange = {
                isStrikeThrough = it
                scope.launch {
                    delay(1000)

                    val updatedItem = item.copy(isCompleted = it)
                    onItemChecked(updatedItem)
                }
            })
        }
    }

}

@Preview
@Composable
private fun Preview() {
    TaskItem(
        TaskItemEntity(
            id = 0,
            title = "Title of the task.",
            description = "A short description of the task showing here.",
            dueDay = LocalDate.now(),
            isCompleted = false,
            priority = 1
        ),
        { item ->

        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskBottomSheet(
    onDismiss: () -> Unit,
    onTaskAdded: (TaskItemEntity) -> Unit,
) {

    var isTodaySelected by remember {
        mutableStateOf(true)
    }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    val scope = rememberCoroutineScope()

    var title by remember {
        mutableStateOf("")
    }

    var description by remember {
        mutableStateOf("")
    }

    ModalBottomSheet(
        modifier = Modifier.fillMaxHeight(0.7f),
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = Color.White,
        contentColor = Color.White
    ) {
        Column(
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.padding(start = 10.dp),
                    text = "Add New Task",
                    fontSize = 22.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Medium
                )

                IconButton(onClick = {
                    scope.launch {
                        sheetState.hide()
                        if (!sheetState.isVisible) {
                            onDismiss()
                        }
                    }
                }) {
                    Image(
                        modifier = Modifier
                            .size(30.dp)
                            .padding(end = 10.dp),
                        imageVector = Icons.Rounded.Close,
                        colorFilter = ColorFilter.tint(Color.Gray),
                        contentDescription = "Close Bottom Dialog"
                    )
                }
            }

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp),
                value = title,
                singleLine = true,
                onValueChange = {
                    title = it
                },
                placeholder = {
                    Text("Title")
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.LightGray,
                    unfocusedIndicatorColor = Color.LightGray
                )
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .padding(start = 15.dp, top = 15.dp, end = 15.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(lighterGray)
            ) {
                TextField(modifier = Modifier.fillMaxWidth(), value = description, onValueChange = {
                    description = it
                }, placeholder = {
                    Text(
                        "Short Description (Optional)",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Light,
                        color = Color.Gray
                    )
                }, colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp, top = 15.dp)
                    .height(40.dp)
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(0.5f)
                        .padding(end = 7.5.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(
                            if (isTodaySelected) {
                                Color.LightGray
                            } else {
                                lighterGray
                            }
                        )
                        .clickable {
                            isTodaySelected = true
                        },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Image(
                        modifier = Modifier
                            .padding(end = 5.dp)
                            .size(25.dp),
                        colorFilter = ColorFilter.tint(
                            if (isTodaySelected) {
                                Color.Black
                            } else {
                                Color.Gray
                            }
                        ),
                        imageVector = Icons.Rounded.Schedule,
                        contentDescription = "Today",
                    )

                    Text(
                        "Today", color = if (isTodaySelected) {
                            Color.Black
                        } else {
                            Color.Gray
                        }, modifier = Modifier.padding(start = 5.dp)
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(0.5f)
                        .padding(start = 7.5.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(
                            if (!isTodaySelected) {
                                Color.LightGray
                            } else {
                                lighterGray
                            }
                        )
                        .clickable {
                            isTodaySelected = false
                        },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Image(
                        modifier = Modifier
                            .padding(end = 5.dp)
                            .size(25.dp),
                        colorFilter = ColorFilter.tint(
                            if (!isTodaySelected) {
                                Color.Black
                            } else {
                                Color.Gray
                            }
                        ),
                        imageVector = Icons.Rounded.Schedule,
                        contentDescription = "Tomorrow",
                    )

                    Text(
                        "Tomorrow", color = if (!isTodaySelected) {
                            Color.Black
                        } else {
                            Color.Gray
                        }, modifier = Modifier.padding(start = 5.dp)
                    )
                }
            }

            Button(enabled = title.isNotBlank(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 40.dp, end = 40.dp, top = 15.dp)
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Gray, disabledContainerColor = Color.LightGray
                ),
                onClick = {
                    val task = TaskItemEntity(
                        id = 0,
                        title = title,
                        description = description,
                        dueDay = if (isTodaySelected) {
                            LocalDate.now()
                        } else {
                            LocalDate.now().plusDays(1)
                        },
                        isCompleted = false,
                        priority = 1
                    )
                    onTaskAdded(task)
                }) {
                Text(text = "Save", color = Color.Black)
            }
        }
    }

}
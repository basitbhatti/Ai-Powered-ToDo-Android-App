package com.basitbhatti.todoproject.presentation.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.basitbhatti.todoproject.R

@Composable
fun PersonTypeScreen(
    modifier: Modifier = Modifier, onButtonClick: () -> Unit
) {

    val options = listOf(
        "8 PM", "10 PM", "Select Time"
    )

    var selectedOption by remember {
        mutableStateOf(options[0])
    }

    val optionsNight = listOf(
        "3 PM", "5 PM", "Select Time"
    )

    var selectedOptionNight by remember {
        mutableStateOf(optionsNight[0])
    }

    var isMorningPersonChecked by remember {
        mutableStateOf(false)
    }

    var isNightPersonChecked by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = modifier
                .align(Alignment.Center)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Choose Your Daily Rhythm",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Understanding your natural rhythm helps us deliver reminders at proper time.",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(top = 10.dp, start = 35.dp, end = 35.dp)
            )

            Spacer(modifier = Modifier.height(100.dp))

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .zIndex(1f)
                        .padding(horizontal = 15.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.LightGray),
                ) {

                    Box(
                        modifier = Modifier.fillMaxHeight(), contentAlignment = Alignment.Center
                    ) {
                        Checkbox(checked = isMorningPersonChecked, onCheckedChange = {
                            isMorningPersonChecked = it
                            if (it) {
                                isNightPersonChecked = false
                            }
                        })
                    }

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Morning Person",
                            color = MaterialTheme.colorScheme.onBackground,
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 15.dp)
                        )

                        Text(
                            text = "9AM - 5PM",
                            color = MaterialTheme.colorScheme.onBackground,
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier.padding(top = 5.dp, bottom = 15.dp)
                        )
                    }
                }

                AnimatedVisibility(
                    modifier = Modifier.offset(y = -4.dp),
                    visible = isMorningPersonChecked,
                    enter = slideInVertically() + expandVertically() + fadeIn(),
                    exit = slideOutVertically() + shrinkVertically() + fadeOut()
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .border(
                                width = 2.dp,
                                color = Color.LightGray,
                                shape = RoundedCornerShape(5.dp)
                            ),

                        ) {

                        Text(
                            text = "Choose when to remind you?",
                            color = Color.DarkGray,
                            fontFamily = FontFamily(Font(R.font.lite)),
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 15.dp, start = 10.dp)
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            Row(

                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                RadioButton(selected = selectedOption == options[0], onClick = {
                                    selectedOption = options[0]
                                })
                                Text(text = options[0])
                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                RadioButton(selected = selectedOption == options[1], onClick = {
                                    selectedOption = options[1]
                                })
                                Text(text = options[1])
                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                RadioButton(selected = selectedOption == options[2], onClick = {
                                    selectedOption = options[2]
                                })
                                Text(text = options[2])
                            }

                        }
                    }

                }

            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp)
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .zIndex(1f)
                        .padding(horizontal = 15.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.LightGray),
                ) {

                    Box(
                        modifier = Modifier.fillMaxHeight(), contentAlignment = Alignment.Center
                    ) {
                        Checkbox(checked = isNightPersonChecked, onCheckedChange = {
                            isNightPersonChecked = it
                            if (it) {
                                isMorningPersonChecked = false
                            }
                        })

                    }

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            text = "Night Person",
                            color = MaterialTheme.colorScheme.onBackground,
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 15.dp)
                        )

                        Text(
                            text = "7PM - 4AM",
                            color = MaterialTheme.colorScheme.onBackground,
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier.padding(top = 5.dp, bottom = 15.dp)
                        )
                    }
                }

                AnimatedVisibility(
                    modifier = Modifier.offset(y = -4.dp),
                    visible = isNightPersonChecked,
                    enter = slideInVertically() + expandVertically() + fadeIn(),
                    exit = slideOutVertically() + shrinkVertically() + fadeOut()
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .border(
                                width = 2.dp,
                                color = Color.LightGray,
                                shape = RoundedCornerShape(5.dp)
                            ),

                        ) {

                        Text(
                            text = "Choose when to remind you?",
                            color = Color.DarkGray,
                            fontFamily = FontFamily(Font(R.font.lite)),
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 15.dp, start = 10.dp)
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            Row(

                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                RadioButton(
                                    selected = selectedOptionNight == optionsNight[0],
                                    onClick = {
                                        selectedOptionNight = optionsNight[0]
                                    })
                                Text(text = optionsNight[0])
                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                RadioButton(
                                    selected = selectedOptionNight == optionsNight[1],
                                    onClick = {
                                        selectedOptionNight = optionsNight[1]
                                    })
                                Text(text = optionsNight[1])
                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                RadioButton(
                                    selected = selectedOptionNight == optionsNight[2],
                                    onClick = {
                                        selectedOptionNight = optionsNight[2]
                                    })
                                Text(text = optionsNight[2])
                            }

                        }
                    }
                }

            }

        }

        Button(
            enabled = isMorningPersonChecked or isNightPersonChecked,
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
                .height(50.dp)
                .align(Alignment.BottomCenter),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Gray, disabledContainerColor = Color.LightGray
            ),
            onClick = onButtonClick
        ) {
            Text(text = "Continue")
        }

    }


}

@Preview
@Composable
private fun Preview() {
    PersonTypeScreen(onButtonClick = {})
}
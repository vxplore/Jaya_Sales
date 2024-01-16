package com.example.jayasales.presentation.screens

import android.annotation.SuppressLint
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.Button import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.debduttapanda.j3lib.NotificationService
import com.debduttapanda.j3lib.dep
import com.debduttapanda.j3lib.listState
import com.debduttapanda.j3lib.rememberNotifier
import com.debduttapanda.j3lib.sep
import com.debduttapanda.j3lib.stringState
import com.example.jayasales.MyDataIds
import com.example.jayasales.R
import com.example.jayasales.model.Attendance
import com.example.jayasales.model.Datum
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date

@SuppressLint("RememberReturnType")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarkAttendanceScreen(
    notifier: NotificationService = rememberNotifier(),
    attencomments: State<String> = stringState(key = MyDataIds.attencomments),
    currentInTimeState: State<String> = stringState(key = MyDataIds.currentInTimeState),
    currentOutTimeState: State<String> = stringState(key = MyDataIds.currentOutTimeState),
) {
    var currentLatitude by remember { mutableStateOf(0.0) }
    var currentLongitude by remember { mutableStateOf(0.0) }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.Mark_Attendance),
                        fontSize = 20.sep,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2B2B2B)
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            notifier.notify(
                                MyDataIds.back
                            )
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBackIosNew,
                            contentDescription = "ArrowBackIosNew",
                            tint = Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFFFEB56)
                )
            )
        }
    )
    {
        var isInButtonSelected by remember { mutableStateOf(false) }
        var isCheckInSelected by remember { mutableStateOf(true) }
        var currentInTime by remember { mutableStateOf<Date?>(null) }
        var currentOutTime by remember { mutableStateOf<Date?>(null) }

        val checkTimeIn = if (!isInButtonSelected) {
            currentInTime?.let {
                val formattedTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(it)
                "Check In: $formattedTime"
            } ?: "Check In: ---:---"
        } else {
            "Check In: ---:---"
        }

        val checkTimeOut = if (!isInButtonSelected) {
            currentOutTime?.let {
                val formattedTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(it)
                "Check Out: $formattedTime"
            } ?: "Check Out: ---:---"
        } else {
            "Check Out: ---:---"
        }
        Box(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 20.dep)
                .fillMaxSize(),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.height(20.dep))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dep)
                ) {
                    GoogleMapSection(
                        latitude = currentLatitude,
                        longitude = currentLongitude,
                        onLocationChanged = { newLatitude, newLongitude ->
                            currentLatitude = newLatitude
                            currentLongitude = newLongitude
                        }
                    )
                }
                Spacer(modifier = Modifier.height(24.dep))
                val currentDate = remember {
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = currentDate,
                        fontSize = 18.sep,
                        color = Color(0xFF2B2B2B)
                    )
                }
                Spacer(modifier = Modifier.height(16.dep))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .background(Color(0xFFD6D6D6), RoundedCornerShape(4.dep))
                            .height(52.dep)
                            .weight(.5f)
                    ) {
                        Text(
                            text = "$checkTimeIn${currentInTimeState.value}",
                            fontSize = 12.sep,
                            color = Color(0xFF2B2B2B)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dep))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .background(Color(0xFFD6D6D6), RoundedCornerShape(4.dep))
                            .height(52.dep)
                            .weight(.5f)
                    ) {
                        Text(
                            text = "$checkTimeOut ${currentOutTimeState.value}",
                            fontSize = 12.sep,
                            color = Color(0xFF2B2B2B)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(14.dep))
                OutlinedTextField(
                    value = attencomments.value,
                    onValueChange = {
                        notifier.notify(MyDataIds.attencomments, it)
                    },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    placeholder = {
                        Text(
                            stringResource(id = R.string.Comments),
                            color = Color(0xFF666666),
                            fontSize = 14.sep
                        )
                    },
                    modifier = Modifier
                        .background(Color.White)
                        .fillMaxWidth(),
                    textStyle = TextStyle(
                        color = Color(0xFF222222),
                        fontSize = 14.sep
                    ),
                    colors = TextFieldDefaults.colors(
                        unfocusedIndicatorColor = Color(0xFFDDDDDD),
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                    ),
                )
            }
            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier
                    .padding(bottom = 100.dep)
                    .fillMaxSize()
            ) {
                Button(
                    onClick = {
                        if (isCheckInSelected) {
                            currentInTime = Calendar.getInstance().time
                            //notifier.notify(MyDataIds.currentInTime,currentInTime)
                        } else {
                            currentOutTime = Calendar.getInstance().time
                            //notifier.notify(MyDataIds.currentOutTime, currentOutTime)
                        }
                        isCheckInSelected = !isCheckInSelected
                        val status = if (isCheckInSelected) "check_out" else "check_in"
                        notifier.notify(MyDataIds.status, status)
                        notifier.notify(MyDataIds.btnClick)
                    },
                    colors = ButtonDefaults.buttonColors(
                        if (isCheckInSelected) {
                            Color(0xFF1FB574)
                        } else {
                            Color(0xFFFF4155)
                        }
                    ),
                    shape = RoundedCornerShape(24.dep),
                    modifier = Modifier
                        .padding(horizontal = 100.dp)
                        .fillMaxWidth()
                ) {
                    Text(if (isCheckInSelected) "Check In" else "Check Out", color = Color.White)
                }
            }
            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier
                    .padding(bottom = 40.dep)
                    .fillMaxSize()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(horizontal = 88.dep)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(4.dep))
                        .clickable {
                            notifier.notify(MyDataIds.timeSheet)
                        }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.DateRange,
                        contentDescription = "DateRange",
                        tint = Color(0xFF222222),
                        modifier = Modifier
                            .size(16.dep)
                    )
                    Text(
                        stringResource(id = R.string.Timesheets),
                        color = Color(0xFF222222),
                        fontSize = 12.sep,
                    )
                    Icon(
                        imageVector = Icons.Default.ArrowForwardIos,
                        contentDescription = "ArrowForwardIos",
                        tint = Color(0xFF222222),
                        modifier = Modifier
                            .size(12.dep)
                    )
                }
            }
        }
    }
}


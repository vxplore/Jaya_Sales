package com.example.jayasales.presentation.screens

import android.annotation.SuppressLint
import android.graphics.PorterDuff
import android.widget.DatePicker
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import com.debduttapanda.j3lib.NotificationService
import com.debduttapanda.j3lib.dep
import com.debduttapanda.j3lib.rememberIntState
import com.debduttapanda.j3lib.rememberNotifier
import com.debduttapanda.j3lib.rememberStringState
import com.debduttapanda.j3lib.rememberTState
import com.debduttapanda.j3lib.sep
import com.example.jayasales.MyDataIds

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeLineScreen(
    notifier: NotificationService = rememberNotifier(),
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Rakesh Kumar",
                        fontSize = 20.sep,
                        color = Color(0xFF2B2B2B),
                        fontWeight = FontWeight.Bold,
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
        Column(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 16.dep)
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(16.dep))
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ){
                Text(
                    text = "Todays Timeline",
                    fontSize = 16.sep,
                    color = Color(0xFF362C2C),
                    fontWeight = FontWeight.SemiBold,
                )
                DatePick()
            }
            Spacer(modifier = Modifier.height(16.dep))
            Divider()
            Spacer(modifier = Modifier.height(20.dep))
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row (
                    modifier = Modifier
                        .weight(.25f)
                ){
                    Column (
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Box(
                            modifier = Modifier
                                .background(
                                    Color(0xFFD62B2B), CircleShape
                                )
                                .size(16.dep)
                        )
                        Box(
                            modifier = Modifier
                                .background(
                                    Color(0xFFD62B2B), CircleShape
                                )
                                .width(2.dep)
                                .fillMaxHeight()
                        )
                    }
                    Spacer(modifier = Modifier.width(4.dep))
                    Column {
                        Text(
                            text = "Rashamkrishna Bhander",
                            fontSize = 16.sep,
                            color = Color(0xFF362C2C),
                            fontWeight = FontWeight.SemiBold,
                        )
                        Text(
                            text = "- Order Taken",
                            fontSize = 14.sep,
                            color = Color(0xFF0CA056),
                        )
                        Row {
                            Text(
                                text = "-Store Visit Time:",
                                fontSize = 12.sep,
                                color = Color(0xFF6D6666),
                                fontWeight = FontWeight.SemiBold,
                            )
                            Text(
                                text = "10 AM",
                                fontSize = 12.sep,
                                color = Color(0xFF362C2C),
                                fontWeight = FontWeight.SemiBold,
                            )
                        }
                    }
                }
                Row (
                    modifier = Modifier
                        .weight(.25f)
                ){
                    Column (
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Box(
                            modifier = Modifier
                                .background(
                                    Color(0xFFD62B2B), CircleShape
                                )
                                .size(16.dep)
                        )
                        Box(
                            modifier = Modifier
                                .background(
                                    Color(0xFFD62B2B), CircleShape
                                )
                                .width(2.dep)
                                .fillMaxHeight()
                        )
                    }
                    Spacer(modifier = Modifier.width(4.dep))
                    Column {
                        Text(
                            text = "Rashamkrishna Bhander",
                            fontSize = 16.sep,
                            color = Color(0xFF362C2C),
                            fontWeight = FontWeight.SemiBold,
                        )
                        Text(
                            text = "- Order Taken",
                            fontSize = 14.sep,
                            color = Color(0xFF0CA056),
                        )
                        Row {
                            Text(
                                text = "-Store Visit Time:",
                                fontSize = 12.sep,
                                color = Color(0xFF6D6666),
                                fontWeight = FontWeight.SemiBold,
                            )
                            Text(
                                text = "10 AM",
                                fontSize = 12.sep,
                                color = Color(0xFF362C2C),
                                fontWeight = FontWeight.SemiBold,
                            )
                        }
                    }
                }
                Row (
                    modifier = Modifier
                        .weight(.25f)
                ){
                    Column (
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Box(
                            modifier = Modifier
                                .background(
                                    Color(0xFFD62B2B), CircleShape
                                )
                                .size(16.dep)
                        )
                        Box(
                            modifier = Modifier
                                .background(
                                    Color(0xFFD62B2B), CircleShape
                                )
                                .width(2.dep)
                                .fillMaxHeight()
                        )
                    }
                    Spacer(modifier = Modifier.width(4.dep))
                    Column {
                        Text(
                            text = "Rashamkrishna Bhander",
                            fontSize = 16.sep,
                            color = Color(0xFF362C2C),
                            fontWeight = FontWeight.SemiBold,
                        )
                        Text(
                            text = "- Order Taken",
                            fontSize = 14.sep,
                            color = Color(0xFF0CA056),
                        )
                        Row {
                            Text(
                                text = "-Store Visit Time:",
                                fontSize = 12.sep,
                                color = Color(0xFF6D6666),
                                fontWeight = FontWeight.SemiBold,
                            )
                            Text(
                                text = "10 AM",
                                fontSize = 12.sep,
                                color = Color(0xFF362C2C),
                                fontWeight = FontWeight.SemiBold,
                            )
                        }
                    }
                }
                Row (
                    modifier = Modifier
                        .weight(.25f)
                ){
                    Column (
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Box(
                            modifier = Modifier
                                .background(
                                    Color(0xFFD62B2B), CircleShape
                                )
                                .size(16.dep)
                        )
                      /*  Box(
                            modifier = Modifier
                                .background(
                                    Color(0xFFD62B2B), CircleShape
                                )
                                .width(2.dep)
                                .fillMaxHeight()
                        )*/
                    }
                    Spacer(modifier = Modifier.width(4.dep))
                    Column {
                        Text(
                            text = "Rashamkrishna Bhander",
                            fontSize = 16.sep,
                            color = Color(0xFF362C2C),
                            fontWeight = FontWeight.SemiBold,
                        )
                        Text(
                            text = "- Order Taken",
                            fontSize = 14.sep,
                            color = Color(0xFF0CA056),
                        )
                        Row {
                            Text(
                                text = "-Store Visit Time:",
                                fontSize = 12.sep,
                                color = Color(0xFF6D6666),
                                fontWeight = FontWeight.SemiBold,
                            )
                            Text(
                                text = "10 AM",
                                fontSize = 12.sep,
                                color = Color(0xFF362C2C),
                                fontWeight = FontWeight.SemiBold,
                            )
                        }
                    }
                }
            }
        }
    }
}

@SuppressLint("ResourceAsColor", "NewApi")
@Composable
fun DatePick(
    mYear: State<Int> = rememberIntState(id = MyDataIds.mYear),
    mMonth: State<Int> = rememberIntState(id = MyDataIds.mMonth),
    mDay: State<Int> = rememberIntState(id = MyDataIds.mDay),
    currentDate: State<Long> = rememberTState(id = MyDataIds.Currentdate),
    todayDate: State<String> = rememberStringState(id = MyDataIds.todayDate)
) {
    val mContext = LocalContext.current
    val mDate = remember { mutableStateOf("") }
    val mDatePickerDialog = android.app.DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            mDate.value = "$mDayOfMonth.${mMonth + 1}.$mYear"
        }, mYear.value, mMonth.value, mDay.value
    )
    mDatePickerDialog.datePicker.maxDate = currentDate.value//long
    mDatePickerDialog.datePicker.backgroundTintMode = PorterDuff.Mode.LIGHTEN

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .background(Color.White, RoundedCornerShape(5.dep))
            .border(1.dep, Color(0xFF959595), RoundedCornerShape(5.dep))
            .height(44.dep)
            .padding(horizontal = 8.dep)

    ) {
        if (mDate.value.isNotEmpty()) {
            Text(
                text = mDate.value,
                color = Color(0xFF2C323A),
                fontSize = 12.sep,
                modifier = Modifier
                //.border(1.5.dep,MaterialTheme.colorScheme.surface)
            )
        }
        else {
            Text(
                text = todayDate.value,
                color = Color(0xFF2C323A),
                fontSize = 12.sep,
                modifier = Modifier
                //.border(1.5.dep,MaterialTheme.colorScheme.surface)
            )
        }
        IconButton(
            onClick = {
                mDatePickerDialog.show()
            },
        ) {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = "",
                tint = Color(0xFFEB3D34)
            )
        }

    }
}
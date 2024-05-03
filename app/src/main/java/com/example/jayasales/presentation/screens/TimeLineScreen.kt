package com.example.jayasales.presentation.screens

import android.annotation.SuppressLint
import android.graphics.PorterDuff
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.debduttapanda.j3lib.NotificationService
import com.debduttapanda.j3lib.boolState
import com.debduttapanda.j3lib.dep
import com.debduttapanda.j3lib.listState
import com.debduttapanda.j3lib.rememberIntState
import com.debduttapanda.j3lib.rememberNotifier
import com.debduttapanda.j3lib.rememberStringState
import com.debduttapanda.j3lib.rememberTState
import com.debduttapanda.j3lib.sep
import com.debduttapanda.j3lib.stringState
import com.example.jayasales.MyDataIds
import com.example.jayasales.model.SalesManDatum
import com.example.jayasales.model.TrackDatum
import com.example.jayasales.presentation.viewmodels.TimeLineViewModel
import java.text.SimpleDateFormat
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeLineScreen(
    notifier: NotificationService = rememberNotifier(),
    tracker: SnapshotStateList<TrackDatum> = listState(key = MyDataIds.tracker),
    loadingState: State<Boolean> = boolState(key = MyDataIds.loadingState),
    nameState: State<String> = stringState(key = MyDataIds.nameState),
) {
    val viewModel: TimeLineViewModel = viewModel()
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = nameState.value,
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
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Todays Timeline",
                    fontSize = 16.sep,
                    color = Color(0xFF362C2C),
                    fontWeight = FontWeight.SemiBold,
                )
                DatePick(
                    viewModel = viewModel,
                    formatter = viewModel.formatter
                )
            }
            Spacer(modifier = Modifier.height(16.dep))
            Divider()
            Spacer(modifier = Modifier.height(20.dep))
            /*   Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {*/
            if (loadingState.value) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        //.padding(bottom = 60.dep)
                        .fillMaxSize()
                ) {
                    CircularProgressIndicator(
                        color = Color(0xFF2DB87C),
                    )
                }
            } else {
             /*   LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(vertical = 10.dep),
                    verticalArrangement = Arrangement.spacedBy(20.dep)
                ) {
                    items(tracker.size) { index ->
                        val item = tracker[index]
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Row(
                                modifier = Modifier
                                    //.weight(.25f)
                                ,
                            ) {
                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .background(
                                                Color(0xFFD62B2B), CircleShape
                                            )
                                            .size(16.dep)
                                    )
                                   // if (index < tracker.size - 1) {
                                        Box(
                                            modifier = Modifier
                                                .background(
                                                    Color(0xFFD62B2B), CircleShape
                                                )
                                                .width(2.dep)
                                                .height(20.dep)
                                        )
                                   // }
                                }
                                Spacer(modifier = Modifier.width(4.dep))
                                Column {
                                    Text(
                                        text = item.store_name,
                                        fontSize = 16.sep,
                                        color = Color(0xFF362C2C),
                                        fontWeight = FontWeight.SemiBold,
                                    )
                                    Text(
                                        text = if (item.order_taken) "Order Taken" else "No Order Taken",
                                        fontSize = 14.sep,
                                        color = if (item.order_taken) {
                                            Color(0xFF0CA056)
                                        } else {
                                            Color(0xFFD62B2B)
                                        },
                                    )

                                    Row {
                                        Text(
                                            text = "-Store Visit Time:",
                                            fontSize = 12.sep,
                                            color = Color(0xFF6D6666),
                                            fontWeight = FontWeight.SemiBold,
                                        )
                                        Text(
                                            text = item.visit_time,
                                            fontSize = 12.sep,
                                            color = Color(0xFF362C2C),
                                            fontWeight = FontWeight.SemiBold,
                                        )
                                    }
                                }
                            }
                        }
                    }
                }*/

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                   /* verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,*/
                ) {
                    tracker.forEachIndexed { index, item ->
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Row(
                                //modifier = Modifier.weight(.25f)
                            ) {
                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .background(
                                                Color(0xFFD62B2B), CircleShape
                                            )
                                            .size(16.dep)
                                    )
                                     if (index < tracker.size - 1) {
                                    Box(
                                        modifier = Modifier
                                            .background(
                                                Color(0xFFD62B2B), CircleShape
                                            )
                                            .width(2.dep)
                                            .height(80.dep)
                                            //.weight(.5f)
                                    )
                                     }
                                }
                                Spacer(modifier = Modifier.width(4.dep))
                                Column {
                                    Text(
                                        text = item.store_name,
                                        fontSize = 16.sep,
                                        color = Color(0xFF362C2C),
                                        fontWeight = FontWeight.SemiBold,
                                    )
                                    Text(
                                        text = if (item.order_taken) "Order Taken" else "No Order Taken",
                                        fontSize = 14.sep,
                                        color = if (item.order_taken) {
                                            Color(0xFF0CA056)
                                        } else {
                                            Color(0xFFD62B2B)
                                        },
                                    )

                                    Row {
                                        Text(
                                            text = "-Store Visit Time:",
                                            fontSize = 12.sep,
                                            color = Color(0xFF6D6666),
                                            fontWeight = FontWeight.SemiBold,
                                        )
                                        Text(
                                            text = item.visit_time,
                                            fontSize = 12.sep,
                                            color = Color(0xFF362C2C),
                                            fontWeight = FontWeight.SemiBold,
                                            /*modifier = Modifier
                                                .padding(bottom = 20.dep)*/
                                        )
                                    }
                                }
                            }
                        }
                    }
                }


            }

            /* Row(
                 modifier = Modifier
                     .weight(.25f)
             ) {
                 Column(
                     verticalArrangement = Arrangement.Center,
                     horizontalAlignment = Alignment.CenterHorizontally
                 ) {
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
             Row(
                 modifier = Modifier
                     .weight(.25f)
             ) {
                 Column(
                     verticalArrangement = Arrangement.Center,
                     horizontalAlignment = Alignment.CenterHorizontally
                 ) {
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
             Row(
                 modifier = Modifier
                     .weight(.25f)
             ) {
                 Column(
                     verticalArrangement = Arrangement.Center,
                     horizontalAlignment = Alignment.CenterHorizontally
                 ) {
                     Box(
                         modifier = Modifier
                             .background(
                                 Color(0xFFD62B2B), CircleShape
                             )
                             .size(16.dep)
                     )
                     *//*  Box(
                          modifier = Modifier
                              .background(
                                  Color(0xFFD62B2B), CircleShape
                              )
                              .width(2.dep)
                              .fillMaxHeight()
                      )*//*
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
            }*/
            //}
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
    todayDate: State<String> = rememberStringState(id = MyDataIds.todayDate),
    viewModel: TimeLineViewModel,
    formatter: SimpleDateFormat
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
                //.border(1.5.dp,MaterialTheme.colorScheme.surface)
            )
        } else {
            Text(
                text = todayDate.value,
                color = Color(0xFF2C323A),
                fontSize = 12.sep,
                modifier = Modifier
                //.border(1.5.dp,MaterialTheme.colorScheme.surface)
            )
        }

        IconButton(
            onClick = {
                mDatePickerDialog.show()
                mDatePickerDialog.setOnDateSetListener { _, year, month, dayOfMonth ->
                    val selectedDate = Calendar.getInstance().apply {
                        set(Calendar.YEAR, year)
                        set(Calendar.MONTH, month)
                        set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    }.time
                    viewModel.updateSelectedDate(selectedDate)
                }
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
package com.example.jayasales.presentation.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.debduttapanda.j3lib.NotificationService
import com.debduttapanda.j3lib.boolState
import com.debduttapanda.j3lib.dep
import com.debduttapanda.j3lib.listState
import com.debduttapanda.j3lib.rememberNotifier
import com.debduttapanda.j3lib.sep
import com.example.jayasales.MyDataIds
import com.example.jayasales.R
import com.example.jayasales.model.TimeSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeSheetScreen(
    notifier: NotificationService = rememberNotifier(),
    showTimeSheet: SnapshotStateList<TimeSheet> = listState(key = MyDataIds.showTimeSheet),
    timeSheetLoadingState: State<Boolean> = boolState(key = MyDataIds.timeSheetLoadingState),
    lostInternet: State<Boolean> = boolState(key = MyDataIds.lostInternet),
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.Timesheet),
                        fontSize = 20.sep,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
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
        if (lostInternet.value) {
            LostInternet_ui(onDismissRequest = { notifier.notify(MyDataIds.onDissmiss) })
        }
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Spacer(modifier = Modifier.height(16.dep))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Outlined.DateRange,
                        contentDescription = "DateRange",
                        tint = Color(0xFFD62B2B),
                        modifier = Modifier
                            .size(20.dep)
                    )
                    Text(
                        stringResource(id = R.string.View_Timesheets),
                        color = Color(0xFFD62B2B),
                        fontSize = 16.sep,
                    )
                }
                Spacer(modifier = Modifier.height(16.dep))
                Divider()
                Spacer(modifier = Modifier.height(16.dep))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(horizontal = 20.dep)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(id = R.string.Date),
                        fontSize = 12.sep,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .weight(.25f),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.width(16.dep))
                    Text(
                        text = stringResource(id = R.string.Check_In),
                        fontSize = 12.sep,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .weight(.25f),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.width(16.dep))
                    Text(
                        text = stringResource(id = R.string.Checkout),
                        fontSize = 12.sep,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .weight(.25f),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.width(16.dep))
                    Text(
                        text = stringResource(id = R.string.Working_hr),
                        fontSize = 12.sep,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .weight(.25f),
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.height(16.dep))
                Divider()
                Spacer(modifier = Modifier.height(12.dep))
                if (timeSheetLoadingState.value) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(bottom = 60.dep)
                            .fillMaxSize()
                    ) {
                        CircularProgressIndicator(
                            color = Color(0XFFFF4155),
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .padding(horizontal = 20.dep)
                            .fillMaxSize(),
                        contentPadding = PaddingValues(vertical = 10.dep),
                        verticalArrangement = Arrangement.spacedBy(20.dep)
                    ) {
                        items(showTimeSheet) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(.25f),
                                    colors = CardDefaults.cardColors(Color(0XFFF5F5F5)),
                                    elevation = CardDefaults.cardElevation(
                                        defaultElevation = 4.dep
                                    ),
                                    border = BorderStroke(1.dep, color = Color(0XFFECECEC)),
                                    shape = RoundedCornerShape(8.dep),
                                ) {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center,
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(8.dep),
                                    ) {
                                        Text(
                                            text = it.date,
                                            fontSize = 12.sep,
                                            fontWeight = FontWeight.Bold,
                                            textAlign = TextAlign.Center
                                        )
                                        /*   Text(
                                        text = "Sat",
                                        fontSize = 12.sep,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier,
                                        textAlign = TextAlign.Center
                                    )*/
                                    }
                                }
                                Spacer(modifier = Modifier.width(12.dep))
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .weight(.25f)
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.up_right_arrow),
                                        contentDescription = "DateRange",
                                        tint = Color(0xFF1FB574),
                                        modifier = Modifier
                                            .padding(top = 4.dep)
                                            .size(8.dep)
                                    )
                                    Text(
                                        text = "${it.check_in.time}",
                                        fontSize = 12.sep,
                                        color = Color(0xFF222222),
                                        textAlign = TextAlign.Center
                                    )
                                    Icon(
                                        imageVector = Icons.Default.LocationOn,
                                        contentDescription = "ArrowForwardIos",
                                        tint = Color(0xFFD62B2B),
                                        modifier = Modifier
                                            .size(16.dep)
                                    )
                                }
                                Spacer(modifier = Modifier.width(12.dep))
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .weight(.25f)
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.red_up_right_arrow),
                                        contentDescription = "DateRange",
                                        tint = Color(0xFFD62B2B),
                                        modifier = Modifier
                                            .size(8.dep)
                                    )
                                    Text(
                                        text = "${it.check_out.time}",
                                        fontSize = 12.sep,
                                        color = Color(0xFF222222),
                                        textAlign = TextAlign.Center
                                    )
                                    Icon(
                                        imageVector = Icons.Default.LocationOn,
                                        contentDescription = "ArrowForwardIos",
                                        tint = Color(0xFFD62B2B),
                                        modifier = Modifier
                                            .size(16.dep)
                                    )
                                }
                                Spacer(modifier = Modifier.width(12.dep))
                                Text(
                                    text = "${it.working_hour}",
                                    color = Color(0xFFD62B2B),
                                    fontSize = 12.sep,
                                    modifier = Modifier
                                        .weight(.25f)
                                )
                            }
                            Spacer(modifier = Modifier.height(12.dep))
                            Divider()
                        }
                    }
                }
            }
        }
    }
}
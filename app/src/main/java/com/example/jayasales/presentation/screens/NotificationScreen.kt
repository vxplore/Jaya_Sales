package com.example.jayasales.presentation.screens

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.debduttapanda.j3lib.NotificationService
import com.debduttapanda.j3lib.dep
import com.debduttapanda.j3lib.listState
import com.debduttapanda.j3lib.rememberNotifier
import com.debduttapanda.j3lib.sep
import com.example.jayasales.MyDataIds
import com.example.jayasales.R
import com.example.jayasales.model.NotificationList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationScreen(
    notifier : NotificationService = rememberNotifier(),
    notificationList : SnapshotStateList<NotificationList> = listState(key = MyDataIds.notificationList)
){
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.notification),
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
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Spacer(modifier = Modifier.height(20.dep))
                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(notificationList) {
                        Row (
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(horizontal = 20.dep)
                                .fillMaxWidth()
                        ){
                            Row (
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier
                                    .padding(bottom = 8.dep)
                                    .background(Color(0x99FFEB56), CircleShape)
                                    .size(56.dep)
                            ){
                                Icon(
                                    imageVector = Icons.Outlined.Notifications,
                                    contentDescription = "Notifications",
                                    tint = Color(0xFFD62B2B),
                                    modifier = Modifier
                                        .width(24.dep)
                                        .height(24.dep)
                                )
                            }
                            Spacer(modifier = Modifier.width(16.dep))
                            Column {
                                Text(
                                    text = it.name,
                                    fontSize = 16.sep,
                                    fontWeight = FontWeight.SemiBold,
                                    textAlign = TextAlign.Center,
                                    color = Color(0xFF222222)
                                )
                                Text(
                                    text = it.details,
                                    fontSize = 14.sep,
                                    textAlign = TextAlign.Center,
                                    color = Color(0xFF222222)
                                )
                                Spacer(modifier = Modifier.height(4.dep))
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.AccessTime,
                                        contentDescription = "Time",
                                        tint = Color(0xFF6A9E73),
                                        modifier = Modifier
                                            .width(16.dep)
                                            .height(16.dep)
                                    )
                                    Spacer(modifier = Modifier.width(4.dep))
                                    Text(
                                        text = it.time,
                                        fontSize = 14.sep,
                                        textAlign = TextAlign.Center,
                                        color = Color(0xFF222222)
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dep))
                        Divider(
                            thickness = 0.4.dep
                        )
                        Spacer(modifier = Modifier.height(12.dep))
                    }
                }
            }
        }
    }
}
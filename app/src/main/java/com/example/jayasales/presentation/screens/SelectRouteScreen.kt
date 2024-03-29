package com.example.jayasales.presentation.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import com.debduttapanda.j3lib.NotificationService
import com.debduttapanda.j3lib.boolState
import com.debduttapanda.j3lib.dep
import com.debduttapanda.j3lib.listState
import com.debduttapanda.j3lib.rememberNotifier
import com.debduttapanda.j3lib.sep
import com.debduttapanda.j3lib.stringState
import com.example.jayasales.MyDataIds
import com.example.jayasales.R
import com.example.jayasales.model.Datum

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SelectRouteScreen(
    notifier: NotificationService = rememberNotifier(),
    routeList: SnapshotStateList<Datum> = listState(key = MyDataIds.routeList),
    routeLoadingState:State<Boolean> = boolState(key = MyDataIds.routeLoadingState),
    lostInternet: State<Boolean> = boolState(key = MyDataIds.lostInternet),

    ) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.routes),
                        fontSize = 20.sep,
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
        if (lostInternet.value) {
            LostInternet_ui(onDismissRequest = { notifier.notify(MyDataIds.onDissmiss) })
        }
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .padding(horizontal = 20.dep)
        ) {
            Spacer(modifier = Modifier.height(16.dep))
            SearchBox()
            Spacer(modifier = Modifier.height(20.dep))
            if (routeLoadingState.value) {
                Column (
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(bottom = 60.dep)
                        .fillMaxSize()
                ){
                    CircularProgressIndicator(
                        color = Color(0XFFFF4155),
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    itemsIndexed(routeList) { index, item ->
                        Spacer(modifier = Modifier.height(8.dep))
                        Card(
                            modifier = Modifier
                                .height(58.dep)
                                .fillMaxWidth(),
                            colors = CardDefaults.cardColors(Color.White),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 8.dep
                            ),
                            shape = RoundedCornerShape(4.dep)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 20.dep),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    IconButton(
                                        onClick = { },
                                        modifier = Modifier
                                            .width(20.dep)
                                            .height(20.dep)
                                            .shadow(
                                                4.dep,
                                                shape = CircleShape,
                                                clip = true
                                            )
                                            .background(Color(0XFFD62B2B))
                                    ) {
                                        Text(
                                            text = item.name.first().toString(),
                                            fontSize = 14.sep,
                                            fontWeight = FontWeight.Medium,
                                            color = Color.White
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(24.dep))
                                    Text(
                                        text = item.name,
                                        fontSize = 15.sep,
                                        color = Color.Black,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                                RadioButton(
                                    selected = item.isSelected,
                                    onClick = {
                                        val updatedList = routeList.mapIndexed { index, listItem ->
                                            listItem.copy(
                                                isSelected = (index == routeList.indexOf(
                                                    item
                                                ))
                                            )
                                        }
                                        routeList.clear()
                                        routeList.addAll(updatedList)
                                        notifier.notify(MyDataIds.routeId, index)
                                    },
                                    colors = RadioButtonDefaults.colors(
                                        selectedColor = Color(0XFFD62B2B),
                                        unselectedColor = Color(0xFF707070)
                                    ),
                                    modifier = Modifier
                                        .size(24.dep)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dep))
                    }

                    if (routeList.isEmpty()) {
                        item {
                            Text(
                                text = "No Routes Found",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dep),
                                textAlign = TextAlign.Center,
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SearchBox(
    notifier: NotificationService = rememberNotifier(),
    routeSearch: State<String> = stringState(
        key = MyDataIds.routeSearch,
    )
) {
    OutlinedTextField(
        value = routeSearch.value,
        onValueChange = {
            notifier.notify(MyDataIds.routeSearch, it)
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        placeholder = {
            Text(
                text = stringResource(id = R.string.searchParties),
                color = Color(0XFF898989),
                fontSize = 16.sep
            )
        },
        maxLines = 1,
        trailingIcon = {
            IconButton(
                onClick = {
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search",
                    tint = Color(0XFF898989)
                )
            }
        }, modifier = Modifier
            .fillMaxWidth()
    )
}
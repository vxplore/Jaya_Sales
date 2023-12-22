package com.example.jayasales.presentation.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
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
import com.debduttapanda.j3lib.NotificationService
import com.debduttapanda.j3lib.dep
import com.debduttapanda.j3lib.listState
import com.debduttapanda.j3lib.rememberBoolState
import com.debduttapanda.j3lib.rememberNotifier
import com.debduttapanda.j3lib.sep
import com.debduttapanda.j3lib.stringState
import com.example.jayasales.MyDataIds
import com.example.jayasales.R
import com.example.jayasales.model.PartiesDatum

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PartiesScreen(
    notifier:NotificationService = rememberNotifier(),
    allbtn: State<Boolean> = rememberBoolState(id = MyDataIds.allbtn),
    visitedbtn: State<Boolean> = rememberBoolState(id = MyDataIds.visitedbtn),
    pendingbtn: State<Boolean> = rememberBoolState(id = MyDataIds.pendingbtn),
    partiesList: SnapshotStateList<PartiesDatum> = listState(key = MyDataIds.partiesList)
){
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.Parties),
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
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .padding(horizontal = 20.dep)
        ) {
            Spacer(modifier = Modifier.height(16.dep))
            PartiesSearchBox()
            Spacer(modifier = Modifier.height(20.dep))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(
                    onClick = { notifier.notify(MyDataIds.allbtn) },
                    colors = if (allbtn.value) ButtonDefaults.buttonColors(Color(0XFF1FB574))
                    else ButtonDefaults.buttonColors(Color(0XFFF3FBF8)),
                    border = BorderStroke(0.dep, Color.Transparent)
                ) {
                    Text(
                        text = "All",
                        color = if (allbtn.value) Color.White else Color.Black
                    )
                }
                OutlinedButton(
                    onClick = { notifier.notify(MyDataIds.visitedbtn, "Visited") },
                    colors = if (visitedbtn.value) ButtonDefaults.buttonColors(Color(0XFF1FB574))
                    else ButtonDefaults.buttonColors(Color(0XFFF3FBF8)),
                    border = BorderStroke(0.dep, Color.Transparent)
                )
                {
                    Text(text = "Visited", color = if (visitedbtn.value) Color.White else Color.Black)
                }
                OutlinedButton(
                    onClick = { notifier.notify(MyDataIds.pendingbtn, "Pending") },
                    colors = if (pendingbtn.value) ButtonDefaults.buttonColors(Color(0XFF1FB574))
                    else ButtonDefaults.buttonColors(Color(0XFFF3FBF8)),
                    border = BorderStroke(0.dep, Color.Transparent)
                )
                {
                    Text(
                        text = "Pending",
                        color = if (pendingbtn.value) Color.White else Color.Black
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dep))
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 10.dep),
                verticalArrangement = Arrangement.spacedBy(20.dep)
            ) {
                items(partiesList) {
                    if (visitedbtn.value) {
                        Visited(it)
                    }else if (pendingbtn.value){
                        Pending(it)
                    }else{
                        Card(
                            modifier = Modifier
                                .height(80.dep)
                                .fillMaxWidth()
                                .clickable {
                                },
                            colors = CardDefaults.cardColors(Color.White),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 8.dep,
                                focusedElevation = 10.dep,
                            ),
                            shape = RoundedCornerShape(4.dep),
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .padding(horizontal = 12.dep)
                                    .fillMaxSize()
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
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
                                            text = it.store_name.first().toString(),
                                            fontSize = 14.sep,
                                            fontWeight = FontWeight.Medium,
                                            color = Color.White
                                        )
                                    }
                                    Column(
                                        //horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            text = it.store_name,
                                            fontSize = 14.sep,
                                            fontWeight = FontWeight.Medium,
                                            color = Color(0xFF222222)
                                        )
                                        Text(
                                            text = it.uid,
                                            fontSize = 12.sep,
                                            //fontWeight = FontWeight.Medium,
                                            color = Color(0xFF898989)
                                        )
                                    }
                                }
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "₹${it.amount}",
                                        fontSize = 14.sep,
                                        fontWeight = FontWeight.Medium,
                                        color = Color.Black
                                    )
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center,
                                        modifier = Modifier

                                            .background(
                                                if (it.status == "Pending") {
                                                    Color(0xFFF2F2F2)
                                                } else {
                                                    Color(0xFFF6D907)
                                                }, RoundedCornerShape(12.dep)
                                            )
                                            .wrapContentSize()
                                            .padding(6.dep)
                                    ) {
                                        Text(
                                            text = it.status,
                                            fontSize = 12.sep,
                                            //fontWeight = FontWeight.Medium,
                                            color =
                                            if (it.status == "Pending") {
                                                Color(0xFF1FB574)
                                            } else {
                                                Color(0xFFFFA956)
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PartiesSearchBox(
    notifier: NotificationService = rememberNotifier(),
    partiesSearch: State<String> = stringState(key = MyDataIds.partiesSearch,)
)
{
    OutlinedTextField(
        value = partiesSearch.value,
        onValueChange = {
            notifier.notify(MyDataIds.partiesSearch, it)
        },
        placeholder = {
            Text(
                text = "Search Route",
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
                    contentDescription = "",
                    tint = Color(0XFF898989)
                )
            }
        }, modifier = Modifier
            .fillMaxWidth()
    )
}

@Composable
fun Pending(
    it:PartiesDatum
){
    Card(
        modifier = Modifier
            .height(80.dep)
            .fillMaxWidth()
            .clickable {
            },
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dep,
            focusedElevation = 10.dep,
        ),
        shape = RoundedCornerShape(4.dep),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(horizontal = 12.dep)
                .fillMaxSize()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
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
                        text = it.store_name.first().toString(),
                        fontSize = 14.sep,
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    )
                }
                Column(
                    //horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = it.store_name,
                        fontSize = 14.sep,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF222222)
                    )
                    Text(
                        text = it.uid,
                        fontSize = 12.sep,
                        //fontWeight = FontWeight.Medium,
                        color = Color(0xFF898989)
                    )
                }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "₹${it.amount}",
                    fontSize = 14.sep,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier

                        .background(Color(0xFFF2F2F2), RoundedCornerShape(12.dep))
                        .wrapContentSize()
                        .padding(6.dep)
                ) {
                    Text(
                        text = it.status,
                        fontSize = 12.sep,
                        //fontWeight = FontWeight.Medium,
                        color = Color(0xFF1FB574)
                    )
                }
            }
        }
    }
}

@Composable
fun Visited(
    it: PartiesDatum
){
    Card(
        modifier = Modifier
            .height(80.dep)
            .fillMaxWidth()
            .clickable {
            },
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dep,
            focusedElevation = 10.dep,
        ),
        shape = RoundedCornerShape(4.dep),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(horizontal = 12.dep)
                .fillMaxSize()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
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
                        text = it.store_name.first().toString(),
                        fontSize = 14.sep,
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    )
                }
                Column(
                    //horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = it.store_name,
                        fontSize = 14.sep,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF222222)
                    )
                    Text(
                        text = it.uid,
                        fontSize = 12.sep,
                        //fontWeight = FontWeight.Medium,
                        color = Color(0xFF898989)
                    )
                }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "₹${it.amount}",
                    fontSize = 14.sep,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier

                        .background(Color(0xFFF6D907), RoundedCornerShape(12.dep))
                        .wrapContentSize()
                        .padding(6.dep)
                ) {
                    Text(
                        text = it.status,
                        fontSize = 12.sep,
                        //fontWeight = FontWeight.Medium,
                        color = Color(0xFFFFA956))
                }
            }
        }
    }
}

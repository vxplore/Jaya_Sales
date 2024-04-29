package com.example.jayasales.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.DefaultShadowColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.debduttapanda.j3lib.NotificationService
import com.debduttapanda.j3lib.dep
import com.debduttapanda.j3lib.listState
import com.debduttapanda.j3lib.rememberNotifier
import com.debduttapanda.j3lib.sep
import com.example.jayasales.MyDataIds
import com.example.jayasales.OrderType
import com.example.jayasales.R
import com.example.jayasales.model.PartiesDatum

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManagerOrderScreen(
    notifier: NotificationService = rememberNotifier(),
    distributorOrder: SnapshotStateList<DistributorOrder> = listState(key = MyDataIds.distributorOrder),
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Orders",
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
        var selectedItem by remember { mutableStateOf(0) }
        val items = listOf(
            OrderType.Product,
            OrderType.Services
        )
        Column(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 20.dep)
                .padding(bottom = 12.dep)
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(20.dep))
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .background(Color.White, RoundedCornerShape(10.dp))
                    .border(.5.dp, Color(0xFF959595), RoundedCornerShape(10.dp))
                    .height(54.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    items.forEachIndexed { index, item ->
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .background(
                                    if (selectedItem == index) Color(0xFFD62B2B) else Color.White,
                                    RoundedCornerShape(10.dep)
                                )
                                .border(
                                    .5.dp, Color(0xFF00000017),
                                    RoundedCornerShape(10.dp)
                                )
                                .fillMaxHeight()
                                .weight(.5f)
                                .clickable {
                                    selectedItem = index
                                }
                        ) {
                            Text(
                                text = item.title,
                                fontSize = 14.sep,
                                color = if (selectedItem == index) Color.White else Color(0xFF362C2C),
                                modifier = Modifier
                            )
                        }
                    }
                }
            }
            Spacer(
                modifier = Modifier
                    .height(24.dp)
            )
            if (selectedItem == 0) {
                if (distributorOrder.isEmpty()) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .padding(bottom = 20.dp)
                            .fillMaxSize()
                            .weight(1f)
                    ) {
                        Text(
                            text = "No Orders available to Show",
                            fontSize = 18.sep,
                            color = Color.Black
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize(),
                        //contentPadding = PaddingValues(vertical = 10.dep),
                        verticalArrangement = Arrangement.spacedBy(20.dep)
                    ) {
                        itemsIndexed(distributorOrder) { index, it ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .shadow(
                                        4.dep,
                                        RoundedCornerShape(4.dep),
                                        clip = true,
                                        DefaultShadowColor
                                    ),
                                /*  .clip(RoundedCornerShape(4.dep))
                                  .clickable {
                                  }*/
                                colors = CardDefaults.cardColors(Color.White),
                                elevation = CardDefaults.cardElevation(
                                    defaultElevation = 8.dep,
                                    focusedElevation = 10.dep,
                                ),
                                shape = RoundedCornerShape(4.dep),
                            ) {
                                Row(
                                    modifier = Modifier
                                        .padding(horizontal = 16.dep)
                                        .padding(top = 16.dep)
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        Text(
                                            text = "Order:#${it.order}",
                                            fontSize = 13.sep,
                                            fontWeight = FontWeight.SemiBold,
                                            color = Color.Black,
                                            modifier = Modifier
                                        )
                                        Spacer(modifier = Modifier.width(6.dep))
                                        Divider(
                                            modifier = Modifier
                                                .height(22.dep)
                                                .width(1.5.dep),
                                            color = Color(0xFF707070)
                                        )
                                        Spacer(modifier = Modifier.width(6.dep))
                                        Text(
                                            text = it.status,
                                            fontSize = 13.sep,
                                            fontWeight = FontWeight.SemiBold,
                                            color = Color(0xFF247FBB),
                                            modifier = Modifier
                                        )
                                    }
                                    Text(
                                        text = "Items:${it.item}",
                                        fontSize = 13.sep,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color.Black,
                                        modifier = Modifier
                                    )
                                }
                                Spacer(modifier = Modifier.height(10.dep))
                                Divider(
                                    color = Color(0xFFE9E5E5)
                                )
                                Spacer(modifier = Modifier.height(12.dep))
                                Row(
                                    modifier = Modifier
                                        .padding(horizontal = 16.dep)
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(
                                        horizontalArrangement = Arrangement.Center,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.house),
                                            contentDescription = stringResource(id = R.string.notification),
                                            tint = Color(0xFFD62B2B)
                                        )
                                        Spacer(modifier = Modifier.width(4.dep))
                                        Text(
                                            text = it.name,
                                            fontSize = 15.sep,
                                            //fontWeight = FontWeight.SemiBold,
                                            color = Color(0xFFD62B2B),
                                            modifier = Modifier
                                        )
                                    }
                                    Row(
                                        horizontalArrangement = Arrangement.Center,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.date),
                                            contentDescription = stringResource(id = R.string.notification),
                                            tint = Color.Black,
                                            modifier = Modifier
                                                .size(16.dep)
                                        )
                                        Spacer(modifier = Modifier.width(4.dep))
                                        Text(
                                            text = it.date,
                                            fontSize = 12.sep,
                                            //fontWeight = FontWeight.SemiBold,
                                            color = Color.Black,
                                            modifier = Modifier
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(12.dep))
                                Row(
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .padding(horizontal = 16.dep)
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.location),
                                        contentDescription = stringResource(id = R.string.notification),
                                        tint = Color.Black,
                                        modifier = Modifier.size(20.dep)

                                    )
                                    Spacer(modifier = Modifier.width(4.dep))
                                    Text(
                                        text = it.address,
                                        fontSize = 14.sep,
                                        //fontWeight = FontWeight.SemiBold,
                                        color = Color.Black,
                                        modifier = Modifier
                                    )
                                }
                                Spacer(modifier = Modifier.height(12.dep))
                                Button(
                                    onClick = {
                                    },
                                    modifier = Modifier
                                        .padding(horizontal = 16.dep)
                                        .padding(bottom = 10.dep)
                                        .fillMaxWidth(),
                                    colors = ButtonDefaults.buttonColors(Color(0xFF2DB87C)),
                                    elevation = ButtonDefaults.buttonElevation(
                                        defaultElevation = 8.dep,
                                        pressedElevation = 10.dep
                                    ),
                                    shape = RoundedCornerShape(4.dep)
                                ) {
                                    Text(
                                        text = "Confirm Order",
                                        fontSize = 15.sep,
                                        color = Color.White
                                    )
                                }
                            }
                        }
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    //contentPadding = PaddingValues(vertical = 10.dep),
                    verticalArrangement = Arrangement.spacedBy(20.dep)
                ) {
                    itemsIndexed(distributorOrder) { index, it ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .shadow(
                                    4.dep,
                                    RoundedCornerShape(4.dep),
                                    clip = true,
                                    DefaultShadowColor
                                ),
                            /*  .clip(RoundedCornerShape(4.dep))
                              .clickable {
                              }*/
                            colors = CardDefaults.cardColors(Color.White),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 8.dep,
                                focusedElevation = 10.dep,
                            ),
                            shape = RoundedCornerShape(4.dep),
                        ) {
                            Row(
                                modifier = Modifier
                                    .padding(horizontal = 16.dep)
                                    .padding(top = 16.dep)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = "Order:#${it.order}",
                                        fontSize = 13.sep,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color.Black,
                                        modifier = Modifier
                                    )
                                    Spacer(modifier = Modifier.width(6.dep))
                                    Divider(
                                        modifier = Modifier
                                            .height(22.dep)
                                            .width(1.5.dep),
                                        color = Color(0xFF707070)
                                    )
                                    Spacer(modifier = Modifier.width(6.dep))
                                    Text(
                                        text = it.status,
                                        fontSize = 13.sep,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color(0xFF247FBB),
                                        modifier = Modifier
                                    )
                                }
                                Text(
                                    text = "Items:${it.item}",
                                    fontSize = 13.sep,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.Black,
                                    modifier = Modifier
                                )
                            }
                            Spacer(modifier = Modifier.height(10.dep))
                            Divider(
                                color = Color(0xFFE9E5E5)
                            )
                            Spacer(modifier = Modifier.height(12.dep))
                            Row(
                                modifier = Modifier
                                    .padding(horizontal = 16.dep)
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.house),
                                        contentDescription = stringResource(id = R.string.notification),
                                        tint = Color(0xFFD62B2B)
                                    )
                                    Spacer(modifier = Modifier.width(4.dep))
                                    Text(
                                        text = it.name,
                                        fontSize = 15.sep,
                                        //fontWeight = FontWeight.SemiBold,
                                        color = Color(0xFFD62B2B),
                                        modifier = Modifier
                                    )
                                }
                                Row(
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.date),
                                        contentDescription = stringResource(id = R.string.notification),
                                        tint = Color.Black,
                                        modifier = Modifier
                                            .size(16.dep)
                                    )
                                    Spacer(modifier = Modifier.width(4.dep))
                                    Text(
                                        text = it.date,
                                        fontSize = 12.sep,
                                        //fontWeight = FontWeight.SemiBold,
                                        color = Color.Black,
                                        modifier = Modifier
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(12.dep))
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .padding(horizontal = 16.dep)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.location),
                                    contentDescription = stringResource(id = R.string.notification),
                                    tint = Color.Black,
                                    modifier = Modifier.size(20.dep)

                                )
                                Spacer(modifier = Modifier.width(4.dep))
                                Text(
                                    text = it.address,
                                    fontSize = 14.sep,
                                    //fontWeight = FontWeight.SemiBold,
                                    color = Color.Black,
                                    modifier = Modifier
                                )
                            }
                            Spacer(modifier = Modifier.height(12.dep))
                            Divider()
                            Text(
                                text = "Order Status",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                modifier = Modifier
                                    .padding(horizontal = 16.dep)
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier
                                    .padding(start = 16.dep)
                                    .fillMaxWidth()
                            ) {
                                Column(
                                    modifier = Modifier
                                        .weight(.25f)
                                ) {
                                    Text(
                                        text = "Booked",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color.Black
                                    )
                                    Spacer(modifier = Modifier.height(4.dep))
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .background(
                                                    if (it.trackorderstatus == "Dispatched" || it.trackorderstatus == "Received" || it.trackorderstatus == "Ready to Load" || it.trackorderstatus == "Booked") {
                                                        Color(0xFF2DB87C)
                                                    } else {
                                                        Color(0xFFD2D0D0)
                                                    }, CircleShape
                                                )
                                                .size(16.dep)
                                        )
                                        Box(
                                            modifier = Modifier
                                                .background(
                                                    if (it.trackorderstatus == "Dispatched" || it.trackorderstatus == "Received" || it.trackorderstatus == "Ready to Load") {
                                                        Color(0xFF2DB87C)
                                                    } else {
                                                        Color(0xFFD2D0D0)
                                                    }, CircleShape
                                                )
                                                .height(2.dep)
                                                .fillMaxWidth()
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(4.dep))
                                    Text(
                                        text = "12/02/22",
                                        fontSize = 8.sep,
                                        color = Color(0xFF818181)
                                    )
                                }
                                Column(
                                    modifier = Modifier
                                        .weight(.25f)
                                ) {
                                    Text(
                                        text = "02/12/23",
                                        fontSize = 8.sp,
                                        color = Color(0xFF818181)
                                    )
                                    Spacer(modifier = Modifier.height(4.dep))
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .background(
                                                    if (it.trackorderstatus == "Dispatched" || it.trackorderstatus == "Received" || it.trackorderstatus == "Ready to Load") {
                                                        Color(0xFF2DB87C)
                                                    } else {
                                                        Color(0xFFD2D0D0)
                                                    }, CircleShape
                                                )
                                                .size(16.dep)
                                        )
                                        Box(
                                            modifier = Modifier
                                                .background(
                                                    if (it.trackorderstatus == "Dispatched" || it.trackorderstatus == "Received") {
                                                        Color(0xFF2DB87C)
                                                    } else {
                                                        Color(0xFFD2D0D0)
                                                    }, CircleShape
                                                )
                                                .height(2.dep)
                                                .fillMaxWidth()
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(4.dep))
                                    Text(
                                        text = "Ready to Load",
                                        fontSize = 8.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color.Black
                                    )
                                }
                                Column(
                                    modifier = Modifier
                                        .weight(.25f)
                                ) {
                                    Text(
                                        text = "Dispatched",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color.Black
                                    )
                                    Spacer(modifier = Modifier.height(4.dep))
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .background(
                                                    if (it.trackorderstatus == "Dispatched" || it.trackorderstatus == "Received") {
                                                        Color(0xFF2DB87C)
                                                    } else {
                                                        Color(0xFFD2D0D0)
                                                    }, CircleShape
                                                )
                                                .size(16.dep)
                                        )
                                        Box(
                                            modifier = Modifier
                                                .background(
                                                    if (it.trackorderstatus == "Received") {
                                                        Color(0xFF2DB87C)
                                                    } else {
                                                        Color(0xFFD2D0D0)
                                                    }, CircleShape
                                                )
                                                .height(2.dep)
                                                .fillMaxWidth()
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(4.dep))
                                    Text(
                                        text = "2-01-2024 6:40",
                                        fontSize = 8.sp,
                                        color = Color(0xFF818181)
                                    )
                                }
                                Column(
                                    modifier = Modifier
                                        .weight(.25f),
                                ) {
                                    Text(
                                        text = "2-01-2024 6:40",
                                        fontSize = 8.sp,
                                        color = Color(0xFF818181)
                                    )
                                    Spacer(modifier = Modifier.height(4.dep))

                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .background(
                                                    if (it.trackorderstatus == "Received") {
                                                        Color(0xFF2DB87C)
                                                    } else {
                                                        Color(0xFFD2D0D0)
                                                    }, CircleShape
                                                )
                                                .size(16.dep)
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(4.dep))
                                    Text(
                                        text = "Received",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color.Black
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(20.dep))
                        }
                    }
                }
            }
        }
    }
}


data class DistributorOrder(
    val order: String,
    val item: String,
    val name: String,
    val date: String,
    val status: String,
    val address: String,
    val trackorderstatus: String,
    val tracker: List<TrackerItem> // Add this field
)

data class TrackerItem(
    val trackdate: String,
    val trackstatus: String
)

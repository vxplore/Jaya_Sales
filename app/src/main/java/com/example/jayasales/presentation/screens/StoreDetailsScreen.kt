package com.example.jayasales.presentation.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
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
import androidx.compose.ui.text.style.TextAlign
import com.debduttapanda.j3lib.NotificationService
import com.debduttapanda.j3lib.dep
import com.debduttapanda.j3lib.listState
import com.debduttapanda.j3lib.rememberNotifier
import com.debduttapanda.j3lib.rememberTState
import com.debduttapanda.j3lib.sep
import com.example.jayasales.MyDataIds
import com.example.jayasales.R
import com.example.jayasales.model.StoreDataResponse
import com.example.jayasales.presentation.viewmodels.PartiesTab
import com.example.jayasales.presentation.viewmodels.TransactionTab

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoreDetailsScreen(
    notifier: NotificationService = rememberNotifier(),
    selectedTransactionTab: State<TransactionTab> = rememberTState(id = MyDataIds.selectedTransactionTab),
    storeDetailsList: SnapshotStateList<StoreDataResponse> = listState(key = MyDataIds.storeDetailsList)
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Ram Krishna Store",
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
                .background(Color(0xFFFFEB56))
                .fillMaxSize()
        ) {
            Divider(
                thickness = 0.4.dep
            )
            Spacer(modifier = Modifier.height(12.dep))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(horizontal = 20.dep)
                    .fillMaxWidth()
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "₹42,568.00",
                            fontSize = 20.sep,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF222222)
                        )
                        Spacer(modifier = Modifier.width(4.dep))
                        Text(
                            text = "Dues",
                            fontSize = 13.sep,
                            color = Color(0xFF222222)
                        )
                    }
                    Text(
                        text = "#93SARJAPUR ROAR",
                        fontSize = 11.sep,
                        color = Color(0xFF222222)
                    )
                }
                IconButton(
                    onClick = { },
                    modifier = Modifier
                        .width(20.dep)
                        .height(20.dep)
                        .background(Color(0xFF222222), CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Call,
                        contentDescription = "",
                        tint = Color.White,
                    )
                }
                IconButton(
                    onClick = { },
                    modifier = Modifier
                        .width(20.dep)
                        .height(20.dep)
                        .background(Color(0xFF222222), CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Filled.LocationOn,
                        contentDescription = "",
                        tint = Color.White
                    )
                }
                IconButton(
                    onClick = { },
                    modifier = Modifier
                        .width(20.dep)
                        .height(20.dep)
                        .background(Color(0xFF222222), CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "",
                        tint = Color.White
                    )
                }
            }
            Spacer(modifier = Modifier.height(28.dep))
            Box(
                modifier = Modifier
                    .background(Color.White, RoundedCornerShape(topStart = 20.dep, topEnd = 20.dep))
                    .fillMaxSize()
                    .padding(horizontal = 20.dep)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Row(
                        modifier = Modifier
                            .padding(top = 28.dep)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .weight(.25f)
                        ) {
                            IconButton(
                                onClick = {
                                    //notifier.notify(MyDataIds.orders)
                                },
                                modifier = Modifier
                                    .width(72.dep)
                                    .height(72.dep)
                                    .shadow(4.dep, shape = RoundedCornerShape(50.dep), clip = true)
                                    .background(Color.White)
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.mark),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .width(40.dep)
                                        .height(32.dep)
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dep))
                            Text(
                                text = stringResource(id = R.string.MarkVisit),
                                fontSize = 12.sep,
                                textAlign = TextAlign.Center,
                                color = Color(0xFF222222)
                            )
                        }
                        Spacer(modifier = Modifier.width(20.dep))
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .weight(.25f)
                        ) {
                            IconButton(
                                onClick = {
                                    notifier.notify(MyDataIds.newOrders)
                                },
                                modifier = Modifier
                                    .width(72.dep)
                                    .height(72.dep)
                                    .shadow(4.dep, shape = RoundedCornerShape(50.dep), clip = true)
                                    .background(Color.White)
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.new_order),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .width(40.dep)
                                        .height(32.dep)
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dep))
                            Text(
                                text = stringResource(id = R.string.NewOrder),
                                fontSize = 12.sep,
                                textAlign = TextAlign.Center,
                                color = Color(0xFF222222)
                            )
                        }
                        Spacer(modifier = Modifier.width(20.dep))
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .weight(.25f)
                        ) {
                            IconButton(
                                onClick = {
                                    notifier.notify(MyDataIds.paymentIn)
                                },
                                modifier = Modifier
                                    .width(72.dep)
                                    .height(72.dep)
                                    .shadow(4.dep, shape = RoundedCornerShape(50.dep), clip = true)
                                    .background(Color.White)
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.payment),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .width(40.dep)
                                        .height(32.dep)
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dep))
                            Text(
                                text = stringResource(id = R.string.PaymentIn),
                                fontSize = 12.sep,
                                textAlign = TextAlign.Center,
                                color = Color(0xFF222222)
                            )
                        }

                    }
                    Spacer(modifier = Modifier.height(20.dep))
                    Divider(
                        thickness = .5.dep
                    )
                    Spacer(modifier = Modifier.height(28.dep))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(id = R.string.Past_Transaction),
                            fontSize = 16.sep,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF222222)
                        )
                        TextButton(
                            onClick = { },
                        ) {
                            Text(
                                text = stringResource(id = R.string.View_all),
                                fontSize = 13.sep,
                                color = Color(0xFFF22E4F)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dep))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        OutlinedButton(
                            onClick = { notifier.notify(MyDataIds.saleBtn, "Sales") },
                            colors = if (selectedTransactionTab.value == TransactionTab.Sales) ButtonDefaults.buttonColors(
                                Color(0XFF1FB574)
                            )
                            else ButtonDefaults.buttonColors(Color(0XFFF3FBF8)),
                            border = BorderStroke(0.dep, Color.Transparent)
                        ) {
                            Text(
                                text = "Sales",
                                color = if (selectedTransactionTab.value == TransactionTab.Sales) Color.White else Color.Black
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dep))

                        OutlinedButton(
                            onClick = { notifier.notify(MyDataIds.paymentBtn, "Payments") },
                            colors = if (selectedTransactionTab.value == TransactionTab.Payments) ButtonDefaults.buttonColors(
                                Color(0XFF1FB574)
                            )
                            else ButtonDefaults.buttonColors(Color(0XFFF3FBF8)),
                            border = BorderStroke(0.dep, Color.Transparent)
                        ) {
                            Text(
                                text = "Payments",
                                color = if (selectedTransactionTab.value == TransactionTab.Payments) Color.White else Color.Black
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dep))
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentPadding = PaddingValues(vertical = 10.dep),
                        verticalArrangement = Arrangement.spacedBy(20.dep)
                    ) {
                        items(storeDetailsList) {
                            if (selectedTransactionTab.value == TransactionTab.Sales) {
                                Card(
                                    modifier = Modifier
                                        .height(112.dep)
                                        .fillMaxWidth()
                                        .shadow(
                                            2.dep,
                                            RoundedCornerShape(8.dep),
                                            clip = true,
                                            DefaultShadowColor
                                        )
                                        .clip(RoundedCornerShape(8.dep))
                                        .clickable {
                                            //notifier.notify(MyDataIds.storeDetails)
                                        },
                                    colors = CardDefaults.cardColors(Color.White),
                                    elevation = CardDefaults.cardElevation(
                                        defaultElevation = 8.dep,
                                        focusedElevation = 10.dep,
                                    ),
                                    shape = RoundedCornerShape(8.dep),
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .padding(start = 16.dep, end = 14.dep)
                                            .fillMaxSize()
                                    ) {
                                        Spacer(modifier = Modifier.height(12.dep))
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            modifier = Modifier
                                                //.padding(top = 8.dep)
                                                .fillMaxWidth()
                                        ) {
                                            Text(
                                                text = "Order: #${it.order_id}",
                                                fontSize = 14.sep,
                                                textAlign = TextAlign.Center,
                                                fontWeight = FontWeight.SemiBold,
                                                color = Color(0xFF222222)
                                            )
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                            ) {
                                                Row(
                                                    modifier = Modifier
                                                        .background(Color(0xFFFFEB56), CircleShape)
                                                        .size(8.dep)
                                                ) {
                                                }
                                                Spacer(modifier = Modifier.width(12.dep))
                                                Text(
                                                    text = it.order_status,
                                                    fontSize = 12.sep,
                                                    textAlign = TextAlign.Center,
                                                    color = Color(0xFF222222)
                                                )
                                            }
                                        }
                                        Spacer(modifier = Modifier.height(8.dep))
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                        ) {
                                            Row {
                                                Image(
                                                    painter = painterResource(id = R.drawable.shop),
                                                    contentDescription = "",
                                                    modifier = Modifier
                                                        .width(16.dep)
                                                        .height(12.dep)
                                                )
                                                Spacer(modifier = Modifier.width(4.dep))
                                                Text(
                                                    text = it.store_name,
                                                    fontSize = 10.sep,
                                                    textAlign = TextAlign.Center,
                                                    color = Color(0xFF222222)
                                                )
                                            }
                                            Text(
                                                text = "₹${it.amount}",
                                                fontSize = 14.sep,
                                                textAlign = TextAlign.Center,
                                                fontWeight = FontWeight.SemiBold,
                                                color = Color(0xFF222222)
                                            )
                                        }
                                        Spacer(modifier = Modifier.height(8.dep))
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                        ) {
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Image(
                                                    painter = painterResource(id = R.drawable.date),
                                                    contentDescription = "",
                                                    modifier = Modifier
                                                        .width(16.dep)
                                                        .height(12.dep)
                                                )
                                                Spacer(modifier = Modifier.width(4.dep))
                                                Text(
                                                    text = it.date,
                                                    fontSize = 10.sep,
                                                    textAlign = TextAlign.Center,
                                                    color = Color(0xFF222222)
                                                )
                                                Spacer(modifier = Modifier.width(8.dep))
                                                Image(
                                                    painter = painterResource(id = R.drawable.watch),
                                                    contentDescription = "",
                                                    modifier = Modifier
                                                        .width(12.dep)
                                                        .height(12.dep)
                                                )
                                                Spacer(modifier = Modifier.width(4.dep))
                                                Text(
                                                    text = it.time,
                                                    fontSize = 10.sep,
                                                    textAlign = TextAlign.Center,
                                                    color = Color(0xFF222222)
                                                )
                                            }
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Center,
                                                modifier = Modifier
                                                    .background(
                                                        if (it.transaction_status == "Paid") {
                                                            Color(0xFFCDFFE9)
                                                        } else {
                                                            Color(0xFFFFCFCF)
                                                        },
                                                        RoundedCornerShape(12.dep)
                                                    )
                                                    .border(
                                                        .5.dep,
                                                        if (it.transaction_status == "Paid") {
                                                            Color(0xFF1FB574)
                                                        } else {
                                                            Color(0xFFD62B2B)
                                                        },
                                                        RoundedCornerShape(12.dep)
                                                    )
                                                    .padding(horizontal = 12.dep)
                                                    .padding(vertical = 4.dep)
                                            ) {
                                                Text(
                                                    text = it.transaction_status,
                                                    fontSize = 10.sep,
                                                    textAlign = TextAlign.Center,
                                                    color = if (it.transaction_status == "Paid") {
                                                        Color(0xFF1FB574)
                                                    } else {
                                                        Color(0xFFD62B2B)
                                                    },
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                            else {
                                Card(
                                    modifier = Modifier
                                        .height(84.dep)
                                        .fillMaxWidth()
                                        .shadow(
                                            2.dep,
                                            RoundedCornerShape(8.dep),
                                            clip = true,
                                            DefaultShadowColor
                                        )
                                        .clip(RoundedCornerShape(8.dep))
                                        .clickable {
                                            //notifier.notify(MyDataIds.storeDetails)
                                        },
                                    colors = CardDefaults.cardColors(Color.White),
                                    elevation = CardDefaults.cardElevation(
                                        defaultElevation = 8.dep,
                                        focusedElevation = 10.dep,
                                    ),
                                    shape = RoundedCornerShape(8.dep),
                                ) {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier
                                            .padding(start = 16.dep, end = 14.dep)
                                            .fillMaxSize()
                                    ) {
                                        Spacer(modifier = Modifier.height(12.dep))
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            modifier = Modifier
                                                //.padding(top = 8.dep)
                                                .fillMaxWidth()
                                        ) {
                                            Text(
                                                text = "Payment",
                                                fontSize = 14.sep,
                                                textAlign = TextAlign.Center,
                                                fontWeight = FontWeight.SemiBold,
                                                color = Color(0xFF222222)
                                            )
                                            Text(
                                                text = "₹${it.amount}",
                                                fontSize = 14.sep,
                                                textAlign = TextAlign.Center,
                                                fontWeight = FontWeight.SemiBold,
                                                color = Color(0xFF222222)
                                            )
                                        }
                                        Spacer(modifier = Modifier.height(8.dep))
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                        ) {
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Image(
                                                    painter = painterResource(id = R.drawable.date),
                                                    contentDescription = "",
                                                    modifier = Modifier
                                                        .width(16.dep)
                                                        .height(12.dep)
                                                )
                                                Spacer(modifier = Modifier.width(4.dep))
                                                Text(
                                                    text = it.date,
                                                    fontSize = 10.sep,
                                                    textAlign = TextAlign.Center,
                                                    color = Color(0xFF222222)
                                                )
                                                Spacer(modifier = Modifier.width(8.dep))
                                                Image(
                                                    painter = painterResource(id = R.drawable.watch),
                                                    contentDescription = "",
                                                    modifier = Modifier
                                                        .width(12.dep)
                                                        .height(12.dep)
                                                )
                                                Spacer(modifier = Modifier.width(4.dep))
                                                Text(
                                                    text = it.time,
                                                    fontSize = 10.sep,
                                                    textAlign = TextAlign.Center,
                                                    color = Color(0xFF222222)
                                                )
                                            }
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Center,
                                                modifier = Modifier
                                                    .background(
                                                        Color(0xFFCDFFE9),
                                                        RoundedCornerShape(12.dep)
                                                    )
                                                    .border(
                                                        .5.dep,
                                                        Color(0xFF1FB574),
                                                        RoundedCornerShape(12.dep)
                                                    )
                                                    .padding(horizontal = 12.dep)
                                                    .padding(vertical = 4.dep)
                                            ) {
                                                Text(
                                                    text = it.payment_mode,
                                                    fontSize = 10.sep,
                                                    textAlign = TextAlign.Center,
                                                    color = Color(0xFF1FB574),
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
    }
}
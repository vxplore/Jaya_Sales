package com.example.jayasales.presentation.screens

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.debduttapanda.j3lib.NotificationService
import com.debduttapanda.j3lib.dep
import com.debduttapanda.j3lib.rememberNotifier
import com.debduttapanda.j3lib.sep
import com.example.jayasales.MyDataIds
import com.example.jayasales.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoreDetailsScreen(
    notifier: NotificationService = rememberNotifier()
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
            Box (
                modifier = Modifier
                    .background(Color.White, RoundedCornerShape(topStart = 20.dep, topEnd = 20.dep))
                    .fillMaxSize()
                    .padding(horizontal = 20.dep)
            ){
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
                                painter = painterResource(id = R.drawable.parties),
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
                                //notifier.notify(MyDataIds.orders)
                            },
                            modifier = Modifier
                                .width(72.dep)
                                .height(72.dep)
                                .shadow(4.dep, shape = RoundedCornerShape(50.dep), clip = true)
                                .background(Color.White)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.items),
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
                                //notifier.notify(MyDataIds.orders)
                            },
                            modifier = Modifier
                                .width(72.dep)
                                .height(72.dep)
                                .shadow(4.dep, shape = RoundedCornerShape(50.dep), clip = true)
                                .background(Color.White)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.transactions),
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
            }
        }
    }
}
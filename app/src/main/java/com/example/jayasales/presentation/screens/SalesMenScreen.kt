package com.example.jayasales.presentation.screens

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.DefaultShadowColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.core.content.PermissionChecker
import com.debduttapanda.j3lib.NotificationService
import com.debduttapanda.j3lib.boolState
import com.debduttapanda.j3lib.dep
import com.debduttapanda.j3lib.listState
import com.debduttapanda.j3lib.rememberNotifier
import com.debduttapanda.j3lib.sep
import com.debduttapanda.j3lib.stringState
import com.example.jayasales.MyDataIds
import com.example.jayasales.R
import com.example.jayasales.model.SalesManDatum

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SalesMenScreen(
    notifier: NotificationService = rememberNotifier(),
    salesmen: SnapshotStateList<SalesManDatum> = listState(key = MyDataIds.salesmen),
    loadingState: State<Boolean> = boolState(key = MyDataIds.loadingState),
    phoneNumber: State<String> = stringState(key = MyDataIds.phoneNumberState),
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Sales Men",
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
                .padding(horizontal = 20.dep)
                .padding(bottom = 12.dep)
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(16.dep))
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
            }
            else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentPadding = PaddingValues(vertical = 10.dep),
                    verticalArrangement = Arrangement.spacedBy(20.dep)
                ) {
                    itemsIndexed(salesmen) { index, it ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .shadow(
                                    4.dep,
                                    RoundedCornerShape(4.dep),
                                    clip = true,
                                    DefaultShadowColor
                                )
                                .clip(RoundedCornerShape(4.dep))
                                .clickable {
                                    notifier.notify(MyDataIds.timeline,index)
                                },
                            colors = CardDefaults.cardColors(Color.White),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 8.dep,
                                focusedElevation = 10.dep,
                            ),
                            shape = RoundedCornerShape(4.dep),
                        ) {
                            Spacer(modifier = Modifier.height(8.dep))
                            Row(
                                modifier = Modifier
                                    .padding(horizontal = 16.dep)
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.Person,
                                        contentDescription = "ArrowBackIosNew",
                                        tint = Color(0xFF362C2C),
                                        modifier = Modifier
                                            .size(20.dep)
                                    )
                                    Spacer(modifier = Modifier.width(4.dep))
                                    Text(
                                        text = it.name,
                                        fontSize = 14.sep,
                                        color = Color(0xFF2B2B2B),
                                        fontWeight = FontWeight.Bold,
                                    )
                                }
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    IconButton(
                                        onClick = {
                                            notifier.notify(MyDataIds.openMap,index)
                                        },
                                        modifier = Modifier
                                            .background(Color(0xFDE4B5B5), CircleShape)
                                            .size(28.dep)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.Send,
                                            contentDescription = "ArrowBackIosNew",
                                            tint = Color(0xFFD62B2B),
                                            modifier = Modifier
                                                .size(16.dep)
                                        )
                                    }
                                    val context = LocalContext.current
                                    val requestPermissionLauncher =
                                        rememberLauncherForActivityResult(
                                            ActivityResultContracts.RequestPermission()
                                        ) { isGranted: Boolean ->
                                            if (isGranted) {
                                                makePhoneCall(context, phoneNumber)
                                            }
                                        }
                                    Spacer(modifier = Modifier.width(4.dep))
                                    IconButton(
                                        onClick = {

                                            if (phoneNumber.value.isNotEmpty()) {
                                                if (PermissionChecker.checkSelfPermission(
                                                        context,
                                                        Manifest.permission.CALL_PHONE
                                                    ) == PermissionChecker.PERMISSION_GRANTED
                                                ) {
                                                    makePhoneCall(context, phoneNumber)
                                                } else {
                                                    requestPermissionLauncher.launch(
                                                        Manifest.permission.CALL_PHONE
                                                    )
                                                }
                                            } else {
                                                Log.e("SalesMenViewModel", "Phone number is empty!")
                                            }
                                        },
                                        modifier = Modifier
                                            .background(Color(0xF2ECF7D7), CircleShape)
                                            .size(28.dep)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.Call,
                                            contentDescription = "ArrowBackIosNew",
                                            tint = Color(0xFF27DB81),
                                            modifier = Modifier
                                                .size(16.dep)
                                        )
                                    }

                                }
                            }
                            Spacer(modifier = Modifier.height(8.dep))
                            Row(
                                modifier = Modifier
                                    .padding(horizontal = 16.dep)
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {

                                Text(
                                    text = "Need to Start",
                                    fontSize = 12.sep,
                                    color = Color.Black,
                                )
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = "Todays Order",
                                        fontSize = 12.sep,
                                        color = Color.Black,
                                    )
                                    Spacer(modifier = Modifier.width(4.dep))
                                    Text(
                                        text = it.today_order,
                                        fontSize = 16.sep,
                                        color = Color.Black,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dep))
                            Text(
                                text = it.status,
                                fontSize = 12.sep,
                                color = Color.Black,
                                modifier = Modifier
                                    .padding(horizontal = 16.dep)
                            )
                            Spacer(modifier = Modifier.height(4.dep))
                        }
                    }
                }
            }
        }
    }
}

private fun makePhoneCall(
    context: Context,
    phoneNumber: State<String>
) {
    val phoneNumberUri = "tel:${phoneNumber.value}"
    Log.d("dcfdc", "$phoneNumber")
    val callIntent = Intent(
        Intent.ACTION_DIAL,
        Uri.parse(phoneNumberUri)
    )
    context.startActivity(callIntent)
}



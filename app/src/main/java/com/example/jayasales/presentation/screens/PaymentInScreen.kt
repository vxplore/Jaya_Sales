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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.DefaultShadowColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.debduttapanda.j3lib.NotificationService
import com.debduttapanda.j3lib.boolState
import com.debduttapanda.j3lib.dep
import com.debduttapanda.j3lib.listState
import com.debduttapanda.j3lib.rememberNotifier
import com.debduttapanda.j3lib.rememberTState
import com.debduttapanda.j3lib.sep
import com.debduttapanda.j3lib.stringState
import com.example.jayasales.MyDataIds
import com.example.jayasales.R
import com.example.jayasales.model.PaymentIn
import com.example.jayasales.presentation.viewmodels.PaymentModeTab

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentInScreen(
    notifier: NotificationService = rememberNotifier(),
    paymentInList: SnapshotStateList<PaymentIn> = listState(key = MyDataIds.paymentInList),
    selectedPaymentMode: State<PaymentModeTab> = rememberTState(id = MyDataIds.selectedPaymentMode),
    instruction: State<String> = stringState(key = MyDataIds.instruction),
    paymentInDialog: Boolean = boolState(key = MyDataIds.paymentInDialog).value,
) {
    val openDialog = remember { mutableStateOf(false) }
    if (paymentInDialog) {
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = {
                Text(
                    text = stringResource(id = R.string.Payment_received_successfully),
                    fontSize = 16.sep,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF222222)
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        notifier.notify(MyDataIds.paymentDone)
                    },
                    modifier = Modifier
                        .padding(horizontal = 52.dep)
                        .height(44.dep)
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(Color(0xFFF22E4F)),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 8.dep,
                        pressedElevation = 10.dep
                    ),
                    shape = RoundedCornerShape(4.dep)
                ) {
                    Text(
                        text = stringResource(id = R.string.Back_Parties),
                        fontSize = 14.sep,
                        color = Color.White,
                    )
                }
            },
            icon = {
                Image(
                    painter = painterResource(id = R.drawable.tick_mark),
                    contentDescription = stringResource(id = R.string.Payment_received_successfully),
                    modifier = Modifier
                        .padding(start = 14.dep)
                        .height(72.dp)
                        .width(72.dp),
                )
            },
            shape = RoundedCornerShape(4.dep)
        )
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.Payment_In),
                        fontSize = 20.sep,
                        fontWeight = FontWeight.Bold,
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            notifier.notify(MyDataIds.back)
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
        ) {
            Spacer(modifier = Modifier.height(24.dep))
            LazyColumn(
                modifier = Modifier
                    .height(360.dep)
                    .fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 10.dep),
                verticalArrangement = Arrangement.spacedBy(20.dep)
            ) {
                itemsIndexed(paymentInList) {index,it->
                    var comment by remember { mutableStateOf("") }
                    Card(
                        modifier = Modifier
                            .padding(horizontal = 20.dep)
                            .wrapContentHeight()
                            .fillMaxWidth()
                            .shadow(
                                2.dep,
                                RoundedCornerShape(8.dep),
                                clip = true,
                                DefaultShadowColor
                            )
                            .clip(RoundedCornerShape(8.dep))
                            .clickable {
                            },
                        colors = CardDefaults.cardColors(Color.White),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 8.dep,
                            focusedElevation = 10.dep,
                        ),
                        shape = RoundedCornerShape(8.dep),
                    ) {
                        Spacer(modifier = Modifier.height(8.dep))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .padding(horizontal = 16.dep)
                                .fillMaxWidth()
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Invoice #",
                                    fontSize = 14.sep,
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF222222)
                                )

                                Text(
                                    text = it.id,
                                    fontSize = 10.sep,
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF222222)
                                )
                            }


                            Text(
                                text = "₹${it.total}",
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
                                .padding(horizontal = 16.dep)
                                .fillMaxWidth()
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.DateRange,
                                    contentDescription = "Date",
                                    tint = Color(0xFFD62B2B),
                                    modifier = Modifier
                                        .width(16.dep)
                                        .height(16.dep)
                                )
                                Spacer(modifier = Modifier.width(4.dep))
                                Text(
                                    text = it.date,
                                    fontSize = 12.sep,
                                    textAlign = TextAlign.Center,
                                    color = Color(0xFF222222)
                                )
                            }
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier
                                    .background(
                                        if (!it.payment_start) {
                                            Color(0xFFFFEAEA)
                                        } else {
                                            Color(0xFFFFFDEB)
                                        },
                                        RoundedCornerShape(12.dep)
                                    )
                                    .border(
                                        .5.dep,
                                        if (!it.payment_start) {
                                            Color(0xFFD62B2B)
                                        } else {
                                            Color(0xFFFAC800)
                                        },
                                        RoundedCornerShape(12.dep)
                                    )
                                    .padding(horizontal = 12.dep)
                                    .padding(vertical = 4.dep)
                            ) {
                                Text(
                                    text = "${it.due_amount} ${it.status}",
                                    fontSize = 12.sep,
                                    textAlign = TextAlign.Center,
                                    color = if (!it.payment_start) {
                                        Color(0xFFD62B2B)
                                    } else {
                                        Color(0xFFFAC800)
                                    },
                                )
                            }
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .padding(horizontal = 16.dep)
                                .fillMaxWidth()
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .padding(bottom = 16.dep)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AccessTime,
                                    contentDescription = "Time",
                                    tint = Color(0xFFD62B2B),
                                    modifier = Modifier
                                        .width(16.dep)
                                        .height(16.dep)
                                )
                                Spacer(modifier = Modifier.width(4.dep))
                                Text(
                                    text = it.time,
                                    fontSize = 12.sep,
                                    textAlign = TextAlign.Center,
                                    color = Color(0xFF222222)
                                )
                            }
                            TextField(
                                value = comment,
                                onValueChange = {
                                    comment = it
                                    notifier.notify(MyDataIds.index,index)
                                    notifier.notify(MyDataIds.comment, it)
                                },
                                modifier = Modifier
                                    .padding(bottom = 12.dep, top = 8.dep)
                                    .border(.5.dep, Color(0xFFDDDDDD))
                                    .width(80.dep),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number,
                                    imeAction = ImeAction.Done
                                ),
                                textStyle = MaterialTheme.typography.bodyMedium.copy(
                                    fontSize = 12.sep
                                ),
                                placeholder = {
                                    Text(
                                        stringResource(id = R.string.amount),
                                        color = Color.LightGray,
                                        fontSize = 11.sep
                                    )
                                },
                                singleLine = true,
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = Color.White,
                                    unfocusedContainerColor = Color.White,
                                    focusedIndicatorColor = Color(0xFFDDDDDD),
                                    unfocusedIndicatorColor = Color(0xFFDDDDDD)
                                ),
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dep))
            Divider(
                modifier = Modifier
                    .background(Color(0xFF707070))
                    .height(1.dep)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dep))
            Text(
                text = stringResource(id = R.string.Past_Transaction),
                fontSize = 16.sep,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(horizontal = 20.dep)
            )
            Spacer(modifier = Modifier.height(12.dep))
            Row(
                modifier = Modifier
                    .padding(horizontal = 20.dep)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                OutlinedButton(
                    onClick = { notifier.notify(MyDataIds.cashbtn, "cash") },
                    colors = if (selectedPaymentMode.value == PaymentModeTab.Cash) ButtonDefaults.buttonColors(
                        Color(0XFF1FB574)
                    )
                    else ButtonDefaults.buttonColors(Color(0XFFF3FBF8)),
                    border = BorderStroke(0.dep, Color.Transparent)
                ) {
                    Text(
                        text = "cash",
                        color = if (selectedPaymentMode.value == PaymentModeTab.Cash) Color.White else Color.Black
                    )
                }
                Spacer(modifier = Modifier.width(12.dep))
                OutlinedButton(
                    onClick = { notifier.notify(MyDataIds.chequebtn, "cheque") },
                    colors = if (selectedPaymentMode.value == PaymentModeTab.Cheque) ButtonDefaults.buttonColors(
                        Color(0XFF1FB574)
                    )
                    else ButtonDefaults.buttonColors(Color(0XFFF3FBF8)),
                    border = BorderStroke(0.dep, Color.Transparent)
                ) {
                    Text(
                        text = "cheque",
                        color = if (selectedPaymentMode.value == PaymentModeTab.Cheque) Color.White else Color.Black
                    )
                }
                OutlinedButton(
                    onClick = { notifier.notify(MyDataIds.onlinebtn, "online") },
                    colors = if (selectedPaymentMode.value == PaymentModeTab.Online) ButtonDefaults.buttonColors(
                        Color(0XFF1FB574)
                    )
                    else ButtonDefaults.buttonColors(Color(0XFFF3FBF8)),
                    border = BorderStroke(0.dep, Color.Transparent)
                ) {
                    Text(
                        text = "online",
                        color = if (selectedPaymentMode.value == PaymentModeTab.Online) Color.White else Color.Black
                    )
                }
            }
            Spacer(modifier = Modifier.height(14.dep))
            Divider(
                modifier = Modifier
                    .background(Color(0xFF707070))
                    .height(1.dep)
                    .fillMaxWidth()
            )
            OutlinedTextField(
                value = instruction.value,
                onValueChange = {
                    notifier.notify(MyDataIds.instruction, it)
                },
                maxLines = 1,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                leadingIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.edit),
                        contentDescription = stringResource(id = R.string.edit),
                        modifier = Modifier
                            .padding(start = 14.dep)
                            .height(20.dp)
                            .width(20.dp),
                    )
                },
                placeholder = {
                    Text(
                        stringResource(id = R.string.Any_Instructions),
                        color = Color(0xFF222222),
                        fontSize = 14.sep
                    )
                },
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxWidth(),
                textStyle = TextStyle(
                    color = Color(0xFF222222),
                    fontSize = 14.sep
                ),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.White,
                    unfocusedIndicatorColor = Color.White,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                ),
            )
            Spacer(modifier = Modifier.height(4.dep))
            Divider(
                modifier = Modifier
                    .background(Color(0xFF707070))
                    .height(1.dep)
                    .fillMaxWidth()
            )
            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .height(68.dep)
                ) {
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth(),
                        thickness = 2.dep
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .padding(horizontal = 20.dep)
                            .fillMaxSize()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "",
                                fontSize = 15.sep,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF222222)
                            )
                        }
                        Button(
                            onClick = {
                                notifier.notify(MyDataIds.savebtn)
                            },
                            modifier = Modifier
                                .height(44.dep)
                                .width(160.dep),
                            colors = ButtonDefaults.buttonColors(Color(0xFFFFEB56)),
                            elevation = ButtonDefaults.buttonElevation(
                                defaultElevation = 8.dep,
                                pressedElevation = 10.dep
                            ),
                            shape = RoundedCornerShape(4.dep)
                        ) {
                            Text(
                                text = stringResource(id = R.string.Save),
                                fontSize = 16.sep,
                                color = Color(0xFF222222)
                            )
                        }
                    }
                }
            }
        }
    }
}

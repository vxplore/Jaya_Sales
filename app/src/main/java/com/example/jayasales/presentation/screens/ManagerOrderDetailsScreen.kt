package com.example.jayasales.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.AutoDelete
import androidx.compose.material.icons.filled.BorderColor
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.debduttapanda.j3lib.NotificationService
import com.debduttapanda.j3lib.boolState
import com.debduttapanda.j3lib.dep
import com.debduttapanda.j3lib.listState
import com.debduttapanda.j3lib.rememberNotifier
import com.debduttapanda.j3lib.sep
import com.debduttapanda.j3lib.stringState
import com.example.jayasales.MyDataIds
import com.example.jayasales.R
import com.example.jayasales.model.DistributorDatum
import com.example.jayasales.model.DistributorDetailsProduct

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManagerOrderDetailsScreen(
    notifier: NotificationService = rememberNotifier(),
    profilename: State<String> = stringState(key = MyDataIds.profilename),
    orderState: State<String> = stringState(key = MyDataIds.orderState),
    statusState: State<String> = stringState(key = MyDataIds.statusState),
    itemState: State<String> = stringState(key = MyDataIds.itemState),
    namesState: State<String> = stringState(key = MyDataIds.namesState),
    dateState: State<String> = stringState(key = MyDataIds.dateState),
    locationState: State<String> = stringState(key = MyDataIds.locationState),
    stateState: State<String> = stringState(key = MyDataIds.stateState),
    pinState: State<String> = stringState(key = MyDataIds.pinState),
    orderDetails: List<DistributorDetailsProduct> = listState(key = MyDataIds.orderDetails),
    loadingState: State<Boolean> = boolState(key = MyDataIds.loadingState),
) {
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
        var showBottomSheetState by remember { mutableStateOf(false) }
        var isBottomSheetLaidOut by remember { mutableStateOf(false) }
        val sheetState = rememberModalBottomSheetState()
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "Order:#${orderState.value}",
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
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        notifier.notify(MyDataIds.addProduct)
                    },
                    //contentColor = Color.White,
                    elevation = FloatingActionButtonDefaults.elevation(8.dep),
                    shape = CircleShape,
                    containerColor = Color(0xFFD62B2B),
                    modifier = Modifier
                        .padding(bottom = 60.dep)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Add",
                        tint = Color.White
                    )
                }
            }
        )
        {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .background(Color(0xFF2DB87C))
                        .fillMaxWidth()
                    //.padding(horizontal = 16.dep)
                ) {
                    Spacer(modifier = Modifier.height(12.dep))
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
                                text = "Order:#${orderState.value}",
                                fontSize = 13.sep,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White,
                                modifier = Modifier
                            )
                            Spacer(modifier = Modifier.width(6.dep))
                            Divider(
                                modifier = Modifier
                                    .height(22.dep)
                                    .width(1.5.dep),
                                color = Color.White,
                            )
                            Spacer(modifier = Modifier.width(6.dep))
                            Text(
                                text = statusState.value,
                                fontSize = 13.sep,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White,
                                modifier = Modifier
                            )
                        }
                        Text(
                            text = "Items:${itemState.value}",
                            fontSize = 13.sep,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White,
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
                                tint = Color.White,
                            )
                            Spacer(modifier = Modifier.width(4.dep))
                            Text(
                                text = namesState.value,
                                fontSize = 15.sep,
                                //fontWeight = FontWeight.SemiBold,
                                color = Color.White,
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
                                tint = Color.White,
                                modifier = Modifier
                                    .size(16.dep)
                            )
                            Spacer(modifier = Modifier.width(4.dep))
                            Text(
                                text = dateState.value,
                                fontSize = 12.sep,
                                //fontWeight = FontWeight.SemiBold,
                                color = Color.White,
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
                            tint = Color.White,
                            modifier = Modifier.size(20.dep)
                        )
                        Spacer(modifier = Modifier.width(4.dep))
                        Text(
                            text = "${locationState.value},${stateState.value},${pinState.value}",
                            fontSize = 14.sep,
                            color = Color.White,
                            modifier = Modifier
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dep))
                }
                Spacer(modifier = Modifier.height(12.dep))
                Text(
                    text = "Order Details",
                    fontSize = 18.sep,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier
                        .padding(horizontal = 16.dep)
                )
                Spacer(modifier = Modifier.height(12.dep))
                Divider()
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentPadding = PaddingValues(top = 8.dep, bottom = 140.dep),
                    verticalArrangement = Arrangement.spacedBy(8.dep)
                ) {
                    itemsIndexed(orderDetails) { index, it ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .padding(horizontal = 16.dep)
                                .fillMaxWidth()
                        ) {
                            Column {
                                Text(
                                    text = "#${it.code}",
                                    fontSize = 12.sep,
                                    color = Color(0xFFA4A4A4),
                                    modifier = Modifier
                                    //.padding(horizontal = 16.dep)
                                )
                                Spacer(modifier = Modifier.height(4.dep))
                                Text(
                                    text = it.name,
                                    fontSize = 14.sep,
                                    color = Color.Black,
                                    modifier = Modifier
                                    //.padding(horizontal = 16.dep)
                                )
                                Spacer(modifier = Modifier.height(4.dep))
                                Text(
                                    text = "${it.weight}/${it.pcs}",
                                    fontSize = 12.sep,
                                    color = Color(0xFFA4A4A4),
                                    modifier = Modifier
                                    //.padding(horizontal = 16.dep)
                                )
                            }
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Row(
                                    modifier = Modifier
                                        .background(Color(0xFFF2F2F2))
                                ) {
                                    Text(
                                        text = it.quantity,
                                        fontSize = 12.sep,
                                        color = Color.Black,
                                        modifier = Modifier
                                            .padding(horizontal = 16.dep)
                                            .padding(vertical = 8.dep)
                                    )
                                }
                                IconButton(
                                    onClick = {
                                        notifier.notify(
                                           MyDataIds.edit,
                                           Pair(index, it.product_id)
                                       )
                                        showBottomSheetState = true
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.BorderColor,
                                        contentDescription = "hamburger",
                                        tint = Color(0xFF83D8E4),
                                        modifier = Modifier
                                            .size(20.dep)

                                    )

                                }
                                Spacer(modifier = Modifier.width(2.dep))

                                IconButton(
                                    onClick = {
                                          notifier.notify(
                                          MyDataIds.delete,
                                          Pair(index, it.product_id)
                                      )
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.AutoDelete,
                                        contentDescription = "hamburger",
                                        tint = Color(0xFFF86262),
                                        modifier = Modifier
                                            .size(20.dep)
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dep))
                        Divider()
                    }
                }
            }
            Box(
                modifier = Modifier
                    .padding(horizontal = 16.dep)
                    .padding(bottom = 20.dep)
                    .fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                Button(
                    onClick = {
                              notifier.notify(MyDataIds.confirmOrder)
                    },
                    modifier = Modifier
                        .height(54.dep)
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(Color(0xFF2DB87C)),
                    shape = RoundedCornerShape(4.dep)
                ) {
                    Text(
                        text = "Confirm Order",
                        fontSize = 16.sep,
                        color = Color.White
                    )
                }
            }
        }
        if (showBottomSheetState) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheetState = false
                },
                sheetState = sheetState
            ) {
                NameBottomSheetContent(
                    onClearIconClick = {
                        showBottomSheetState = false
                    }, // Provide a default empty click handler
                    onLaidOut = {
                        isBottomSheetLaidOut = true
                    },
                    onUpdateQuantity = { /* Implement the update logic here */ },
                    profilename = profilename // Pass the state
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NameBottomSheetContent(
    onClearIconClick: () -> Unit,
    onLaidOut: () -> Unit,
    onUpdateQuantity: (String) -> Unit,
    notifier: NotificationService = rememberNotifier(),
    profilename: State<String> = stringState(key = MyDataIds.profilename),
    bottomSheetVisible: MutableState<Boolean> = remember { mutableStateOf(true) },
    //qty: State<String> = stringState(key = MyDataIds.qtyState),
) {
    var visible by remember { mutableStateOf(true) }

    DisposableEffect(Unit) {
        onLaidOut()
        onDispose { }
    }

    Column(
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier
            .padding(bottom = 50.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .background(Color.White, RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(end = 8.dp)
                .fillMaxWidth()
        ) {
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(),
                exit = fadeOut(),
                modifier = Modifier
                    .clickable {
                        visible = !visible
                        onClearIconClick.invoke()
                    }
                    .padding(start = 12.dp)
                    .height(32.dp)
                    .width(32.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "",
                    modifier = Modifier
                        .height(24.dp)
                        .width(24.dp),
                    tint = Color(0xFFEB3D34)
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "Edit the quantity",
            fontSize = 12.sp,
            color = Color(0xFF2C323A),
            modifier = Modifier
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))

        OutlinedTextField(
            value = profilename.value,
            onValueChange = {
                notifier.notify(MyDataIds.profilename, it)
            },
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .imePadding()
                .fillMaxWidth(),
            textStyle = TextStyle(
                color = Color(0xFF2C323A),
                fontSize = 12.sp
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFFEB3D34),
                unfocusedBorderColor = Color(0xFF959595)
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            placeholder = {
                Text(
                    "Quantity",
                    color = Color(0xFF959595),
                    fontSize = 12.sp
                )
            }
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                bottomSheetVisible.value = false
                onClearIconClick.invoke()
                notifier.notify(MyDataIds.update)
            },
            modifier = Modifier
                .padding(horizontal = 80.dp)
                .height(54.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(Color(0xFFEB3D34)),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(
                text = "Save",
                fontSize = 16.sp,
                color = Color.White
            )
        }
    }
}

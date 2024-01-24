package com.example.jayasales.presentation.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.AlertDialog
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
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
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
import com.debduttapanda.j3lib.sep
import com.debduttapanda.j3lib.stringState
import com.example.jayasales.MyDataIds
import com.example.jayasales.R
import com.example.jayasales.model.Cart
import com.example.jayasales.model.ReviewCartDataResponse

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ReviewCartScreen(
    notifier: NotificationService = rememberNotifier(),
    reviewCartDialog: Boolean = boolState(key = MyDataIds.reviewCartDialog).value,
    reviewInstruction :State<String> = stringState(key = MyDataIds.reviewInstruction),
    reviewCart : SnapshotStateList<Cart> = listState(key = MyDataIds.reviewCart),
    taxAmountState : State<String> = stringState(key = MyDataIds.taxAmountState),
    cgstState : State<String> = stringState(key = MyDataIds.cgstState),
    gstState : State<String> = stringState(key = MyDataIds.gstState),
    totalState : State<String> = stringState(key = MyDataIds.totalState),
    totalQuantityState : State<String> = stringState(key = MyDataIds.totalQuantityState),
    reviewLoadingState : State<Boolean> = boolState(key = MyDataIds.reviewLoadingState),
    lostInternet: State<Boolean> = boolState(key = MyDataIds.lostInternet),
) {
    val openDialog = remember { mutableStateOf(false) }
    if (lostInternet.value) {
        LostInternet_ui(onDismissRequest = { notifier.notify(MyDataIds.onDissmiss) })
    }
    if (reviewCartDialog){
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = {
                Text(
                    text = stringResource(id = R.string.order_placed),
                    fontSize = 16.sep,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF222222)
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        notifier.notify(MyDataIds.orderPlaced)
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
                    Column(modifier = Modifier
                        .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Text(
                            text = stringResource(id = R.string.Review_Cart),
                            fontSize = 20.sep,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Ram Krishna Store",
                            fontSize = 10.sep,
                            fontWeight = FontWeight.Medium
                        )
                    }
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
                    .fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.height(20.dep))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dep),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(id = R.string.Items),
                        fontSize = 16.sep,
                        fontWeight = FontWeight.Medium
                    )
                    TextButton(
                        onClick = {
                            notifier.notify(MyDataIds.addItem)
                        },
                    ) {
                        Text(
                            text = stringResource(id = R.string.Add_item),
                            fontSize = 14.sep,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFFD62B2B),
                        )
                    }
                }
                if (reviewLoadingState.value) {
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
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 300.dep),
                    ) {
                        itemsIndexed(reviewCart) {index,it->
                            Divider(
                                modifier = Modifier.height(0.3.dep),
                                color = Color(0XFFADA9A9)
                            )
                            Spacer(modifier = Modifier.height(5.dep))
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 30.dep),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(horizontalAlignment = Alignment.Start) {
                                    Text(
                                        text = it.product.name,
                                        fontSize = 14.sep
                                    )
                                    Spacer(modifier = Modifier.height(10.dep))
                                    Text(
                                        text = it.product.pcs,
                                        fontSize = 12.sep,
                                        fontWeight = FontWeight.Medium
                                    )
                                    TextButton(
                                        onClick = {
                                            notifier.notify(MyDataIds.remove,index)
                                        },
                                        modifier = Modifier
                                    ) {
                                        Text(
                                            text = stringResource(id = R.string.REMOVE),
                                            fontSize = 10.sep,
                                            color = Color(0xFF222222)
                                        )
                                    }
                                }
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    OutlinedCard(
                                        shape = RoundedCornerShape(2.dep),
                                        modifier = Modifier.width(73.dep),
                                        border = BorderStroke(1.dep, color = Color(0XFFD3D3D3))
                                    ) {
                                        Text(
                                            text = it.quantity,
                                            fontSize = 14.sep,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(10.dep),
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(10.dep))
                                    Text(
                                        text = "₹${it.price_for_product}",
                                        fontSize = 14.sep,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                            Divider(
                                modifier = Modifier.height(0.3.dep),
                                color = Color(0XFFADA9A9)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dep))
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dep)
                ) {
                    Text(
                        text = stringResource(id = R.string.Payment_Details),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sep
                    )
                    Spacer(modifier = Modifier.height(12.dep))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                        ,
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        Column(horizontalAlignment = Alignment.Start) {
                            Text(
                                text = stringResource(id = R.string.Taxable_Amount),
                                fontSize = 12.sep
                            )
                            Spacer(modifier = Modifier.height(10.dep))
                            Text(
                                text = stringResource(id = R.string.CGST),
                                fontSize = 12.sep
                            )
                            Spacer(modifier = Modifier.height(10.dep))
                            Text(
                                text = stringResource(id = R.string.SGST),
                                fontSize = 12.sep
                            )
                            Spacer(modifier = Modifier.height(10.dep))
                            Text(
                                text = stringResource(id = R.string.Total),
                                fontSize = 14.sep,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "₹${taxAmountState.value}",
                                fontSize = 14.sep
                            )
                            Spacer(modifier = Modifier.height(10.dep))
                            Text(
                                text = cgstState.value,
                                fontSize = 14.sep
                            )
                            Spacer(modifier = Modifier.height(10.dep))
                            Text(
                                text =gstState.value,
                                fontSize = 14.sep
                            )
                            Spacer(modifier = Modifier.height(10.dep))
                            Text(
                                text = totalState.value,
                                fontSize = 14.sep, fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(20.dep))
                Divider(
                    modifier = Modifier.height(0.3.dep),
                    color = Color(0XFFADA9A9)
                )
                Spacer(modifier = Modifier.height(10.dep))
                    OutlinedTextField(
                        value =reviewInstruction.value,
                        onValueChange = {
                            notifier.notify(MyDataIds.reviewInstruction, it)
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
                Spacer(modifier = Modifier.height(10.dep))
                Divider(
                    modifier = Modifier.height(0.3.dep),
                    color = Color(0XFFADA9A9)
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                Card(
                    colors = CardDefaults.cardColors(Color(0XFFFFFFFF)),
                    shape = RectangleShape,
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 8.dep
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = 20.dep,
                                vertical = 10.dep
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text ="${totalQuantityState.value} items",
                            fontSize = 12.sep,
                            fontWeight = FontWeight.Medium
                        )
                        Text(text = "₹${totalState.value}",
                            fontSize = 18.sep,
                            fontWeight = FontWeight.Bold
                        )
                        Button(
                            onClick = {
                                notifier.notify(MyDataIds.placeOrder)
                            },
                            colors = ButtonDefaults.buttonColors(Color(0XFFFFEB56)),
                            shape = RoundedCornerShape(2.dep),
                            modifier = Modifier
                        ) {
                            Text(
                                text = stringResource(id = R.string.Place_Order),
                                fontSize = 14.sep,
                                fontWeight = FontWeight.Medium,
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
fun PaymentDetails(
    it:ReviewCartDataResponse
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dep)
    ) {
        Text(
            text = stringResource(id = R.string.Payment_Details),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sep
        )
        Spacer(modifier = Modifier.height(12.dep))
        Row(
            modifier = Modifier
                .fillMaxWidth()
            ,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Column(horizontalAlignment = Alignment.Start) {
                Text(
                    text = stringResource(id = R.string.Taxable_Amount),
                    fontSize = 12.sep
                )
                Spacer(modifier = Modifier.height(10.dep))
                Text(
                    text = stringResource(id = R.string.CGST),
                    fontSize = 12.sep
                )
                Spacer(modifier = Modifier.height(10.dep))
                Text(
                    text = stringResource(id = R.string.SGST),
                    fontSize = 12.sep
                )
                Spacer(modifier = Modifier.height(10.dep))
                Text(
                    text = stringResource(id = R.string.Total),
                    fontSize = 14.sep,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "₹${it.sub_total}",
                    fontSize = 14.sep
                )
                Spacer(modifier = Modifier.height(10.dep))
                Text(
                    text = it.cgst,
                    fontSize = 14.sep
                )
                Spacer(modifier = Modifier.height(10.dep))
                Text(
                    text = it.gst,
                    fontSize = 14.sep
                )
                Spacer(modifier = Modifier.height(10.dep))
                Text(
                    text = "₹${it.total}",
                    fontSize = 14.sep, fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

/*@Composable
fun AddedProductToCartList(
    it: ReviewCartDataResponse.Cart.ProductList
){
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 30.dep),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Column(horizontalAlignment = Alignment.Start) {
            Text(
                text = it.name,
                fontSize = 14.sep)
            Spacer(modifier = Modifier.height(10.dep))
            Text(text = "₹100/pcs", fontSize = 12.sep, fontWeight = FontWeight.Medium)
            TextButton(
                onClick = {
                },
                modifier = Modifier
            ) {
                Text(text = stringResource(id = R.string.REMOVE),
                    fontSize = 10.sep,
                    color = Color(0xFF222222)
                )
            }
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            OutlinedCard(
                shape = RoundedCornerShape(2.dep),
                modifier = Modifier.width(73.dep),
                border = BorderStroke(1.dep, color = Color(0XFFD3D3D3))
            ) {
                Text(text = "30", fontSize = 14.sep,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dep),
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.height(10.dep))
            Text(text = "₹3000", fontSize = 14.sep,
                fontWeight = FontWeight.Medium)
        }
    }
}*/

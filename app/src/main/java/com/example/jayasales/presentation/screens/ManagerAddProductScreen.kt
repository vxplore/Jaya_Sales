package com.example.jayasales.presentation.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.AutoDelete
import androidx.compose.material.icons.filled.BorderColor
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
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
import com.example.jayasales.model.DistributorDetailsProduct
import com.example.jayasales.model.SearchData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManagerAddProductScreen(
    notifier: NotificationService = rememberNotifier(),
    qty: State<String> = stringState(key = MyDataIds.qty),
    searchList: List<SearchData> = listState(key = MyDataIds.searchList),
    loadingState: State<Boolean> = boolState(key = MyDataIds.loadingState),
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Add Products",
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
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(20.dep))
            OrderSearchBox()
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
            }else {
                Spacer(modifier = Modifier.height(16.dep))
                Divider()
                Spacer(modifier = Modifier.height(20.dep))

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentPadding = PaddingValues(vertical = 10.dep),
                    verticalArrangement = Arrangement.spacedBy(20.dep)
                ) {
                    if (searchList.isEmpty()) {
                        item {
                            Text(
                                text = "Search your product",
                                fontSize = 16.sep,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dep)
                            )
                        }
                    } else {
                        itemsIndexed(searchList) { index, it ->
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
                                OutlinedTextField(
                                    value = qty.value,
                                    onValueChange = {
                                        //qtyState.value = it
                                        notifier.notify(MyDataIds.qty, it)
                                    },
                                    placeholder = {
                                        Text(
                                            text = "amount",
                                            color = Color(0XFF898989),
                                            fontSize = 7.sep
                                        )
                                    },
                                    maxLines = 1,
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Number,
                                        imeAction = ImeAction.Done
                                    ),
                                    modifier = Modifier
                                        //.padding(horizontal = 20.dep)
                                        .size(60.dep)
                                    //.fillMaxWidth()
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    Box (
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ){
        Button(
            onClick = {
                      notifier.notify(MyDataIds.addNow)
            },
            modifier = Modifier
                .padding(horizontal = 16.dep)
                .padding(bottom = 20.dep)
                .height(54.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(Color(0xFF2DB87C)),
            shape = RoundedCornerShape(4.dp)
        ) {
            Text(
                text = "Add Now",
                fontSize = 16.sp,
                color = Color.White
            )
        }
    }
}

@Composable
fun OrderSearchBox(
    notifier: NotificationService = rememberNotifier(),
    orderSearch: State<String> = stringState(key = MyDataIds.orderSearch),
) {
    OutlinedTextField(
        value = orderSearch.value,
        onValueChange = {
            notifier.notify(MyDataIds.orderSearch, it)
        },
        placeholder = {
            Text(
                text = "Search Products",
                color = Color(0XFF898989),
                fontSize = 16.sep
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Done
        ),
        maxLines = 1,
        trailingIcon = {
            IconButton(
                modifier = Modifier
                    .background(
                        Color(0xFF2DB87C),
                        RoundedCornerShape(topEnd = 4.dep, bottomEnd = 4.dep)
                    )
                    .height(54.dep),
                onClick = {
                          notifier.notify(MyDataIds.search)
                },
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "",
                    tint = Color.White
                )
            }
        }, modifier = Modifier
            .padding(horizontal = 16.dep)
            .fillMaxWidth()
    )
}
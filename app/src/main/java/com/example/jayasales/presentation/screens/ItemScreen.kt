package com.example.jayasales.presentation.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.debduttapanda.j3lib.NotificationService
import com.debduttapanda.j3lib.boolState
import com.debduttapanda.j3lib.dep
import com.debduttapanda.j3lib.intState
import com.debduttapanda.j3lib.listState
import com.debduttapanda.j3lib.rememberNotifier
import com.debduttapanda.j3lib.sep
import com.debduttapanda.j3lib.stringState
import com.example.jayasales.MyDataIds
import com.example.jayasales.R
import com.example.jayasales.model.AllBrandDataResponse
import com.example.jayasales.model.AllCategory
import com.example.jayasales.model.Product

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ItemScreen(
    notifier: NotificationService = rememberNotifier(),
    brandTabItem: List<AllBrandDataResponse.Brand> = listState(key = MyDataIds.brands),
    selectedTabIndex: State<Int> = intState(key = MyDataIds.brandChange),
    categoryList: List<AllCategory.Category> = listState(key = MyDataIds.categories),
    selectedCategoryId: State<String> = stringState(key = MyDataIds.selectedCategoryId),
    productData: List<Product> = listState(key = MyDataIds.filterProductData),
    lostInternet: State<Boolean> = boolState(key = MyDataIds.lostInternet),
    loadingState:State<Boolean> = boolState(key = MyDataIds.loadingState),
    emptyDataDialog:State<Boolean> = boolState(key = MyDataIds.emptyDataDialog),
) {
    val openDialog = remember { mutableStateOf(false) }
    if (emptyDataDialog.value){
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = {
                Text(
                    text = "No Data Available",
                    fontSize = 16.sep,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF222222)
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        notifier.notify(MyDataIds.gotIt)
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
                        text = "Got It",
                        fontSize = 14.sep,
                        color = Color.White,
                    )
                }
            },
           /* icon = {
                Image(
                    painter = painterResource(id = R.drawable.tick_mark),
                    contentDescription = stringResource(id = R.string.Payment_received_successfully),
                    modifier = Modifier
                        .padding(start = 14.dep)
                        .height(72.dp)
                        .width(72.dp),
                )
            },*/
            shape = RoundedCornerShape(4.dep)
        )
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Items",
                        fontSize = 20.sep,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
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
        var totalQuantity by remember { mutableStateOf(0) }
        if (lostInternet.value) {
            LostInternet_ui(onDismissRequest = { notifier.notify(MyDataIds.onDissmiss) })
        }
            Box(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Spacer(modifier = Modifier.height(12.dep))
                    ScrollableTabRow(
                        selectedTabIndex = selectedTabIndex.value,
                        contentColor = Color.Black,
                        containerColor = Color.Transparent,
                        edgePadding = 0.dep,
                        modifier = Modifier.fillMaxWidth(),
                        indicator = { tabPositions ->
                            if (selectedTabIndex.value > -1 && tabPositions.isNotEmpty()) {
                                TabRowDefaults.Indicator(
                                    modifier = Modifier.tabIndicatorOffset(
                                        currentTabPosition = tabPositions[selectedTabIndex.value]
                                    ),
                                    color = Color.Red
                                )
                            }
                        }
                    )
                    {
                        brandTabItem.forEachIndexed { tabIndex, tab ->
                            Tab(
                                selected = selectedTabIndex.value == tabIndex,
                                onClick = {
                                    notifier.notify(MyDataIds.brandChange, tabIndex)
                                },
                                text = {
                                    Text(
                                        text = tab.name,
                                        fontSize = 12.sep,
                                        fontWeight = FontWeight.Medium
                                    )
                                },
                                modifier = Modifier
                                    .weight(1f),
                                selectedContentColor = Color.Red,
                                unselectedContentColor = Color.Black
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dep))
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dep)
                    ) {
                        ItemSearchField()
                        Spacer(modifier = Modifier.height(20.dep))
                        Text(
                            text = stringResource(id = R.string.Categories),
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sep
                        )
                        Spacer(modifier = Modifier.height(10.dep))
                        LazyRow(
                            modifier = Modifier.fillMaxWidth(),
                            contentPadding = PaddingValues(horizontal = 8.dep),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dep)
                        ) {
                            itemsIndexed(categoryList) { index, it ->
                                ItemCategoryTabItemUi(index, it, selectedCategoryId.value)
                            }
                        }
                        Spacer(modifier = Modifier.height(24.dep))
                        if (loadingState.value) {
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
                                //.padding(bottom = 80.dep)
                                .fillMaxSize(),
                        ) {
                            if (productData.isEmpty()) {
                                item {
                                    Text(
                                        text = "No data available",
                                        fontSize = 16.sep,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dep)
                                    )
                                }
                            } else {
                                items(productData) {
                                    ItemProductList(
                                        noOfItem = totalQuantity,
                                        it,
                                        onQuantityChange = { newQuantity ->
                                            totalQuantity += newQuantity
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

@Composable
fun ItemProductList(
    noOfItem: Int,
    it: Product,
    onQuantityChange: (Int) -> Unit,
    notifier: NotificationService = rememberNotifier()
) {
    var quantity by remember { mutableStateOf(0) }
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(Color(0XFFF5F5F5)),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dep
        ),
        border = BorderStroke(1.dep, color = Color(0XFFECECEC)),
        shape = RoundedCornerShape(8.dep)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dep)
            ,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = it.image,
                    contentDescription = "",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .height(64.dep)
                        .width(72.dep)
                )
                Spacer(modifier = Modifier.width(8.dep))
                Column {
                    Text(
                        text = "${it.name}",
                        fontSize = 14.sep,
                        color = Color(0XFF838383)
                    )
                    Spacer(modifier = Modifier.height(5.dep))
                    Text(
                        text = "${it.name}",
                        fontSize = 14.sep,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0XFF222222)
                    )
                    Spacer(modifier = Modifier.height(5.dep))
                    Text(
                        text = "₹${it.sell_price} MRP ₹${it.mrp}",
                        fontSize = 12.sep,
                        color = Color(0XFF838383)
                    )
                }
            }
            Column {
                Text(
                    text = "-${it.discount} %OFF",
                    fontSize = 10.sep,
                    color = Color(0XFFFF4155),
                    modifier = Modifier.padding(start = 30.dep)
                )
                Spacer(modifier = Modifier.height(20.dep))
                Card(
                    modifier = Modifier
                        .width(80.dep)
                        .height(35.dep),
                    colors = CardDefaults.cardColors(Color.Transparent),
                    border = BorderStroke(0.2.dep, color = Color(0XFFFF4155)),
                    shape = RoundedCornerShape(1.dep)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(.33f)
                                .clickable {
                                    if (quantity > 0) {
                                        quantity--
                                        onQuantityChange(quantity)
                                    }
                                }
                                .clip(RoundedCornerShape(0.dep))
                        ) {
                            Text(
                                text = "-",
                                fontSize = 16.sep,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                            )
                        }
                        Divider(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(0.3.dep),
                            color = Color(0XFFA91F1A)
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(.33f)
                        ) {
                            Text(
                                text = quantity.toString(),
                                fontSize = 16.sep,
                                fontWeight = FontWeight.Normal
                            )
                        }
                        Divider(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(0.3.dep),
                            color = Color(0XFFA91F1A)
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(.33f)
                                .clickable {
                                    quantity++
                                    onQuantityChange(quantity)
                                }
                                .clip(RoundedCornerShape(0.dep))
                        ) {
                            Text(
                                text = "+",
                                fontSize = 16.sep,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                    }
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(10.dep))
}

@Composable
fun ItemCategoryTabItemUi(
    index: Int,
    it: AllCategory.Category,
    selectedCategoryId: String,
    notifier: NotificationService = rememberNotifier()
) {
    Button(
        onClick = {
            notifier.notify(MyDataIds.categoryChange, it.uid)
        },
        colors = if (it.uid == selectedCategoryId)
            ButtonDefaults.buttonColors(Color(0XFF1FB574))
        else
            ButtonDefaults.buttonColors(Color.White)
    ) {
        Text(
            text = it.name,
            fontSize = 12.sep,
            color = if (it.uid == selectedCategoryId)
                Color.White
            else Color.Black
        )
    }
}

@Composable
fun ItemSearchField(
    notifier: NotificationService = rememberNotifier(),
    productSearch: State<String> = stringState(key = MyDataIds.productSearch)
) {
    OutlinedTextField(
        value = productSearch.value,
        onValueChange = {
            notifier.notify(MyDataIds.productSearch, it)
        },
        placeholder = {
            Text(
                text = "Search Product",
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
fun LostInternet_ui(
    onDismissRequest: () -> Unit,
    notifier: NotificationService = rememberNotifier(),
    lostInternet: State<Boolean> = boolState(key = MyDataIds.lostInternet)
) {


    Dialog(
        onDismissRequest = { onDismissRequest() }
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(Color.White)
        ) {

            Spacer(modifier = Modifier.height(24.dep))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
            ) {


                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,

                    ) {
                    Image(
                        painter = painterResource(id = R.drawable.no_internet),
                        contentDescription = "",
                        modifier = Modifier

                            .height(70.dep)
                            .width(70.dep)
                    )
                    Spacer(
                        modifier = Modifier
                            .height(10.dep)
                    )
                    Text(
                        "Please check your connection",
                        fontSize = 20.sp,
                        color = Color(0xFF222222),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(horizontal = 20.dep)
                    )
                    Spacer(
                        modifier = Modifier
                            .height(32.dep)
                    )
                    TextButton(
                        onClick = {
                            notifier.notify(MyDataIds.tryagain)
                        },
                        colors = ButtonDefaults.buttonColors(Color(0xFFD62B2B)),
                        modifier = Modifier
                            //.padding(horizontal = 20.dep)
                    ) {
                        Text(
                            "Try again",
                            fontSize = 16.sep,
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(horizontal = 8.dep)
                        )
                    }

                    Spacer(
                        modifier = Modifier
                            .height(32.dep)
                    )

                }
            }
        }
    }
}

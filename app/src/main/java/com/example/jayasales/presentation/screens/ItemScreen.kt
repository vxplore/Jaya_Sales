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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
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
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.debduttapanda.j3lib.NotificationService
import com.debduttapanda.j3lib.dep
import com.debduttapanda.j3lib.intState
import com.debduttapanda.j3lib.listState
import com.debduttapanda.j3lib.rememberNotifier
import com.debduttapanda.j3lib.sep
import com.debduttapanda.j3lib.stringState
import com.example.jayasales.MyDataIds
import com.example.jayasales.R
import com.example.jayasales.model.Brand
import com.example.jayasales.model.Category
import com.example.jayasales.model.Product

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ItemScreen(
    notifier: NotificationService = rememberNotifier(),
    brandTabItem: List<Brand> = listState(key = MyDataIds.brands),
    selectedTabIndex: State<Int> = intState(key = MyDataIds.brandChange),
    categoryList: List<Category> = listState(key = MyDataIds.categories),
    noOfItem: State<Int> = intState(key = MyDataIds.noOfItem),
    selectedCategoryId: State<String> = stringState(key = MyDataIds.selectedCategoryId),
    productData: List<Product> = listState(key = MyDataIds.filterProductData)
) {
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
                            notifier.notify(
                                MyDataIds.back
                            )
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBackIosNew,
                            contentDescription = "hamburger",
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
                    modifier = Modifier
                        .fillMaxWidth(),
                    //.padding(top = 10.dep),
                    indicator = { tabPosition ->
                        if (selectedTabIndex.value > -1) {
                            TabRowDefaults.Indicator(
                                modifier = Modifier.tabIndicatorOffset(
                                    currentTabPosition = tabPosition[selectedTabIndex.value]
                                ),
                                color = Color.Red
                            )
                        }
                    }
                ) {
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
                            modifier = Modifier.weight(1f),
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
                        text = "Categories",
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
                    LazyColumn(
                        modifier = Modifier
                            .padding(bottom = 80.dep)
                            .fillMaxSize(),
                    ) {
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
                .padding(16.dep),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(painter = painterResource(id = R.drawable.biscuit), contentDescription = "")
                Spacer(modifier = Modifier.width(16.dep))
                Column {
                    Text(
                        text = "${it.type}",
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
                        text = "₹${it.sellValue} MRP ₹${it.mrp}",
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
                            .fillMaxWidth()
                            .padding(horizontal = 10.dep),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "-",
                            fontSize = 16.sep,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.clickable {
                                if (quantity > 0) {
                                    quantity--
                                    onQuantityChange(quantity)
                                }
                            }
                        )
                        Divider(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(0.3.dep),
                            color = Color(0XFFA91F1A)
                        )
                        Text(
                            text = quantity.toString(),
                            fontSize = 16.sep,
                            fontWeight = FontWeight.Normal
                        )
                        Divider(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(0.3.dep),
                            color = Color(0XFFA91F1A)
                        )
                        Text(text = "+",
                            fontSize = 16.sep,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.clickable {
                                quantity++
                                onQuantityChange(quantity)
                            }
                        )
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
    it: Category,
    selectedCategoryId: String,
    notifier: NotificationService = rememberNotifier()

) {
    Button(
        onClick = {
            notifier.notify(MyDataIds.categoryChange, it.id)
        },
        colors = if (it.id == selectedCategoryId)
            ButtonDefaults.buttonColors(Color(0XFF1FB574))
        else
            ButtonDefaults.buttonColors(Color.White)
    ) {
        Text(
            text = it.name,
            fontSize = 12.sep,
            color = if (it.id == selectedCategoryId) Color.White
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

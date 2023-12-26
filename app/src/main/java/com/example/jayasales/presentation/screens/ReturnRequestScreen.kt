package com.example.jayasales.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.debduttapanda.j3lib.NotificationService
import com.debduttapanda.j3lib.dep
import com.debduttapanda.j3lib.listState
import com.debduttapanda.j3lib.rememberNotifier
import com.debduttapanda.j3lib.sep
import com.debduttapanda.j3lib.stringState
import com.example.jayasales.MyDataIds
import com.example.jayasales.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReturnRequestScreen(
    notifier: NotificationService = rememberNotifier(),
    product: State<String> = stringState(key = MyDataIds.product),
    lot: State<String> = stringState(key = MyDataIds.lot)
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Return Request",
                        fontSize = 20.sep,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2B2B2B),
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
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(24.dep))
            OutlinedTextField(
                value = product.value,
                onValueChange = {
                    notifier.notify(MyDataIds.product, it)
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                placeholder = {
                    Text(
                        stringResource(id = R.string.Product_Name),
                        color = Color(0xFF959595),
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
                    //focusedIndicatorColor = Color(0xFFEB3D34),
                    unfocusedIndicatorColor = Color(0xFFB9B9B9),
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                ),
            )
            Spacer(modifier = Modifier.height(12.dep))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .background(Color.White, RoundedCornerShape(5.dep))
                        .border(1.dep, Color(0xFFB9B9B9), RoundedCornerShape(5.dep))
                        .height(54.dep)
                        .weight(.5f)
                    //.fillMaxWidth()
                ) {
                    BrandDropDown()
                }
                Spacer(modifier = Modifier.width(12.dep))
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .background(Color.White, RoundedCornerShape(5.dep))
                        .border(1.dep, Color(0xFFB9B9B9), RoundedCornerShape(5.dep))
                        .height(54.dep)
                        .weight(.5f)
                    //.fillMaxWidth()
                ) {
                    BrandCategoryDropDown()
                }
            }
            Spacer(modifier = Modifier.height(12.dep))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .weight(.5f)
                ) {
                    ReturnDatePicker()
                }
                Spacer(modifier = Modifier.width(12.dep))
                OutlinedTextField(
                    value = lot.value,
                    onValueChange = {
                        notifier.notify(MyDataIds.lot, it)
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    placeholder = {
                        Text(
                            stringResource(id = R.string.Lot_No),
                            color = Color(0xFF959595),
                            fontSize = 14.sep
                        )
                    },
                    modifier = Modifier
                        .background(Color.White)
                        .weight(.5f),
                    textStyle = TextStyle(
                        color = Color(0xFF222222),
                        fontSize = 14.sep
                    ),
                    colors = TextFieldDefaults.colors(
                        //focusedIndicatorColor = Color(0xFFEB3D34),
                        unfocusedIndicatorColor = Color(0xFFB9B9B9),
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                    ),
                )
            }
            Spacer(modifier = Modifier.height(12.dep))
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .background(Color.White, RoundedCornerShape(5.dep))
                    .border(1.dep, Color(0xFFB9B9B9), RoundedCornerShape(5.dep))
                    .height(54.dep)
                    .fillMaxWidth()
            ) {
                ReasonDropDown()
            }
        }
    }
}

@Composable
fun BrandDropDown(
    brandName: SnapshotStateList<String> = listState(key = MyDataIds.brandName)
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf("Brand Name") }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .clickable { expanded = true }
            .fillMaxWidth()
    ) {
        Text(
            text = selectedItem,
            fontSize = 14.sep,
            color = Color(0xFF222222),
            modifier = Modifier
                .padding(start = 10.dep)
        )
        IconButton(
            onClick = {
                expanded = true
            },
            modifier = Modifier
                .padding(end = 16.dep)
                .height(24.dep)
                .width(24.dep)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = stringResource(id = R.string.ArrowDropDown),
                tint = Color(0xFF666666)
            )
        }
    }
    if (expanded) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.BottomEnd
        ) {
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                brandName.forEach { item ->
                    DropdownMenuItem(
                        {
                            Text(
                                text = item,
                                fontSize = 14.sep,
                                color = Color(0xFF222222)
                            )
                        },
                        onClick = {
                            selectedItem = item
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun BrandCategoryDropDown(
    productCategory: SnapshotStateList<String> = listState(key = MyDataIds.productCategory)
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf("Category") }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .clickable { expanded = true }
            .fillMaxWidth()
    ) {
        Text(
            text = selectedItem,
            fontSize = 14.sep,
            color = Color(0xFF222222),
            modifier = Modifier
                .padding(start = 10.dep)
        )
        IconButton(
            onClick = {
                expanded = true
            },
            modifier = Modifier
                .padding(end = 16.dep)
                .height(24.dep)
                .width(24.dep)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = stringResource(id = R.string.ArrowDropDown),
                tint = Color(0xFF666666)
            )
        }
    }
    if (expanded) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.BottomEnd
        ) {
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                productCategory.forEach { item ->
                    DropdownMenuItem(
                        { Text(text = item, fontSize = 14.sep, color = Color(0xFF222222)) },
                        onClick = {
                            selectedItem = item
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ReasonDropDown(
    reason: SnapshotStateList<String> = listState(key = MyDataIds.reason)
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf("Select Rason") }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .clickable { expanded = true }
            .fillMaxWidth()
    ) {
        Text(
            text = selectedItem,
            fontSize = 14.sep,
            color = Color(0xFF222222),
            modifier = Modifier
                .padding(start = 10.dep)
        )
        IconButton(
            onClick = {
                expanded = true
            },
            modifier = Modifier
                .padding(end = 16.dep)
                .height(24.dep)
                .width(24.dep)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = stringResource(id = R.string.ArrowDropDown),
                tint = Color(0xFF666666)
            )
        }
    }
    if (expanded) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.BottomEnd
        ) {
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                reason.forEach { item ->
                    DropdownMenuItem(
                        { Text(text = item, fontSize = 14.sep, color = Color(0xFF222222)) },
                        onClick = {
                            selectedItem = item
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReturnDatePicker(
) {
    val calendar = Calendar.getInstance()
    // set the initial date
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = calendar.timeInMillis)
    var showDatePicker by remember {
        mutableStateOf(false)
    }
    var selectedDate by remember {
        mutableLongStateOf(calendar.timeInMillis)
    }
    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = {
                showDatePicker = false
            },
            confirmButton = {
                TextButton(onClick = {
                    showDatePicker = false
                    selectedDate = datePickerState.selectedDateMillis!!
                }
                ) {
                    Text(text = "Confirm")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDatePicker = false
                }) {
                    Text(text = "Cancel")
                }
            }
        ) {
            DatePicker(
                state = datePickerState
            )
        }
    }
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.ROOT)
    val date = formatter.format(Date(selectedDate))
    //if (date != formatter.format(Date())) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .background(Color.White, RoundedCornerShape(5.dep))
            .border(1.dep, Color(0xFF959595), RoundedCornerShape(5.dep))
            .fillMaxWidth()
            .height(54.dep)
            .padding(horizontal = 8.dep)
    ) {
        Text(
            text = date,
            color = Color(0xFF222222),
            fontSize = 14.sep,
            modifier = Modifier
            //.border(1.5.dep,MaterialTheme.colorScheme.surface)
        )
        IconButton(
            onClick = {
                showDatePicker = true
            },
        ) {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = "",
                tint = Color(0xFFB9B9B9)
            )
        }
    }
}
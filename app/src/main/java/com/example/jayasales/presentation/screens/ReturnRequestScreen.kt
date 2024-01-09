package com.example.jayasales.presentation.screens

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import com.debduttapanda.j3lib.NotificationService
import com.debduttapanda.j3lib.dep
import com.debduttapanda.j3lib.depx
import com.debduttapanda.j3lib.listState
import com.debduttapanda.j3lib.rememberNotifier
import com.debduttapanda.j3lib.sep
import com.debduttapanda.j3lib.stringState
import com.example.jayasales.MyDataIds
import com.example.jayasales.R
import com.example.jayasales.model.SelectedFile
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReturnRequestScreen(
    notifier: NotificationService = rememberNotifier(),
    product: State<String> = stringState(key = MyDataIds.product),
    lot: State<String> = stringState(key = MyDataIds.lot),
    message: State<String> = stringState(key = MyDataIds.message),
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string. Return_Request),
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
                        color = Color(0xFF666666),
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
                            color = Color(0xFF666666),
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
            Spacer(modifier = Modifier.height(12.dep))
            OutlinedTextField(
                value = message.value,
                onValueChange = {
                    notifier.notify(MyDataIds.message, it)
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                placeholder = {
                    Text(
                        stringResource(id = R.string.Message),
                        color = Color(0xFF666666),
                        fontSize = 14.sep
                    )
                },
                modifier = Modifier
                    .background(Color.White)
                    .height(136.dep)
                    .fillMaxWidth(),
                textStyle = TextStyle(
                    color = Color(0xFF222222),
                    fontSize = 14.sep
                ),
                colors = TextFieldDefaults.colors(
                    unfocusedIndicatorColor = Color(0xFFB9B9B9),
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                ),
            )
            Spacer(modifier = Modifier.height(12.dep))
            Text(
                stringResource(id = R.string.Upload_Photo),
                color = Color(0xFF666666),
                fontSize = 14.sep
            )
            Spacer(modifier = Modifier.height(8.dep))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                FilePickerScreen()
            }
        }
        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier
                .padding(horizontal = 24.dep)
                .padding(bottom = 20.dep)
                .fillMaxSize()
        ) {
            Button(
                onClick = { notifier.notify(MyDataIds.signUpClick,it) },
                modifier = Modifier
                    .height(54.dep)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(Color(0xFFFFEB56)),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 8.dep,
                    pressedElevation = 10.dep
                ),
                shape = RoundedCornerShape(4.dep)
            ) {
                Text(
                    text = stringResource(id = R.string.Submit),
                    fontSize = 18.sep,
                    color = Color(0xFF222222)
                )
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
        )
        IconButton(
            onClick = {
                showDatePicker = true
            },
        ) {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = "",
                tint = Color(0xFF666666)
            )
        }
    }
}

val stroke = Stroke(
    width = 2f,
    pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
)

@Composable
fun FilePickerScreen()
{
    var selectedFileUri by remember { mutableStateOf<Uri?>(null) }
    val selectedFileName = remember { mutableStateOf<String?>(null) }
    var isPDFFile by remember { mutableStateOf(false) }
    var selectedFiles by remember { mutableStateOf<MutableList<SelectedFile>>(mutableListOf()) }
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            selectedFileUri = it
            isPDFFile = selectedFileName.value?.endsWith(".pdf", ignoreCase = true) == true
            val selectedFile = SelectedFile(uri)
            selectedFiles.add(selectedFile)
        }
    }
    val openFileIntentLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .height(64.dep)
            .width(52.dep)
            .clip(MaterialTheme.shapes.small)
            .clickable {
                launcher.launch("*/*")
            }
            .drawBehind {
                drawRoundRect(
                    color = Color.DarkGray,
                    style = stroke,
                    cornerRadius = CornerRadius(4.depx, 4.depx)
                )
            }
    ) {
        AsyncImage(
            model = R.drawable.camera,
            contentDescription = "File Icon",
            modifier = Modifier
                .height(24.dp)
                .width(24.dp)
        )
    }
    selectedFileUri?.let {
        LazyRow(
            modifier = Modifier
                .padding(start = 16.dp)
                .background(Color(0xFFFBFBFB))
                .fillMaxWidth()
        ) {
            items(selectedFiles) { selectedFile ->
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .width(92.dp)
                        .clickable {
                            val openFileIntent = Intent(Intent.ACTION_VIEW)
                            openFileIntent.data = selectedFile.uri
                            openFileIntentLauncher.launch(openFileIntent)
                        }
                ) {
                    Row {
                        LoadSelectedImage(selectedFile.uri)
                        Image(
                            painter = painterResource(id = R.drawable.cancel),
                            contentDescription = "Cancel Icon",
                            modifier = Modifier
                                .background(Color.White, CircleShape)
                                .size(16.dep)
                                .clickable {
                                    val indexToRemove =
                                        selectedFiles.indexOfFirst { it.uri == selectedFile.uri }
                                    if (indexToRemove != -1) {
                                        selectedFiles.removeAt(indexToRemove)
                                    }
                                }
                        )
                    }
                }
            }
        }
    }
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun LoadSelectedImage(uri: Uri) {
    val selectedImage = rememberImagePainter(data = uri)
    Image(
        painter = selectedImage,
        contentDescription = "Selected Image",
        modifier = Modifier
            .height(64.dp)
            .width(52.dp),
        contentScale = ContentScale.Crop,
    )
}

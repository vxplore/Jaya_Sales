package com.example.jayasales.presentation.screens

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewStoreScreen(
    notifier: NotificationService = rememberNotifier(),
    storeName: State<String> = stringState(key = MyDataIds.storeName),
    pin: State<String> = stringState(key = MyDataIds.pin),
    address: State<String> = stringState(key = MyDataIds.address),
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.Add_Store),
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
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(12.dep))
            Text(
                text = stringResource(id = R.string.Store_Name),
                fontSize = 14.sep,
                color = Color(0xFF222222)
            )
            Spacer(modifier = Modifier.height(8.dep))
            OutlinedTextField(
                value = storeName.value,
                onValueChange = {
                    notifier.notify(MyDataIds.storeName, it)
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                placeholder = {
                    Text(
                        stringResource(id = R.string.Store_name),
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
                    //focusedIndicatorColor = Color(0xFFEB3D34),
                    unfocusedIndicatorColor = Color(0xFFDDDDDD),
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                ),
            )
            Spacer(modifier = Modifier.height(16.dep))
            Text(
                text = stringResource(id = R.string.shop_image),
                fontSize = 14.sep,
                color = Color(0xFF222222)
            )
            Spacer(modifier = Modifier.height(8.dep))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                ImagePickerScreen()
            }
            Spacer(modifier = Modifier.height(16.dep))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .weight(.5f)
                ) {
                    Text(
                        text = stringResource(id = R.string.City),
                        fontSize = 14.sep,
                        color = Color(0xFF222222)
                    )
                    Spacer(modifier = Modifier.height(8.dep))
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .background(Color.White, RoundedCornerShape(5.dep))
                            .border(1.dep, Color(0xFFB9B9B9), RoundedCornerShape(5.dep))
                            .height(54.dep)
                            .fillMaxWidth()
                    ) {
                        CityDropDown()
                    }
                }
                Spacer(modifier = Modifier.width(12.dep))
                Column(
                    modifier = Modifier
                        .weight(.5f)
                ) {
                    Text(
                        text = stringResource(id = R.string.State),
                        fontSize = 14.sep,
                        color = Color(0xFF222222)
                    )
                    Spacer(modifier = Modifier.height(8.dep))
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .background(Color.White, RoundedCornerShape(5.dep))
                            .border(1.dep, Color(0xFFB9B9B9), RoundedCornerShape(5.dep))
                            .height(54.dep)
                            .fillMaxWidth()
                    ) {
                        StateDropDown()
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dep))
            Text(
                text = stringResource(id = R.string.Postal_Code),
                fontSize = 14.sep,
                color = Color(0xFF222222)
            )
            Spacer(modifier = Modifier.height(8.dep))
            OutlinedTextField(
                value = pin.value,
                onValueChange = {
                    notifier.notify(MyDataIds.pin, it)
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                placeholder = {
                    Text(
                        stringResource(id = R.string.postalCode),
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
                    unfocusedIndicatorColor = Color(0xFFDDDDDD),
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                ),
            )
            Spacer(modifier = Modifier.height(16.dep))
            Text(
                text = stringResource(id = R.string.Address),
                fontSize = 14.sep,
                color = Color(0xFF222222)
            )
            Spacer(modifier = Modifier.height(8.dep))
            OutlinedTextField(
                value = address.value,
                onValueChange = {
                    notifier.notify(MyDataIds.address, it)
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                placeholder = {
                    Text(
                        stringResource(id = R.string.EnterAddress),
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
                    unfocusedIndicatorColor = Color(0xFFDDDDDD),
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                ),
            )
            Spacer(modifier = Modifier.height(16.dep))
            Text(
                text = stringResource(id = R.string.addRoute),
                fontSize = 14.sep,
                color = Color(0xFF222222)
            )
            Spacer(modifier = Modifier.height(8.dep))
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .background(Color.White, RoundedCornerShape(5.dep))
                    .border(1.dep, Color(0xFFB9B9B9), RoundedCornerShape(5.dep))
                    .height(54.dep)
                    .fillMaxWidth()
            ) {
                RouteDropDown()
            }
            Spacer(modifier = Modifier.height(16.dep))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(144.dep)
            ) {
                GoogleMapSection()
            }
            Spacer(modifier = Modifier.height(20.dep))

            Button(
                onClick = {
                },
                modifier = Modifier
                    .height(54.dep)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(Color.White),
                border = BorderStroke(1.dep, Color(0xFF1FB574)),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 8.dep,
                    pressedElevation = 10.dep
                ),
                shape = RoundedCornerShape(4.dep)
            ) {
                Text(text = "GET LOCATION", fontSize = 16.sep, color = Color(0xFF1FB574))
            }
            Spacer(modifier = Modifier.height(16.dep))
            Button(
                onClick = { notifier.notify(MyDataIds.contactInformation) },
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
                Text(text = "NEXT", fontSize = 16.sep, color = Color(0xFF222222))
            }
        }
    }
}

@Composable
fun ImagePickerScreen(
) {
    var selectedFileUri by remember { mutableStateOf<Uri?>(null) }
    val selectedFileName = remember { mutableStateOf<String?>(null) }
    var isPDFFile by remember { mutableStateOf(false) }
    var selectedFiles by remember { mutableStateOf<MutableList<SelectedFile>>(mutableListOf()) }
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
            .height(64.dp)
            .width(52.dp)
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
                        LoadSelectedImages(selectedFile.uri)
                        Image(
                            painter = painterResource(id = R.drawable.cancel),
                            contentDescription = "Cancel Icon",
                            modifier = Modifier
                                .background(Color.White, CircleShape)
                                .size(16.dp)
                                .clickable {
                                    selectedFiles.remove(selectedFile)
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
fun LoadSelectedImages(uri: Uri) {
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

@Composable
fun CityDropDown(
    city: SnapshotStateList<String> = listState(key = MyDataIds.city)
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf("Enter City") }
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
                city.forEach { item ->
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
fun StateDropDown(
    state: SnapshotStateList<String> = listState(key = MyDataIds.state)
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf("Enter City") }
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
                state.forEach { item ->
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
fun RouteDropDown(
    addRoute: SnapshotStateList<String> = listState(key = MyDataIds.addRoute)
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf("Enter Route") }
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
                addRoute.forEach { item ->
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
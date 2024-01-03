package com.example.jayasales.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.debduttapanda.j3lib.rememberNotifier
import com.debduttapanda.j3lib.sep
import com.debduttapanda.j3lib.stringState
import com.example.jayasales.MyDataIds
import com.example.jayasales.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactInformationScreen(
    notifier: NotificationService = rememberNotifier(),
    c_name: State<String> = stringState(key = MyDataIds.c_name),
    phnNo: State<String> = stringState(key = MyDataIds.phnNo),
    email: State<String> = stringState(key = MyDataIds.email),
    gst: State<String> = stringState(key = MyDataIds.gst),
    storeDialog: Boolean = boolState(key = MyDataIds.storeDialog).value,
) {
    val openDialog = remember { mutableStateOf(false) }
    if (storeDialog){
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = {
                Text(
                    text = stringResource(id = R.string.add_Store),
                    fontSize = 16.sep,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF222222)
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        notifier.notify(MyDataIds.addNewStore)
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
                        text = "Back to Parties",
                        fontSize = 14.sep,
                        color = Color.White,
                        modifier = Modifier

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
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(20.dep))
            Text(
                text = stringResource(id = R.string.contact),
                fontSize = 16.sep,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF222222)
            )
            Spacer(modifier = Modifier.height(18.dep))
            Text(
                text = stringResource(id = R.string.name),
                fontSize = 14.sep,
                color = Color(0xFF222222)
            )
            Spacer(modifier = Modifier.height(8.dep))
            OutlinedTextField(
                value = c_name.value,
                onValueChange = {
                    notifier.notify(MyDataIds.c_name, it)
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                placeholder = {
                    Text(
                        stringResource(id = R.string.enter_name),
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
                text = stringResource(id = R.string.PhoneNumber),
                fontSize = 14.sep,
                color = Color(0xFF222222)
            )
            Spacer(modifier = Modifier.height(8.dep))
            OutlinedTextField(
                value = phnNo.value,
                onValueChange = {
                    notifier.notify(MyDataIds.phnNo, it)
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                placeholder = {
                    Text(
                        stringResource(id = R.string.enter_phn),
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
                text = stringResource(id = R.string.Email),
                fontSize = 14.sep,
                color = Color(0xFF222222)
            )
            Spacer(modifier = Modifier.height(8.dep))
            OutlinedTextField(
                value = email.value,
                onValueChange = {
                    notifier.notify(MyDataIds.email, it)
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                placeholder = {
                    Text(
                        stringResource(id = R.string.EnterEmailId),
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
                text = stringResource(id = R.string.gst),
                fontSize = 14.sep,
                color = Color(0xFF222222)
            )
            Spacer(modifier = Modifier.height(8.dep))
            OutlinedTextField(
                value = gst.value,
                onValueChange = {
                    notifier.notify(MyDataIds.gst, it)
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                placeholder = {
                    Text(
                        stringResource(id = R.string.enterGst),
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
        }
        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier
                .padding(horizontal = 20.dep)
                .fillMaxSize()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(bottom = 20.dep)
                    .fillMaxWidth()
            ) {
                Button(
                    onClick = { notifier.notify(MyDataIds.backNow) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(.5f),
                    colors = ButtonDefaults.buttonColors(Color(0xFF222222)),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 8.dep,
                        pressedElevation = 10.dep
                    ),
                    shape = RoundedCornerShape(4.dep)
                ) {
                    Text(text = "BACK", fontSize = 18.sep, color = Color.White)
                }
                Spacer(modifier = Modifier.width(12.dep))
                Button(
                    onClick = { notifier.notify(MyDataIds.addNow) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(.5f),
                    colors = ButtonDefaults.buttonColors(Color(0xFFFFEB56)),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 8.dep,
                        pressedElevation = 10.dep
                    ),
                    shape = RoundedCornerShape(4.dep)
                ) {
                    Text(text = "ADD NOW", fontSize = 18.sep, color = Color(0xFF222222))
                }
            }
        }
    }
}
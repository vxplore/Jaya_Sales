package com.example.jayasales.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import com.debduttapanda.j3lib.NotificationService
import com.debduttapanda.j3lib.dep
import com.debduttapanda.j3lib.rememberBoolState
import com.debduttapanda.j3lib.rememberNotifier
import com.debduttapanda.j3lib.rememberStringState
import com.debduttapanda.j3lib.sep
import com.example.jayasales.MyDataIds
import com.example.jayasales.R
import com.example.jayasales.openSans

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    userName: State<String> = rememberStringState(id = MyDataIds.username),
    recoverUserName: State<String> = rememberStringState(id = MyDataIds.recoverUserName),
    password: State<String> = rememberStringState(id = MyDataIds.password),
    recoverPassword: State<String> = rememberStringState(id = MyDataIds.recoverPassword),
    confirmPassword: State<String> = rememberStringState(id = MyDataIds.confirmPassword),
    passwordVisibility: State<Boolean> = rememberBoolState(id = MyDataIds.passwordVisibility),
    recoverPasswordDialog: State<Boolean> = rememberBoolState(id = MyDataIds.recoverpasswordDialog),
    otpValue: State<String> = rememberStringState(id = MyDataIds.otpIntput),
    loading: State<Boolean> = rememberBoolState(id = MyDataIds.loading),
    notifier: NotificationService = rememberNotifier()
) {
    RecoverPasswordDialog(
        appearDialog = recoverPasswordDialog.value,
        recoverUserName.value,
        otpValue.value,
        recoverPassword.value,
        confirmPassword.value
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFD62B2B)),
        contentAlignment = Alignment.TopCenter
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(start = 24.dep)
                .fillMaxWidth()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.box),
                        contentDescription = "",
                        modifier = Modifier
                            .width(42.dep)
                            .height(76.dep),
                    )
                    Spacer(modifier = Modifier.width(5.dep))
                    Text(
                        text = "Hi",
                        fontSize = 20.sep,
                        fontFamily = openSans,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0XFFFFEB56),
                    )
                }
                Spacer(modifier = Modifier.height(8.dep))
                Text(
                    text = "Welcome\nBack!",
                    fontSize = 20.sep,
                    fontFamily = openSans,
                    fontWeight = FontWeight.Normal,
                    color = Color.White,
                    lineHeight = 28.sep
                )
            }
            Image(
                painter = painterResource(id = R.drawable.girl),
                contentDescription = "",
                modifier = Modifier
                    .padding(top = 40.dep)
                    .width(200.dep)
                    .height(304.dep)
            )
        }
    }
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier
            .padding(top = 260.dep)
            .background(Color.White, shape = RoundedCornerShape(topStart = 40.dep, topEnd = 40.dep))
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 24.dep)
                .fillMaxSize(),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(28.dep))
                Text(
                    text = stringResource(id = R.string.login),
                    fontSize = 20.sep,
                    color = Color(0xFF011947),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                )
            }
            Spacer(modifier = Modifier.height(24.dep))
            Text(
                text = stringResource(id = R.string.username),
                modifier = Modifier,
                fontSize = 12.sep,
                color = Color(0xFF677591)
            )
            Spacer(modifier = Modifier.height(6.dep))
            OutlinedTextField(
                value = userName.value,
                onValueChange = {
                    notifier.notify(MyDataIds.username, it)
                },
                modifier = Modifier
                    .background(Color(0xFFf7f9fb), RoundedCornerShape(12.dep))
                    .fillMaxWidth(),
                placeholder = {
                    Text(text = "use@gmail.com")
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedTextColor = Color(0xFF011947),
                    unfocusedTextColor = Color(0xFF011947),
                    cursorColor = Color.Black,
                    unfocusedBorderColor = Color(0xFF99accf)
                ),
                shape = RoundedCornerShape(12.dep),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(16.dep))
            Text(
                text = stringResource(id = R.string.password),
                modifier = Modifier,
                fontSize = 12.sep,
                color = Color(0xFF677591)
            )
            Spacer(modifier = Modifier.height(6.dep))
            OutlinedTextField(
                value = password.value,
                onValueChange = {
                    notifier.notify(MyDataIds.password, it)
                },
                modifier = Modifier
                    .background(Color.White, RoundedCornerShape(12.dep))
                    .fillMaxWidth(),
                placeholder = {
                    Text(text = if (!passwordVisibility.value) "********" else "123456")
                },
                shape = RoundedCornerShape(12.dep),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedTextColor = Color(0xFF011947),
                    unfocusedTextColor = Color(0xFF011947),
                    cursorColor = Color.Black,
                    unfocusedBorderColor = Color(0xFF99accf)
                ),
                visualTransformation = if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image =
                        if (passwordVisibility.value) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    IconButton(onClick = {
                        notifier.notify(MyDataIds.passwordVisibility)
                    }) {
                        Icon(imageVector = image, contentDescription = null)
                    }
                }
            )
            Spacer(modifier = Modifier.height(14.dep))
            Text(
                text = stringResource(id = R.string.recoverPassword),
                modifier = Modifier
                    .align(Alignment.End)
                    .clip(RoundedCornerShape(4.dep))
                    .clickable {
                        notifier.notify(MyDataIds.recoverPasswordClick)
                    },
                fontSize = 13.sep,
                color = Color(0xFF677591)
            )
        }
        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier
                .padding(horizontal = 24.dep)
                .padding(bottom = 80.dep)
                .fillMaxSize()
        ) {
            Button(
                onClick = { notifier.notify(MyDataIds.signUpClick) },
                modifier = Modifier
                    .height(60.dep)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(Color(0xFFFFEB56)),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 8.dep,
                    pressedElevation = 10.dep
                ),
                shape = RoundedCornerShape(12.dep)
            ) {
                if (loading.value) {
                    CircularProgressIndicator(
                        color = Color.White
                    )
                } else {
                    Text(
                        text = stringResource(id = R.string.Sign_In),
                        fontSize = 18.sep,
                        color = Color(0xFF222222)
                    )
                }
            }
        }
        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier
                .padding(horizontal = 24.dep)
                .padding(bottom = 20.dep)
                .fillMaxSize()
        ) {
            Text(
                text = stringResource(id = R.string.rights),
                fontSize = 12.sep,
                textAlign = TextAlign.Center,
                color = Color(0xFFC7C7C7)
            )
        }
    }
}

@Composable
private fun RecoverPasswordDialog(
    appearDialog: Boolean,
    userName: String,
    otpValue: String,
    password: String,
    confirmPassword: String,
    notifier: NotificationService = rememberNotifier()
) {
    if (appearDialog) {
        Dialog(onDismissRequest = { notifier.notify(MyDataIds.onDismissDialog) }) {
            RecoverPasswordCustomDialogUi(userName, otpValue, password, confirmPassword)
        }
    }
}

@Composable
private fun RecoverPasswordCustomDialogUi(
    userName: String,
    otpValue: String,
    password: String,
    confirmPassword: String,
    recoverLoading: State<Boolean> = rememberBoolState(id = MyDataIds.recoverLoading),
    notifier: NotificationService = rememberNotifier()
) {
    var showOtpField by remember { mutableStateOf(false) }
    var verifyButtonVisible by remember { mutableStateOf(true) }
    Box(
        Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(10.dep))
    ) {
        Column(
            modifier = Modifier
                .padding(30.dep),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.Recover_Password),
                modifier = Modifier
                    .align(Alignment.Start),
                fontSize = 25.sep,
                fontWeight = FontWeight.Bold
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.5.dep)
            ) {
                Text(
                    text = stringResource(id = R.string.USERNAME),
                    modifier = Modifier,
                    fontSize = 14.sep,
                    color = Color(0xF13E495E)
                )
                Spacer(modifier = Modifier.height(6.dep))
                OutlinedTextField(
                    value = userName,
                    onValueChange = {
                        notifier.notify(MyDataIds.recoverUserName, it)
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    placeholder = {
                        Text(text = "use@gmail.com")
                    },
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(16.dep))
                if (verifyButtonVisible) {
                    Button(
                        colors = ButtonDefaults.buttonColors(Color(0xFFFFEB56)),
                        onClick = {
                            notifier.notify(MyDataIds.verify)
                            if (userName.isNotBlank()) {
                                showOtpField = true
                                verifyButtonVisible = false
                            }
                        },
                        modifier = Modifier
                            .height(72.dep)
                            .fillMaxWidth(),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 8.dep,
                            pressedElevation = 10.dep
                        ),
                        shape = RectangleShape
                    ) {
                        if (recoverLoading.value) {
                            CircularProgressIndicator(
                                color = Color.White
                            )
                        } else {
                            Text(text = "Verify", fontSize = 20.sep, color = Color.Black)
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(15.dep))
            if (showOtpField) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.5.dep)
                ) {
                    OtpField(otpValue = otpValue)
                }
            }
            Spacer(modifier = Modifier.height(15.dep))

            if (otpValue.length == 4) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.5.dep)
                ) {
                    Column {
                        Text(
                            text = "PASSWORD",
                            modifier = Modifier
                                ,
                            fontSize = 14.sep,
                            color = Color(0xF13E495E)
                        )
                        Spacer(modifier = Modifier.height(6.dep))
                        OutlinedTextField(
                            value = password,
                            onValueChange = {
                                notifier.notify(MyDataIds.recoverPassword, it)
                            },
                            modifier = Modifier
                                .fillMaxWidth(),
                            placeholder = {
                                Text(text = "*******")
                            },
                            singleLine = true
                        )
                    }
                    Spacer(modifier = Modifier.height(15.dep))
                    Column {
                        Text(
                            text = "CONFIRM PASSWORD",
                            modifier = Modifier
                               ,
                            fontSize = 14.sep,
                            color = Color(0xF13E495E)
                        )
                        Spacer(modifier = Modifier.height(6.dep))
                        OutlinedTextField(
                            value = confirmPassword,
                            onValueChange = {
                                notifier.notify(MyDataIds.confirmPassword, it)
                            },
                            modifier = Modifier
                                .fillMaxWidth(),
                            placeholder = {
                                Text(text = "*******")
                            },
                            singleLine = true
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dep))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(horizontal = 32.5.dep)
                ) {
                    Button(
                        onClick = { notifier.notify(MyDataIds.resetPassword) },
                        modifier = Modifier
                            .height(72.dep)
                            .fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(Color(0xFFFFEB56)),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 8.dep,
                            pressedElevation = 10.dep
                        ),
                        shape = RectangleShape
                    ) {
                        Text(text = "Reset", fontSize = 20.sep, color = Color.Black)
                    }
                }
            }
        }
    }
}

@Composable
private fun OtpField(
    otpValue: String,
    notifier: NotificationService = rememberNotifier()
) {
    BasicTextField(
        value = otpValue,
        onValueChange = {
            if (it.length <= 4) {
                notifier.notify(MyDataIds.otpIntput, it)
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.NumberPassword
        ),
        decorationBox = {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(4) { index ->
                    val input = when {
                        index >= otpValue.length -> ""
                        else -> otpValue[index].toString()
                    }
                    val isFocused = otpValue.length == index
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .aspectRatio(1f)
                            .clip(CircleShape)
                            .background(
                                if (index > otpValue.length - 1) Color.Unspecified else Color(
                                    0xff707070
                                ).copy(
                                    alpha = .1f
                                )
                            )
                            .border(
                                if (index > otpValue.length - 1) 1.dep else 0.dep,
                                if (index > otpValue.length - 1) Color(0xffDDDDDD) else Color.Unspecified,
                                CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            modifier = Modifier,
                            text = input,
                            fontSize = 24.sep,
                            fontWeight = FontWeight.Normal,
                            color = Color(0xff25B58A),
                            textAlign = TextAlign.Center
                        )
                        Divider(
                            if (index > otpValue.length - 1) Modifier
                                .padding(top = 32.dep)
                                .width(13.dep)
                                .height(2.dep) else Modifier
                                .width(0.dep)
                                .height(0.dep),
                            color = Color.Gray,
                            thickness = if (isFocused) 2.dep else 0.dep,
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dep))
                }
            }
        }
    )
    Spacer(modifier = Modifier.height(16.dep))
}

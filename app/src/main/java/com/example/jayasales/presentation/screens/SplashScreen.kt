package com.example.jayasales.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.debduttapanda.j3lib.dep
import com.debduttapanda.j3lib.rememberStringState
import com.debduttapanda.j3lib.sep
import com.example.jayasales.MyDataIds
import com.example.jayasales.R
import com.example.jayasales.openSans

@Composable
fun SplashScreen(
    versionName: State<String> = rememberStringState(id = MyDataIds.versionName),
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .background(color = Color(0xFFFFEB56))
            .fillMaxSize()
    ){
        Image(
            painter = painterResource(id = R.drawable.jayasales),
            contentDescription = "",
            modifier = Modifier
                .height(120.dep)
                .width(196.dep),
        )
        Spacer(modifier = Modifier.height(12.dep))
        Text(
            stringResource(id = R.string.splash),
            fontSize = 20.sep,
            fontFamily = openSans,
            letterSpacing = (-0.03).sep,
            color = Color(0xFF222222)
        )
    }
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier
            .padding(bottom = 30.dep)
            .fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
        }
        Spacer(modifier = Modifier.height(200.dep))
        Text(
            text = "Version : ${versionName.value}",
            fontSize = 14.sep,
            color = Color.Black,
            fontWeight = FontWeight.SemiBold
        )
    }
}
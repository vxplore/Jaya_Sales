package com.example.jayasales.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.debduttapanda.j3lib.NotificationService
import com.debduttapanda.j3lib.dep
import com.debduttapanda.j3lib.doubleState
import com.debduttapanda.j3lib.rememberNotifier
import com.debduttapanda.j3lib.sep
import com.example.jayasales.MyDataIds
import com.example.jayasales.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarkVisitScreen(
    notifier:NotificationService = rememberNotifier(),
){
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.mark_visit),
                        fontSize = 20.sep,
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
            Spacer(modifier = Modifier.height(16.dep))
            GoogleMapSection()
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoogleMapSection(
    latitude: Double = doubleState(key = MyDataIds.currentLatitude).value,
    longitude: Double = doubleState(key = MyDataIds.currentLongitude).value,
    notifier: NotificationService= rememberNotifier()
) {

    val context = LocalContext.current
    val currentLocation by remember (latitude,longitude){
        derivedStateOf {
            LatLng(latitude,longitude)
        }
    }


    val currentLocationState = remember(currentLocation) {
        MarkerState(position = currentLocation)
    }

    val cameraPositionState = rememberCameraPositionState(currentLocation.toString()) {
        position = CameraPosition.fromLatLngZoom(currentLocation, 10f)
    }

    LaunchedEffect(currentLocation) {
        cameraPositionState.animate(
            update = CameraUpdateFactory.newCameraPosition(
                CameraPosition(currentLocation, 15f, 0f, 0f)
            ),
            durationMs = 1000
        )
    }

    Box(
        modifier = Modifier
            .fillMaxWidth(1f)
            .height(220.dep),
    ) {
        Card(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dep /*bottom = 6.dp*/, start = 16.dep, end = 16.dep),
            shape = RoundedCornerShape(20.dep)
        ) {
            GoogleMap(
                modifier = Modifier
                    .fillMaxSize(),
                cameraPositionState = cameraPositionState,
                properties = MapProperties(isIndoorEnabled = true, isBuildingEnabled = true, isMyLocationEnabled = true),
                uiSettings = MapUiSettings(
                    scrollGesturesEnabled = true,
                    zoomGesturesEnabled = true,
                    zoomControlsEnabled = false,
                    mapToolbarEnabled = true,
                    indoorLevelPickerEnabled = true,
                    tiltGesturesEnabled = true,
                    myLocationButtonEnabled = true,
                    scrollGesturesEnabledDuringRotateOrZoom = true
                )
            ) {
                Marker(
                    state = currentLocationState,
                )
            }
        }
    }
}
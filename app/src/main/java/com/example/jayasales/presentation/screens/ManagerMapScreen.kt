package com.example.jayasales.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.debduttapanda.j3lib.NotificationService
import com.debduttapanda.j3lib.boolState
import com.debduttapanda.j3lib.dep
import com.debduttapanda.j3lib.doubleState
import com.debduttapanda.j3lib.rememberNotifier
import com.debduttapanda.j3lib.sep
import com.example.jayasales.MyDataIds
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManagerMapScreen(
    notifier: NotificationService = rememberNotifier(),
    permissionGrant: State<Boolean> = boolState(key = MyDataIds.locationPermission),
    latitude: State<Double> = doubleState(key = MyDataIds.mapLatitude),
    longitude: State<Double> = doubleState(key = MyDataIds.mapLongitude),
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Location",
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
                .padding(horizontal = 16.dep)
                .padding(bottom = 12.dep)
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(20.dep))
            MapScreen(permissionGrant.value, latitude.value, longitude.value)
        }
    }

}

@Composable
fun MapScreen(
    permissionGrant: Boolean,
    latitude: Double,
    longitude: Double
) {
    val scope = rememberCoroutineScope()
    val currentLocation by remember(latitude, longitude) {
        derivedStateOf {
            LatLng(latitude, longitude)
        }
    }
    val currentLocationState = remember(currentLocation) {
        MarkerState(position = currentLocation)
    }
    val cameraPositionState = rememberCameraPositionState(currentLocation.toString()) {
        position = CameraPosition.fromLatLngZoom(currentLocation, 10f)
    }
    LaunchedEffect(currentLocation) {
        scope.launch {
            cameraPositionState.animate(
                update = CameraUpdateFactory.newCameraPosition(
                    CameraPosition(currentLocation, 15f, 0f, 0f)
                ),
                durationMs = 1000
            )
        }
    }
    GoogleMap(
        modifier = Modifier
            .fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = MapProperties(
            isIndoorEnabled = true, isBuildingEnabled = true,
        ),
        uiSettings = MapUiSettings(
            scrollGesturesEnabled = true,
            zoomGesturesEnabled = true,
            zoomControlsEnabled = false,
            mapToolbarEnabled = true,//newly added
            indoorLevelPickerEnabled = true,
            tiltGesturesEnabled = true,
            myLocationButtonEnabled = true,
            scrollGesturesEnabledDuringRotateOrZoom = true
        )
    )
    {
        Marker(state = currentLocationState)
    }
}
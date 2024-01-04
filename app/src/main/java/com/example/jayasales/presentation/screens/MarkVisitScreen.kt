package com.example.jayasales.presentation.screens

import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.debduttapanda.j3lib.NotificationService
import com.debduttapanda.j3lib.dep
import com.debduttapanda.j3lib.rememberNotifier
import com.debduttapanda.j3lib.sep
import com.debduttapanda.j3lib.stringState
import com.example.jayasales.MyDataIds
import com.example.jayasales.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.coroutineScope
import java.util.concurrent.CancellationException

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarkVisitScreen(
    notifier: NotificationService = rememberNotifier(),
    comments: State<String> = stringState(key = MyDataIds.comments)
) {
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
                .padding(horizontal = 20.dep)
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(16.dep))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(236.dep)
            ) {
                GoogleMapSection()
                //MapScreenWithMarker()
            }
            Spacer(modifier = Modifier.height(20.dep))
            OutlinedTextField(
                value = comments.value,
                onValueChange = {
                    notifier.notify(MyDataIds.comments, it)
                },
                maxLines = 1,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                placeholder = {
                    Text(
                        stringResource(id = R.string.Comments),
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
            Spacer(modifier = Modifier.height(20.dep))
            Button(
                onClick = { notifier.notify(MyDataIds.visitShop) },
                modifier = Modifier
                    .height(54.dep)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(Color(0xFF1FB574)),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 8.dep,
                    pressedElevation = 10.dep
                ),
                shape = RoundedCornerShape(4.dep)
            ) {
                Text(text = stringResource(id = R.string.VISIT_SHOP), fontSize = 16.sep, color = Color.White)
            }
        }
    }
}

@Composable
fun GoogleMapSection(
    latitude: Double = 0.0,
    longitude: Double = 0.0,
) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { result ->
            val granted = result.entries.all { it.value }
        }
    )
    DisposableEffect(context) {
        launcher.launch(
            arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
        onDispose { }
    }
    val permissionState by remember {
        mutableStateOf(
            when {
                ContextCompat.checkSelfPermission(
                    context,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED -> PermissionState.Granted

                else -> PermissionState.NotGranted
            }
        )
    }

    when (permissionState) {
        PermissionState.Granted -> {
            MapContent(latitude, longitude)
        }

        PermissionState.NotGranted -> {
            Text("Location permission is required for this feature.")
        }
    }
}

enum class PermissionState {
    Granted,
    NotGranted
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapContent(
    latitude: Double,
    longitude: Double
) {
    val currentLocationState = remember { MarkerState(position = LatLng(latitude, longitude)) }

    val cameraPositionState = rememberCameraPositionState("default") {
        CameraPosition.Builder()
            .target(LatLng(latitude, longitude))
            .zoom(15f)
            .build()
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
    ) {
        Card(
            onClick = { },
            modifier = Modifier
                .fillMaxSize(),
            shape = RoundedCornerShape(4.dp)
        ) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                properties = MapProperties(
                    isIndoorEnabled = true,
                    isBuildingEnabled = true,
                    isMyLocationEnabled = true,
                    mapType = MapType.NORMAL
                ),
                uiSettings = MapUiSettings(
                    scrollGesturesEnabled = true,
                    zoomGesturesEnabled = true,
                    zoomControlsEnabled = true,
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
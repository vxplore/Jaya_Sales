package com.example.jayasales.utility

import android.content.Context
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class)
fun updateLocation(
    permissionHandler : PermissionHandler,
    scope: CoroutineScope,
    context: Context,
    callback: (Double, Double, Throwable?) -> Unit
) {
    scope.launch {
        val p = listOf(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        )
        if(
            permissionHandler
                .check(
                    *p.toTypedArray()
                )
                ?.allPermissionsGranted == true
        ){
            fetchLocation(context,callback)
        }
        else{
            val granted = permissionHandler.request(*p.toTypedArray())?.all {
                it.value
            } == true
            if(granted){
                fetchLocation(context,callback)
            }
        }
    }

}

private fun fetchLocation(
    context: Context,
    callback: (Double,Double,Throwable?)->Unit
) {
    getLastLocation(context,callback)
}
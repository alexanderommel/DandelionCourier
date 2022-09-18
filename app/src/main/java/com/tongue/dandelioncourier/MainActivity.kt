package com.tongue.dandelioncourier

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.tongue.dandelioncourier.data.model.Position
import com.tongue.dandelioncourier.databinding.ActivityMainBinding
import com.tongue.dandelioncourier.ui.viewmodels.ActivityViewModel
import com.tongue.dandelioncourier.utils.AppLog

class MainActivity : AppCompatActivity() {

    companion object{
        const val TAG = "MainActivity"
        const val REQUEST_PERMISSION = 170005
    }

    private lateinit var binding: ActivityMainBinding
    private val viewModel: ActivityViewModel by viewModels()
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 3000
        locationRequest.fastestInterval = 3000

    }

    override fun onStart() {
        super.onStart()

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            val permissions = arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSION)
        }

        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    AppLog.d(TAG,"Location updated")
                    val location: Location = locationResult.lastLocation
                    val currentPosition = Position(location.latitude.toFloat(), location.longitude.toFloat(),"","me")
                    viewModel.position = currentPosition
                }
            },
            Looper.getMainLooper()
        )

    }

}
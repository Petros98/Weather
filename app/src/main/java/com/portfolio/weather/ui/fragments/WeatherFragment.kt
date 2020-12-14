package com.portfolio.weather.ui.fragments

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.portfolio.weather.R
import com.portfolio.weather.data.util.hasNetwork
import com.portfolio.weather.databinding.FragmentWeatherBinding
import com.portfolio.weather.ui.showSnackbar
import com.portfolio.weather.utils.ConnectivityReceiver
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class WeatherFragment : Fragment(), LocationListener {

    private lateinit var binding: FragmentWeatherBinding
    private val viewModel by viewModel<WeatherViewModel>()

    private lateinit var locationManager: LocationManager
    private val locationRequestCode = 111

    private lateinit var connReceiver: ConnectivityReceiver

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeatherBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.run {
            viewModel = this@WeatherFragment.viewModel
            lifecycleOwner = this@WeatherFragment
            searchButton.setOnClickListener {
                lifecycleScope.launch {
                    this@WeatherFragment.viewModel.search()
                }
            }
            btnGeo.setOnClickListener { getLocation() }
        }
        observe()
        registerNetworkBroadcast()
        setSearchAction()
    }
    
    private fun observe(){
        viewModel.error.observe(viewLifecycleOwner){
            this.showSnackbar(it)
//            Snackbar.make(
//                    binding.root, it,
//                    Snackbar.LENGTH_SHORT
//            ).show()
        }
    }

    private fun setSearchAction(){
        binding.searchText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                v.clearFocus()
                val input: InputMethodManager? =
                    context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
                input?.hideSoftInputFromWindow(v.windowToken, 0)
                lifecycleScope.launch {
                    viewModel.search()
                }
                true
            }
            else false
        }
    }

    private fun getLocation() {
        locationManager = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        context?.let { context ->
            when {
                ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED -> {
                    activity?.let { activity ->
                        ActivityCompat.requestPermissions(
                                activity,
                                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                                locationRequestCode
                        )
                    }
                }
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) -> {
                    if (context.hasNetwork())
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                5000,
                                5f,
                                this
                        )
                    else
                        this.showSnackbar(getText(R.string.no_internet).toString())

//                    Snackbar.make(binding.root, getText(R.string.no_internet), Snackbar.LENGTH_SHORT).show()
                }
                else -> {
                    Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).also {
                        startActivity(it)
                    }
                }
            }
        }
    }

    private fun registerNetworkBroadcast() {
        connReceiver = ConnectivityReceiver{
            if (!it){
                viewModel.setErrorMessage(getString(R.string.no_internet))
            }
        }
        val filter = IntentFilter()
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
        activity?.registerReceiver(connReceiver, filter)
    }

    private fun unregister(){
        activity?.unregisterReceiver(connReceiver)
    }

    override fun onProviderEnabled(provider: String) {
        this.showSnackbar("enabled $provider")
    }

    override fun onProviderDisabled(provider: String) {
        this.showSnackbar("disabled $provider")
    }

    override fun onLocationChanged(location: Location) {
        lifecycleScope.launch {
            viewModel.getByLocation(location)
        }
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        if (requestCode == locationRequestCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        unregister()
        super.onDestroy()
    }

}
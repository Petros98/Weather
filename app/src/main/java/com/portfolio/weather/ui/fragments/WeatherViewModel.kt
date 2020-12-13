package com.portfolio.weather.ui.fragments

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.portfolio.weather.domain.interactors.WeatherInteractor
import com.portfolio.weather.entity.local.Current
import com.portfolio.weather.entity.local.Result
import com.portfolio.weather.entity.remote.City
import com.portfolio.weather.entity.remote.Coord
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeatherViewModel(private val interactor: WeatherInteractor) : ViewModel() {

    private val _currentWeather by lazy {  MutableLiveData<Current>() }
    val currentWeather: LiveData<Current> get() = _currentWeather

    private val _error by lazy {  MutableLiveData<String>() }
    val error: LiveData<String> get() = _error

    private val _cities by lazy {  MutableLiveData<List<City>>() }
    val cities: LiveData<List<City>> get() = _cities
    
    private val _isLoading by lazy { MutableLiveData<Boolean>() }
    val isLoading: LiveData<Boolean> get() = _isLoading

    val cityName by lazy { MutableLiveData<String>() }

    init {
        _cities.value = mutableListOf(
            City(Coord(40.1811,44.5136),"AM",616052,"Yerevan",1093485,1607832962,1607866598,14400),
            City(Coord(55.7522,37.6156),"RU",524901,"Moscow",1000000,1607838696,1607864176,10800),
            City(Coord(48.8534,2.3488),"FR",2988507,"Paris",2138551,1607844969,1607874837,3600),
            City(Coord(52.5244,13.4105),"DE",2950159,"Berlin",1000000,1607843368,1607871126,3600),
        )
        viewModelScope.launch {
            getDefault()
        }
    }

    private suspend fun getDefault(){
        withContext(Dispatchers.Main){
            _isLoading.value = true
            when(val data = interactor.getWeather()){
                is Result.Success -> {
                    _currentWeather.value = data.data
                    currentWeather.value?.city?.let { addCityToList(it) }
                }
                is Result.Error ->{
                    _error.value = data.errors.errorMessage
                }
            }
            _isLoading.value = false
        }
    }

    suspend fun search(){
        withContext(Dispatchers.Main) {
            cityName.value?.let { searchName ->
                _isLoading.value = true
                when (val data = interactor.getWeatherByCityName(searchName.trim())) {
                    is Result.Success -> {
                        _currentWeather.value = data.data
                        currentWeather.value?.city?.let { addCityToList(it) }
                    }
                    is Result.Error -> {
                        _error.value = data.errors.errorMessage
                    }
                }
                _isLoading.value = false
            }
        }
    }

    suspend fun getByLocation(location: Location){
        withContext(Dispatchers.Main) {
            _isLoading.value = true
            when (val data = interactor.getByLocation(location)) {
                is Result.Success -> {
                    _currentWeather.value = data.data
                    currentWeather.value?.city?.let { addCityToList(it) }
                }
                is Result.Error -> {
                    _error.value = data.errors.errorMessage
                }
            }
            _isLoading.value = false
        }
    }

    fun setErrorMessage(text: String) {
        _error.value = text
    }

    private fun addCityToList(city: City){
        val list = cities.value?.toMutableList()
        list?.let{
            if (!list.contains(city))
                it.add(city)
        }
        _cities.value = list
    }
}
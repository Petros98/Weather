package com.portfolio.weather.entity.local

data class CallException<ErrorBody>(val errorCode: Int, val errorMessage:String?=null, val errorBody: ErrorBody? = null)
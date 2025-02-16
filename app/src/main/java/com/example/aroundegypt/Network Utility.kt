package com.example.aroundegypt

import android.net.ConnectivityManager
import android.net.NetworkCapabilities

fun isNetworkStable(connectivityManager: ConnectivityManager): Boolean {
    val network = connectivityManager.activeNetwork ?: return false
    val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
    return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
}
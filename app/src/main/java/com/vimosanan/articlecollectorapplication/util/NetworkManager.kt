package com.vimosanan.articlecollectorapplication.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import com.vimosanan.articlecollectorapplication.app.IS_CONNECTED

object NetworkManager {
    fun registerNetworkCallback(context: Context){
        try{
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                connectivityManager.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
                    override fun onAvailable(network: Network) {
                        IS_CONNECTED = true
                    }

                    override fun onLost(network: Network) {
                        IS_CONNECTED = false
                    }
                })
            }
        } catch (exception: Exception){
            IS_CONNECTED = false
        }
    }
}
package com.example.todolistyandex.data.network

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import com.example.todolistyandex.NetworkReceiverEntryPoint
import com.example.todolistyandex.data.repository.ToDoItemsRepository
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * BroadcastReceiver class for detecting network connectivity changes,
 * triggering a callback when the network becomes available.
 */

@AndroidEntryPoint
class NetworkReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        if (networkInfo != null && networkInfo.isConnected) {
            val entryPoint = EntryPointAccessors.fromApplication(context, NetworkReceiverEntryPoint::class.java)
            GlobalScope.launch {
                entryPoint.onNetworkAvailable().invoke()
            }
        }
    }
}




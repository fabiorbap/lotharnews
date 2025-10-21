package br.fabiorbap.lotharnews.common.util
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

fun isNetworkAvailable(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val network = connectivityManager.activeNetwork
    val activeNetwork = network?.let { connectivityManager.getNetworkCapabilities(it) }

    return activeNetwork?.let {
        it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                it.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                it.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ||
                it.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH)
    } ?: false
}

package br.com.connectattoo.utils

import android.view.View
import androidx.fragment.app.Fragment
import br.com.connectattoo.R
import br.com.connectattoo.ui.loadingscreen.LoadingScreenFragment
import br.com.connectattoo.utils.Constants.INTERVAL_TIME_MILLIS_1500
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay

fun Fragment.showLoadingFragment(rootScreen: View) {

    rootScreen.visibility = View.GONE
    parentFragmentManager.beginTransaction()
        .add(R.id.nav_host_fragment, LoadingScreenFragment(), "loadingFragment")
        .commitAllowingStateLoss()
}

fun Fragment.hideLoadingFragment(rootScreen: View) {
    parentFragmentManager.findFragmentByTag("loadingFragment")?.let {
        parentFragmentManager.beginTransaction()
            .remove(it)
            .commitAllowingStateLoss()
    }
    rootScreen.visibility = View.VISIBLE
}

fun <T> Fragment.executeWithLoadingAsync(
    binding: View,
    function: suspend () -> T
): Deferred<T> {
    return CoroutineScope(Dispatchers.Main).async {
        val startTime = System.currentTimeMillis()
        val result = function()
        val endTime = System.currentTimeMillis()
        val durationMs = endTime - startTime

        if (durationMs > 1) {
            showLoadingFragment(binding)
            delay(INTERVAL_TIME_MILLIS_1500)
        }

        result
    }
}

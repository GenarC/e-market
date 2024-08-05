package com.genar.e_market.extensions

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.genar.e_market.utils.FragmentViewBindingDelegate
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.coroutineContext

fun <T : ViewBinding> Fragment.viewBinding(viewBindingFactory: (View) -> T) =
    FragmentViewBindingDelegate(this, viewBindingFactory)

@JvmName(name = "observeFlow")
fun <T : Any?, F : Flow<T>> LifecycleOwner.observeFlow(
    flow: F, body: (T) -> Unit,
    state: Lifecycle.State = Lifecycle.State.STARTED
) {
    lifecycleScope.launch {
        repeatOnLifecycle(state) {
            flow.collect { body(it) }
        }
    }
}

fun LifecycleOwner.launch(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    stuff: suspend () -> Unit
) {
    lifecycleScope.launch(context, start) {
        stuff
    }

}

suspend inline fun <T> Flow<T>.safeCollect(crossinline action: suspend (value: T) -> Unit) {
    collect {
        coroutineContext.ensureActive()
        action(it)
    }
}
package me.barta.stayintouch.common.viewbinding

import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

fun <T : ViewBinding> Fragment.viewBinding(viewBindingFactory: (View) -> T, disposeCallback: T.() -> Unit = {}) =
    FragmentViewBindingDelegate(this, viewBindingFactory, disposeCallback)

inline fun <T : ViewBinding> AppCompatActivity.viewBinding(crossinline bindingInflater: (LayoutInflater) -> T) =
    lazy { bindingInflater.invoke(layoutInflater) }

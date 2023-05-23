package com.example.basicworktest.denise.mendez.view.properties

import android.app.Activity
import android.view.LayoutInflater
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import denise.mendez.meli.adapter.ItemView
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

private abstract class BindingProperty<T, B : ViewDataBinding> : ReadOnlyProperty<T, B> {

    private var binding: B? = null

    override fun getValue(thisRef: T, property: KProperty<*>): B =
        binding ?: createBinding(thisRef).also { binding = it }

    abstract fun createBinding(thisRef: T): B
}

fun <T : ViewDataBinding> activityBinding(@LayoutRes resId: Int): ReadOnlyProperty<Activity, T> =
    object : BindingProperty<Activity, T>() {
        override fun createBinding(thisRef: Activity): T =
            DataBindingUtil.setContentView(thisRef, resId)
    }

fun <T : ViewDataBinding> fragmentBinding(@LayoutRes resId: Int): ReadOnlyProperty<Fragment, T> =
    object : BindingProperty<Fragment, T>() {
        override fun createBinding(thisRef: Fragment): T =
            DataBindingUtil.inflate(LayoutInflater.from(thisRef.context), resId, null, false)
    }

fun <T : ViewDataBinding> itemViewBinding(@LayoutRes resId: Int): ReadOnlyProperty<ItemView<*>, T> =
    object : BindingProperty<ItemView<*>, T>() {
        override fun createBinding(thisRef: ItemView<*>): T =
            DataBindingUtil.inflate(LayoutInflater.from(thisRef.context), resId, null, true)
    }
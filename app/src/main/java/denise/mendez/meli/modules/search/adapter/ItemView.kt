package com.example.basicworktest.denise.mendez.adapter

import android.content.Context
import android.view.View

interface ItemView<T> {

    val context: Context

    val view: View

    val data: T

    fun bind(item: T)
}
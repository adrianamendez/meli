package com.example.basicworktest.denise.mendez.adapter

class ItemDataAbstract<T>(
    override val data: T,
    override val type: Int = 0
) : ItemData<T>
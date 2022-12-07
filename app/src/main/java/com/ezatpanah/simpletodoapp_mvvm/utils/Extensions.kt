package com.ezatpanah.simpletodoapp_mvvm.utils

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner

fun Spinner.setupList(list: MutableList<String>, callback: (String) -> Unit) {
    val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, list)
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    this.adapter = adapter
    this.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            callback(list[position])
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {}
    }
}

fun MutableList<out Any>.getIndexFromList(item: Any): Int {
    var index = 0
    for (i in this.indices) {
        if (this[i] == item) {
            index = i
            break
        }
    }
    return index
}
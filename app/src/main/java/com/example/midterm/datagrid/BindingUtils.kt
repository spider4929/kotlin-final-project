package com.example.midterm.datagrid

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.midterm.database.Path

@BindingAdapter("pathDetailString")
fun TextView.setPathDetailString(item: Path?) {
    item?.let{
        text = item.description
    }
}

@BindingAdapter("pathString")
fun TextView.setPathString(item: Path?) {
    item?.let{
        text = item.title
    }
}
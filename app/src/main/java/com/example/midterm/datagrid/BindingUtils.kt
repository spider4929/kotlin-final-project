package com.example.midterm.datagrid

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.midterm.R
import com.example.midterm.database.Path

@BindingAdapter("pathDetailString")
fun TextView.setPathDetailString(item: Path?) {
    item?.let{
        text = "Loading data..."
    }
}

@BindingAdapter("pathString")
fun TextView.setPathString(item: Path?) {
    item?.let{
        text = item.title
    }
}

@BindingAdapter("mapImage")
fun ImageView.setMapImage(item: Path?){
    item?.let {
        setImageResource(R.drawable.map_icon)
    }
}

@BindingAdapter("pathSrcDestString")
fun TextView.setPathSrcDestString(item: Path?) {
    item?.let {
        text = "From ${item.source} to ${item.destination}"
    }
}

@BindingAdapter("pathSrcString")
fun TextView.setPathSrcString(item: Path?){
    item?.let {
        text = item.source
    }
}

@BindingAdapter("pathDestString")
fun TextView.setPathDestString(item: Path?){
    item?.let {
        text = item.destination
    }
}
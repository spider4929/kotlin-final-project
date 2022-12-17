package com.example.pathfinder.datagrid

import android.content.res.Resources
import android.os.Build
import android.text.Html
import android.text.Spanned
import com.example.pathfinder.database.Path

fun formatPath(paths: List<Path>, resources: Resources): Spanned {
    val sb = StringBuilder()
    sb.apply {
        append("<h3>HERE IS YOUR PATH DATA</H3>")
        paths.forEach {
            append("<br>")
            append("<b>Title:</b>")
            append("\t${it.title}<br>")
            append("<b>Source:</b>")
            append("\t${it.source}<br>")
            append("<b>Destination:</b>")
            append("\t${it.destination}<br>")
            append("<b>Description:</b>")
            append("\t${it.description}<br><br>")
        }
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        return Html.fromHtml(sb.toString(), Html.FROM_HTML_MODE_LEGACY)
    } else {
        @Suppress("DEPRECATION")
        return Html.fromHtml(sb.toString())
    }
}
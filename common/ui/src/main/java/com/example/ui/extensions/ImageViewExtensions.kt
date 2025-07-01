package com.example.ui.extensions

import android.widget.ImageView
import com.example.ui.R
import com.squareup.picasso.Picasso

fun ImageView.loadImage(url: String?) {
    if (url.isNullOrBlank()) {
        setImageResource(R.drawable.placeholder)
        return
    }

    Picasso.get()
        .load(url)
        .placeholder(R.drawable.placeholder)
        .error(R.drawable.placeholder)
        .into(this)
}

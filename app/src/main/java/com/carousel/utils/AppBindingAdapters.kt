package com.carousel.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.ImageLoader
import coil.request.ImageRequest
import com.carousel.R

@BindingAdapter("setImage")
fun setImage(imageView: ImageView, url: String) {
    val imgLoader = ImageLoader.Builder(imageView.context)
//                TODO: change place holder to whatever you want
        .placeholder(R.drawable.ic_launcher_foreground)
        .crossfade(true)
        .build()

    imgLoader.enqueue(
        ImageRequest.Builder(imageView.context)
            .data(url)
            .target(imageView)
            .build()
    )
}
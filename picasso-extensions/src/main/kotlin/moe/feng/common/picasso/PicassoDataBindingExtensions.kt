@file:JvmName("PicassoDataBindingUtils")
package moe.feng.common.picasso

import android.databinding.BindingAdapter
import android.net.Uri
import android.widget.ImageView
import java.io.File

@BindingAdapter("bind:picassoLoadUri")
fun loadUri(imageView: ImageView, uri: Uri?) {
    imageView.loadUri = uri
}

@BindingAdapter("bind:picassoLoadUrl")
fun loadUrl(imageView: ImageView, url: String?) {
    imageView.loadUrl = url
}

@BindingAdapter("bind:picassoLoadFile")
fun loadFile(imageView: ImageView, file: File?) {
    imageView.loadFile = file
}
@file:JvmName("PicassoUtils")
package moe.feng.common.picasso

import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator
import java.io.File

/**
 * Picasso Extensions
 *
 * Help developers use Picasso library to load image async.
 *
 * @see com.squareup.picasso.Picasso
 */
private val ANIMATION_DURATION : Int = 1000

var ImageView.placeholderResource : Int?
    get() = this.getTag(R.id.tag_picasso_placeholder_res) as? Int
    set(value) {
        this.setTag(R.id.tag_picasso_placeholder_drawable, null)
        this.setTag(R.id.tag_picasso_placeholder_res, value)
    }

var ImageView.placeholder : Drawable?
    get() = this.getTag(R.id.tag_picasso_placeholder_drawable) as? Drawable
    set(value) {
        this.setTag(R.id.tag_picasso_placeholder_drawable, value)
        this.setTag(R.id.tag_picasso_placeholder_res, 0)
    }

var ImageView.picassoCallback : Callback?
    get() = this.getTag(R.id.tag_picasso_callback) as? Callback
    set(value) { this.setTag(R.id.tag_picasso_callback, value) }

var ImageView.loadUrl : String?
    get() = this.getTag(R.id.tag_loading_url) as? String
    set(value) {
        if (value == null) return
        this.setTag(R.id.tag_loading_url, value)
        Picasso.with(this.context).load(value).let { makePicassoRequest(it) }
    }

var ImageView.loadUri : Uri?
    get() = this.getTag(R.id.tag_loading_uri) as? Uri
    set(value) {
        if (value == null) return
        this.setTag(R.id.tag_loading_uri, value)
        Picasso.with(this.context).load(value).let { makePicassoRequest(it) }
    }

var ImageView.loadFile : File?
    get() = File(this.getTag(R.id.tag_loading_url) as? String)
    set(value) {
        if (value == null) return
        this.setTag(R.id.tag_loading_url, value.absolutePath)
        Picasso.with(this.context).load(value).let { makePicassoRequest(it) }
    }

/**
 * Set common options of requests
 *
 * @param transformer Methods to modify request options
 */
fun ImageView.picassoRequestTransform(transformer: RequestCreator.() -> Unit) {
    setTag(R.id.tag_picasso_request_transfomer, transformer)
}

private fun ImageView.makePicassoRequest(request: RequestCreator) {
    if (placeholderResource != null && placeholderResource != 0) request.placeholder(placeholderResource!!)
    if (placeholder != null) request.placeholder(placeholder)
    val transformer = getTag(R.id.tag_picasso_request_transfomer) as? RequestCreator.() -> Unit
    transformer?.let { it(request) }
    if (picassoCallback != null) request.into(this, picassoCallback) else request.into(this)
}

/**
 * Enable Material Fade-In Animation after loading
 */
fun ImageView.enableMaterialPicassoAnimation() {
    this.picassoCallback = object : Callback {
        override fun onSuccess() {
            MaterialImageLoader.animate(this@enableMaterialPicassoAnimation).setDuration(ANIMATION_DURATION).start()
        }
        override fun onError() {}
    }
}

/**
 * Picasso.into() with Material Fade-In Animation
 *
 * @param view Target ImageView
 * @param callback Picasso Callback (Optional)
 */
fun RequestCreator.intoMaterialStyle(view : ImageView, callback : Callback? = null) {
    into(view, object : Callback {
        override fun onSuccess() {
            MaterialImageLoader.animate(view).setDuration(ANIMATION_DURATION).start()
            callback?.onSuccess()
        }

        override fun onError() {
            callback?.onError()
        }
    })
}
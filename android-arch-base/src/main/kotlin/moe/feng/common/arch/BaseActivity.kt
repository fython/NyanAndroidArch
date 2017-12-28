package moe.feng.common.arch

import android.os.Build
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import moe.shizuku.fontprovider.FontProviderClient

open class BaseActivity : AppCompatActivity() {

    protected val toolbar: Toolbar? by lazyBindNullable(R.id.toolbar)

    protected fun <T: View> lazyBind(@IdRes id: Int): LazyBindViewCreator<T> =
            LazyBindViewCreator(id)

    protected fun <T: View> lazyBindNullable(@IdRes id: Int): LazyBindNullableViewCreator<T> =
            LazyBindNullableViewCreator(id)

    open val defaultLayoutId: Int @LayoutRes get() = -1

    open internal val autoBindDefaultLayout: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        if (isFontProviderEnabled && Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
                && !isFontInitialized) {
            // Start replace fonts if necessary
            FontProviderClient.create(this)?.let { client ->
                client.setNextRequestReplaceFallbackFonts(true)
                client.replaceFonts()
                isFontInitialized = true
            }
        }

        super.onCreate(savedInstanceState)

        if (defaultLayoutId > 0 && autoBindDefaultLayout) {
            setContentView(defaultLayoutId)
        }
    }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        toolbar?.let(this::setSupportActionBar)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {

        private var isFontInitialized = false
        @JvmStatic var isFontProviderEnabled = false

        var replaceFonts: FontProviderClient.() -> Unit = {
            replace("Noto Sans CJK", "sans-serif", "sans-serif-medium")
            replace("Noto Serif CJK", "serif", "serif-medium")
        }

    }

}
package moe.feng.common.arch

import android.app.Activity
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.view.MenuItem
import android.view.View

open class BaseActivity : Activity() {

    protected fun <T: View> lazyBind(id: Int): LazyBindViewCreator<T> =
            LazyBindViewCreator(id)

    protected fun <T: View> lazyBindNullable(id: Int): LazyBindNullableViewCreator<T> =
            LazyBindNullableViewCreator(id)

    open val defaultLayoutId: Int @LayoutRes get() = -1

    open internal val autoBindDefaultLayout: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (defaultLayoutId > 0 && autoBindDefaultLayout) {
            setContentView(defaultLayoutId)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}
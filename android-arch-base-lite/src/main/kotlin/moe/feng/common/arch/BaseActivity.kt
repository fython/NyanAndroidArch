package moe.feng.common.arch

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Process
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

    /**
     * Run codes with permission safely.
     * @param permission Manifest.permission.XXX
     * @param onSuccess If permission is granted, then it will be called
     * @param onFailed If permission is denied, then it will be called
     */
    fun runWithPermission(permission: String, onFailed: () -> Unit = {}, onSuccess: () -> Unit) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            if (checkPermission(permission, Process.myPid(), Process.myUid())
                    == PackageManager.PERMISSION_GRANTED) {
                onSuccess()
            } else {
                onFailed()
            }
        } else {
            when {
                checkPermission(permission, Process.myPid(), Process.myUid())
                        == PackageManager.PERMISSION_GRANTED -> {
                    onSuccess()
                }
                shouldShowRequestPermissionRationale(permission) -> {
                    permissionOnSuccessCallbacks["$localClassName#$permission"] = onSuccess
                    onFailed()
                }
                else -> {
                    permissionOnSuccessCallbacks["$localClassName#$permission"] = onSuccess
                    requestPermissions(arrayOf(permission), REQUEST_PERMISSION_CODE)
                }
            }
        }
    }

    /**
     * Run codes with permission safely. (No rationale)
     * @param permission Manifest.permission.XXX
     * @param onSuccess If permission is granted, then it will be called
     * @return If it is denied (Lower than M), return false
     */
    fun runWithPermissionNoRationale(permission: String, onSuccess: () -> Unit) : Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            if (checkPermission(permission, Process.myPid(), Process.myUid())
                    == PackageManager.PERMISSION_GRANTED) {
                onSuccess()
            } else {
                return false
            }
        } else {
            if (checkPermission(permission, Process.myPid(), Process.myUid())
                    == PackageManager.PERMISSION_GRANTED) {
                onSuccess()
            } else {
                permissionOnSuccessCallbacks["$localClassName#$permission"] = onSuccess
                requestPermissions(arrayOf(permission), REQUEST_PERMISSION_CODE)
            }
        }
        return true
    }

    /**
     * If user accept permission rationale, you can call this continue to ask for permission.
     * @param permission Manifest.permission.XXX
     */
    fun continueRunWithPermission(permission: String) {
        if (checkPermission(permission, android.os.Process.myPid(), Process.myUid())
                == PackageManager.PERMISSION_GRANTED) {
            permissionOnSuccessCallbacks["$localClassName#$permission"]?.invoke()
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(arrayOf(permission), REQUEST_PERMISSION_CODE)
            } else {
                throw SecurityException("Cannot request $permission permission automatically.")
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        val deniedPermissions = mutableListOf<String>()
        permissions.forEachIndexed { index, permission ->
            if (grantResults[index] == PackageManager.PERMISSION_GRANTED) {
                permissionOnSuccessCallbacks["$localClassName#$permission"]?.invoke()
            } else if (grantResults[index] == PackageManager.PERMISSION_DENIED) {
                deniedPermissions += permission
            }
        }
        if (deniedPermissions.isNotEmpty()) {
            onPermissionsDenied(deniedPermissions.toTypedArray())
        }
    }

    open fun onPermissionsDenied(deniedPermissions: Array<out String>) {

    }

    companion object {

        private val permissionOnSuccessCallbacks = hashMapOf<String, () -> Unit>()
        private const val REQUEST_PERMISSION_CODE = 60003

    }

}
package br.com.connectattoo.utils.permissions

import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

object PermissionImage {
    fun Fragment.shouldRequestPermission(permissions: Array<String>): Boolean {
        val grantedPermissions = mutableListOf<Boolean>()
        permissions.forEach { permission ->
            grantedPermissions.add(hasPermission(permission))
        }
        return grantedPermissions.any { granted -> !granted }
    }

    fun Fragment.hasPermission(permission: String): Boolean {
        val permissionCheckResult = ContextCompat.checkSelfPermission(requireContext(), permission)
        return PackageManager.PERMISSION_GRANTED == permissionCheckResult
    }
}

package com.simoes.expense.helpers

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PermissionsHelper {

    companion object {

        fun havePermission (context: Context, typePermission: String) : Boolean {
            return ContextCompat.checkSelfPermission( context, typePermission ) == PackageManager.PERMISSION_GRANTED
        }

        fun requestPermission (context: Context, typePermission: String ) {
            ActivityCompat.requestPermissions(context as Activity, arrayOf( typePermission ), 0 )
        }

    }

}